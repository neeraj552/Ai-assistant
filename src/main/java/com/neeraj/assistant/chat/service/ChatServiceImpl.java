package com.neeraj.assistant.chat.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.neeraj.assistant.ai.client.GroqClient;
import com.neeraj.assistant.chat.dto.ChatRequest;
import com.neeraj.assistant.chat.dto.ChatResponse;
import com.neeraj.assistant.chat.entity.ChatMessage;
import com.neeraj.assistant.chat.mapper.ChatMapper;
import com.neeraj.assistant.chat.repository.ChatRepository;
import com.neeraj.assistant.common.security.SecurityUtils;
import com.neeraj.assistant.file.entity.FileDocument;
import com.neeraj.assistant.file.exception.ResourceNotFoundException;
import com.neeraj.assistant.file.repository.FileRepository;
import com.neeraj.assistant.rag.embedding.service.RetrievalService;
import com.neeraj.assistant.rag.entity.DocumentChunk;
import com.neeraj.assistant.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    private final FileRepository fileRepository;

    private final GroqClient     groqClient;

    private final RetrievalService retrievalService;

    
    @Override
    public ChatResponse askQuestion(UUID fileId, ChatRequest request){

        User user = SecurityUtils.getCurrentUser();

        FileDocument file = fileRepository
                   .findByIdAndUser(fileId, user)
                   .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        List<DocumentChunk> chunks =
                    retrievalService.retrieveRelevantChunks( file, request.question(), 5);

        log.info("Retrieved {} relevant chunks for file: {}", chunks.size(), file.getId());

        chunks.forEach(chunk ->
        log.debug(
        "Retrieved chunk {} ({} chars)",
        chunk.getChunkIndex(),
        chunk.getContent().length()
        )
        );
        
        chunks.sort(Comparator.comparingInt(DocumentChunk::getChunkIndex));

        String context = chunks.stream()
                .map(DocumentChunk::getContent)
                .collect(Collectors.joining("\n\n"));
        
        String answer =
                groqClient.generateAnswer(context, request.question()); 
                   
        ChatMessage chatMessage = ChatMessage.builder()
                    .question(request.question())
                    .answer(answer)
                    .modelUsed("llama-3.3-70b-versatile")
                    .createdAt(LocalDateTime.now())  
                    .file(file)
                    .user(user)
                    .build();

                    ChatMessage saved = chatRepository.save(chatMessage);

                    return ChatMapper.toResponse(saved);


        
    }
    
    @Override
    public List<ChatResponse> getChatHistory(UUID fileId){

        User user = SecurityUtils.getCurrentUser();

        FileDocument file = fileRepository
                .findByIdAndUser(fileId, user)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        return chatRepository
                 .findByFileAndUserOrderByCreatedAtAsc(file, user)
                 .stream()
                 .map(ChatMapper::toResponse)
                 .toList();
        
    }

    @Override
    public void deleteChatHistory(UUID fileId){

        User user = SecurityUtils.getCurrentUser();

        FileDocument file = fileRepository
              .findByIdAndUser(fileId, user)
              .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        chatRepository.deleteByFileAndUser(file, user);

    }



}
