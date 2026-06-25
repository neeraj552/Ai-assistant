package com.neeraj.assistant.file.dto;

import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileDownloadResponse {

    private Resource resource;

    private String originalFileName;

    private String contentType;

}
