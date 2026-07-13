package com.neeraj.assistant.rag.embedding.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.neeraj.assistant.rag.embedding.dto.JinaRequest;
import com.neeraj.assistant.rag.embedding.dto.JinaResponse;
import com.neeraj.assistant.rag.exception.EmbeddingServiceException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmbeddingClient {

    private final RestClient jinaRestClient;

    private static final String TASK = "retrieval.passage";

    @Value("${jina.api.key}")
    private String apiKey;
    
    @Value("${jina.api.model}")
    private String model;

    public List<Float> generateEmbedding(String text){

        JinaRequest request =
            new JinaRequest(
                model,
                TASK,
                text
            );

        JinaResponse response =
                  jinaRestClient.post()
                  .header("Authorization", "Bearer " + apiKey)
                  .header("Content-Type", "application/json")
                  .body(request)
                  .retrieve()
                  .body(JinaResponse.class);

        if(response == null|| response.data().get(0).embedding() == null || response.data().get(0).embedding().isEmpty()){
            throw new EmbeddingServiceException("jina returned an empty response");
        }

        return response
              .data()
              .get(0)
              .embedding();   
    }

}
