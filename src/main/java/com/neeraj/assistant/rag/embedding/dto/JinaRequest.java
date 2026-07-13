package com.neeraj.assistant.rag.embedding.dto;

public record JinaRequest(

    String model,

    String task,

    String input


) {

    
}
