package com.neeraj.assistant.rag.chunk;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "rag.chunk")
public class ChunkProperties {

    private int size = 1000;

    private int overlap = 200;

}
