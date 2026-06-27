package com.neeraj.assistant.ai.dto;

import java.util.List;

public record GroqResponse(

        List<Choice> choices

) {
}