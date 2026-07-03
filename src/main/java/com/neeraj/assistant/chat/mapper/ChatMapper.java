package com.neeraj.assistant.chat.mapper;

import com.neeraj.assistant.chat.dto.ChatResponse;
import com.neeraj.assistant.chat.entity.ChatMessage;

public class ChatMapper {
    private ChatMapper(){

    }

    public static ChatResponse toResponse(ChatMessage chatMessage){

        return new ChatResponse(
            chatMessage.getId(),
            chatMessage.getQuestion(),
            chatMessage.getAnswer(),
            chatMessage.getModelUsed(),
            chatMessage.getCreatedAt()

        );
    }

}
