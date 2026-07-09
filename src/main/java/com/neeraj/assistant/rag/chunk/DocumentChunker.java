package com.neeraj.assistant.rag.chunk;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DocumentChunker {

    private final ChunkProperties properties;

    public List<String> chunk(String text){
        
        List<String> chunks = new ArrayList<>();

        if(text == null || text.isBlank()){
            return chunks;
        }

        int chunkSize = properties.getSize();
        int overlap   = properties.getOverlap();

        int start = 0;

        while(start < text.length()){

            int end = Math.min(start + chunkSize, text.length());

            chunks.add(text.substring(start, end));

            if(end == text.length()){
                break;
            }

            start = end - overlap;
        }
        return chunks;
    }

}
