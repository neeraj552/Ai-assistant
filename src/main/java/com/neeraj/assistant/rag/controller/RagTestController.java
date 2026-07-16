package com.neeraj.assistant.rag.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neeraj.assistant.rag.embedding.service.RetrievalService;
import com.neeraj.assistant.rag.entity.DocumentChunk;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/rag")
@RequiredArgsConstructor
public class RagTestController {

    private final RetrievalService retrievalService;

    @GetMapping("/test")
    public List<DocumentChunk> test(@RequestParam String question) {

        return retrievalService.retrieveRelevantChunks(question, 5);
    }
}