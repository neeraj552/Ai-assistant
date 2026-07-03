package com.neeraj.assistant.chat.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neeraj.assistant.chat.dto.ChatRequest;
import com.neeraj.assistant.chat.dto.ChatResponse;
import com.neeraj.assistant.chat.service.ChatService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    
    @PostMapping("/{fileId}")
    public ResponseEntity<ChatResponse> askQuestion(
        @PathVariable UUID fileId,
        @Valid @RequestBody ChatRequest request
    ){
        
        return ResponseEntity.ok(chatService.askQuestion(fileId, request));
    }
    
    @GetMapping("/{fileId}")
    public ResponseEntity<List<ChatResponse>> getCgatHistory(
        @PathVariable UUID fileId
    ){
        
        return ResponseEntity.ok(chatService.getChatHistory(fileId));
    }
    
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteChatHistory(
        @PathVariable UUID fileId
    ){

        chatService.deleteChatHistory(fileId);

        return ResponseEntity.noContent().build();
    }



}
