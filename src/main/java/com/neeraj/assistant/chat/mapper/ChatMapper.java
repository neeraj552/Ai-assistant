package com.neeraj.assistant.chat.mapper;

import com.neeraj.assistant.chat.dto.ChatResponse;

public class ChatMapper {
    private ChatMapper(){

    }

    public static ChatResponse toResponse(ChatResponse chatMessage){

        return new ChatResponse(
            chatMessage.id(),
            chatMessage.question(),
            chatMessage.answer(),
            chatMessage.createdAt()

        );
    }

}
