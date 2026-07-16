package com.neeraj.assistant.rag.embedding.service;

public interface EmbeddingService {

    float[] generateEmbedding(String text);
}
