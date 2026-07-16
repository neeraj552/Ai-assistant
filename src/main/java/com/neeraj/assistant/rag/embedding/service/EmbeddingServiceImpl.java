package com.neeraj.assistant.rag.embedding.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.neeraj.assistant.rag.embedding.client.EmbeddingClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService {

    private final EmbeddingClient embeddingClient;

    @Override
    public float[] generateEmbedding(String text) {

        List<Float> embedding =
                embeddingClient.generateEmbedding(text);
        
        float[] vector = new float[embedding.size()];

        for(int i = 0; i < embedding.size(); i++){
            vector[i] = embedding.get(i);
        }
            

        return  vector;
    }
}
