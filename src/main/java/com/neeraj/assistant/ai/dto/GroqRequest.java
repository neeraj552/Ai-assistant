package com.neeraj.assistant.ai.dto;

import java.util.List;

public record GroqRequest(

        String model,

        List<Message> messages

) {
}