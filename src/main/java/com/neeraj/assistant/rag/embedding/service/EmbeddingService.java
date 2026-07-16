package com.neeraj.assistant.rag.embedding.service;
import com.pgvector.PGvector;

public interface EmbeddingService {

    PGvector generateEmbedding(String text);
}
