package com.neeraj.assistant.file.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.neeraj.assistant.file.entity.UploadStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FileResponse {
    private UUID id;
    private String originalName;
    private Long fileSize;
    private UploadStatus status;
    private LocalDateTime uploadedAt;

}
