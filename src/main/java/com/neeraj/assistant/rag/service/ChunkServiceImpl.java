package com.neeraj.assistant.rag.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neeraj.assistant.file.entity.FileDocument;
import com.neeraj.assistant.file.exception.ResourceNotFoundException;
import com.neeraj.assistant.file.repository.FileRepository;
import com.neeraj.assistant.rag.chunk.DocumentChunker;
import com.neeraj.assistant.rag.embedding.service.EmbeddingService;
import com.neeraj.assistant.rag.entity.DocumentChunk;
import com.neeraj.assistant.rag.repository.DocumentChunkRepository;
import com.neeraj.assistant.summary.util.PdfExtractor;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChunkServiceImpl implements ChunkService {

    private final FileRepository fileRepository;
    private final PdfExtractor   pdfExtractor;
    private final DocumentChunker documentChunker;
    private final DocumentChunkRepository chunkRepository;
    private final EmbeddingService embeddingService;
    
    @Override
    public void processDocument(UUID fileId){
        FileDocument file = fileRepository
               .findById(fileId)
               .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        File pdf = new File(file.getFilePath());

        String text = pdfExtractor.extractText(pdf);

        List<String> chunks = documentChunker.chunk(text);

        chunkRepository.deleteByFile(file);

        for(int i = 0; i < chunks.size(); i++){
            String chunkText = chunks.get(i);
            float[] embedding = embeddingService.generateEmbedding(chunkText);
            DocumentChunk chunk = DocumentChunk.builder()
                    .chunkIndex(i)
                    .content(chunks.get(i))
                    .embedding(embedding)
                    .file(file)
                    .build();
            
            chunkRepository.save(chunk);

        }


    }

}
