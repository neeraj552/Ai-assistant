package com.neeraj.assistant.rag.service;

import java.util.UUID;

public interface ChunkService {

    void processDocument(UUID fileId);

}
