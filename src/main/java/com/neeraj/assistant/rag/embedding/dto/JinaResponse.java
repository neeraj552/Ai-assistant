package com.neeraj.assistant.rag.embedding.dto;

import java.util.List;

public record JinaResponse(

    String model,
    String object,
    List<EmbeddingData> data
) {

}
