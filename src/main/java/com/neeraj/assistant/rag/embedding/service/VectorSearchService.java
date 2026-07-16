package com.neeraj.assistant.rag.embedding.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.neeraj.assistant.rag.entity.DocumentChunk;
import com.neeraj.assistant.rag.repository.DocumentChunkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VectorSearchService {

    private final EmbeddingService embeddingService;
    private final DocumentChunkRepository documentChunkRepository;

    public void testSearch(String question){
        float[] vector = embeddingService.generateEmbedding(question);

        List<DocumentChunk> chunks = documentChunkRepository.findMostSimilaChunks(vector, 5);

        System.out.print("======results=====");

        chunks.forEach(chunk -> {
            System.out.println("Chunk " + chunk.getChunkIndex());
            System.out.println(chunk.getContent());
            System.out.println("--------------------------------");
        });
    }

}
