package com.neeraj.assistant.summary.service;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neeraj.assistant.ai.client.GroqClient;
import com.neeraj.assistant.common.security.SecurityUtils;
import com.neeraj.assistant.file.entity.FileDocument;
import com.neeraj.assistant.file.exception.ResourceNotFoundException;
import com.neeraj.assistant.file.repository.FileRepository;
import com.neeraj.assistant.summary.dto.SummaryResponse;
import com.neeraj.assistant.summary.entity.Summary;
import com.neeraj.assistant.summary.entity.SummaryStatus;
import com.neeraj.assistant.summary.mapper.SummaryMapper;
import com.neeraj.assistant.summary.repository.SummaryRepository;
import com.neeraj.assistant.summary.util.PdfExtractor;
import com.neeraj.assistant.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SummaryServiceImpl implements SummaryService {

    private final FileRepository fileRepository;

    private final PdfExtractor pdfExtractor;

    private final GroqClient groqClient;

    private final SummaryRepository summaryRepository;

    @Override
    public SummaryResponse generateSummary(UUID fileId) {

        User user = SecurityUtils.getCurrentUser();

        FileDocument file = fileRepository
                .findByIdAndUser(fileId, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("File not found"));

        Optional<Summary> existingSummary =
                summaryRepository.findByFile(file);

        if (existingSummary.isPresent()) {
            return SummaryMapper.toResponse(existingSummary.get());
        }

        File pdf = new File(file.getFilePath());

        String extractedText = pdfExtractor.extractText(pdf);

        String generatedSummary =
                groqClient.generateSummary(extractedText);

        Summary summary = Summary.builder()
                .summaryText(generatedSummary)
                .modelUsed("llama-3.3-70b-versatile")
                .status(SummaryStatus.COMPLETED)
                .file(file)
                .build();

        Summary savedSummary = summaryRepository.save(summary);

        return SummaryMapper.toResponse(savedSummary);
    }

    @Override
    public SummaryResponse getSummary(UUID fileId) {

    User user = SecurityUtils.getCurrentUser();

    FileDocument file = fileRepository
            .findByIdAndUser(fileId, user)
            .orElseThrow(() ->
                    new ResourceNotFoundException("File not found"));

    Summary summary = summaryRepository.findByFile(file)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Summary not found"));

    return SummaryMapper.toResponse(summary);
    }
    
}