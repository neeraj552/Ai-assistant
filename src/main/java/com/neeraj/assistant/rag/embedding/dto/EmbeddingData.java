package com.neeraj.assistant.rag.embedding.dto;

import java.util.List;

public record EmbeddingData( 
     Integer index,
     List<Float> embedding
){}

