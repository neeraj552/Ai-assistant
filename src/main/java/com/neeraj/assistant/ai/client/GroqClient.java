package com.neeraj.assistant.ai.client;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.neeraj.assistant.ai.dto.GroqRequest;
import com.neeraj.assistant.ai.dto.GroqResponse;
import com.neeraj.assistant.ai.dto.Message;
import com.neeraj.assistant.ai.prompt.PromptTemplates;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GroqClient {

    private final RestClient groqRestClient;

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.model}")
    private String model;

    public String generateSummary(String documentText) {

    String prompt = PromptTemplates.SUMMARY_PROMPT
            .formatted(documentText);

    GroqRequest request = new GroqRequest(
            model,
            List.of(new Message("user", prompt))
    );

    GroqResponse response =
            groqRestClient.post()
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .body(request)
                    .retrieve()
                    .body(GroqResponse.class);

    if (response == null
            || response.choices() == null
            || response.choices().isEmpty()) {

        throw new RuntimeException("Groq returned an empty response");
    }

    return response
            .choices()
            .get(0)
            .message()
            .content();
}

}