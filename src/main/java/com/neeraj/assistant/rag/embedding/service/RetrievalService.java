package com.neeraj.assistant.rag.embedding.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neeraj.assistant.file.entity.FileDocument;
import com.neeraj.assistant.rag.entity.DocumentChunk;
import com.neeraj.assistant.rag.repository.DocumentChunkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RetrievalService {

    private final EmbeddingService embeddingService;

    private final DocumentChunkRepository documentChunkRepository;

    public List<DocumentChunk> retrieveRelevantChunks(FileDocument file, String question, int limit){
        float[] queryVector = embeddingService.generateEmbedding(question);
        return documentChunkRepository.findMostSimilaChunks(file.getId(),queryVector, limit);
    }

}
