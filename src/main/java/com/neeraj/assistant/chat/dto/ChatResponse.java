package com.neeraj.assistant.chat.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;


public record ChatResponse(

    UUID id,

    String question,

    String answer,

    LocalDateTime createdAt

) {

}
