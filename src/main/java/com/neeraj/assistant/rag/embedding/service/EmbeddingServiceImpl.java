package com.neeraj.assistant.rag.embedding.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.neeraj.assistant.rag.embedding.client.EmbeddingClient;
import com.neeraj.assistant.rag.repository.DocumentChunkRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService {

    private final EmbeddingClient embeddingClient;

    @Override
    public String generateEmbedding(String text) {

        List<Float> embedding =
                embeddingClient.generateEmbedding(text);

        return embedding.toString();
    }
}
