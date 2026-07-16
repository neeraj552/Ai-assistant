package com.neeraj.assistant.rag.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.neeraj.assistant.file.entity.FileDocument;
import com.neeraj.assistant.rag.entity.DocumentChunk;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, UUID> {

    List<DocumentChunk> findByFileOrderByChunkIndexAsc(FileDocument file);

    void deleteByFile(FileDocument file);
    
    @Query( value = """
            SELECT *
            FROM document_chunks
            ORDER BY embedding <=> CAST(:queryEmbedding AS vector)
            LIMIT :limit
            """, nativeQuery  = true )
    List<DocumentChunk> findMostSimilaChunks(
        @Param("queryEmbedding") float[] queryEmbedding,
        @Param("limit") int limit);

}
