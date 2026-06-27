package com.neeraj.assistant.summary.controller.test;

import java.io.File;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neeraj.assistant.common.security.SecurityUtils;
import com.neeraj.assistant.file.entity.FileDocument;
import com.neeraj.assistant.file.exception.ResourceNotFoundException;
import com.neeraj.assistant.file.repository.FileRepository;
import com.neeraj.assistant.summary.util.PdfExtractor;
import com.neeraj.assistant.user.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController1 {

    private final PdfExtractor pdfExtractor;
    private final FileRepository fileRepository;

   @GetMapping("/extract/{fileId}")
public ResponseEntity<String> extractText(
        @PathVariable UUID fileId) {

    User user = SecurityUtils.getCurrentUser();

    FileDocument file = fileRepository
            .findByIdAndUser(fileId, user)
            .orElseThrow(() ->
                    new ResourceNotFoundException("File not found"));

    File pdf = new File(file.getFilePath());

    String text = pdfExtractor.extractText(pdf);

    return ResponseEntity.ok(text);
}
}