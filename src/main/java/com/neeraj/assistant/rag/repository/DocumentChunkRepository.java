package com.neeraj.assistant.rag.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neeraj.assistant.file.entity.FileDocument;
import com.neeraj.assistant.rag.entity.DocumentChunk;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, UUID> {

    List<DocumentChunk> findByFileOrderByChunkIndexAsc(FileDocument file);

    void deleteByFile(FileDocument file);

}
