package com.neeraj.assistant.chat.service;

import java.util.List;
import java.util.UUID;

import com.neeraj.assistant.chat.dto.ChatRequest;
import com.neeraj.assistant.chat.dto.ChatResponse;

public interface ChatService {

    ChatResponse askQuestion(UUID fileId, ChatRequest request);

    List<ChatResponse> getChatHistory(UUID fileId);

    void deleteChatHistory(UUID fileId);

}
