package com.neeraj.assistant.common.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.neeraj.assistant.chat.exception.AIServiceException;
import com.neeraj.assistant.file.exception.FileStorageException;
import com.neeraj.assistant.file.exception.InvalidFileException;
import com.neeraj.assistant.file.exception.ResourceNotFoundException;
import com.neeraj.assistant.rag.exception.EmbeddingServiceException;
import com.neeraj.assistant.summary.exception.PdfExtractionException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ErrorResponse> handledInvalidFile(
            InvalidFileException ex, 
            HttpServletRequest request ){

                 ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(response);


    }
    
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponse> handledStorageException(
            FileStorageException ex,
            HttpServletRequest request){

                ErrorResponse response = ErrorResponse.builder()
                     .timestamp(LocalDateTime.now())
                     .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                     .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                     .message(ex.getMessage())
                     .path(request.getRequestURI())
                     .build();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(response);

    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handledNotFound(
            ResourceNotFoundException ex, 
            HttpServletRequest request){

                ErrorResponse response = ErrorResponse.builder()
                     .timestamp(LocalDateTime.now())
                     .status(HttpStatus.NOT_FOUND.value())
                     .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                     .message(ex.getMessage())
                     .path(request.getRequestURI())
                     .build();

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(response);


    }

    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handledGenericException(
            Exception ex, 
            HttpServletRequest request){

                ErrorResponse response = ErrorResponse.builder()
                     .timestamp(LocalDateTime.now())
                     .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                     .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                     .message(ex.getMessage())
                     .path(request.getRequestURI())
                     .build();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(response);

    }

    
    @ExceptionHandler(PdfExtractionException.class)
    public ResponseEntity<ErrorResponse> handledPDFExtraction(
            PdfExtractionException ex,
            HttpServletRequest request){

                ErrorResponse response = ErrorResponse.builder()
                     .timestamp(LocalDateTime.now())
                     .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                     .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                     .message(ex.getMessage())
                     .path(request.getRequestURI())
                     .build();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(response);

            }
    @ExceptionHandler(AIServiceException.class)
    public ResponseEntity<ErrorResponse>handledAiException(
        AIServiceException ex,
        HttpServletRequest request
    ){
                ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

    }
    
    @ExceptionHandler(EmbeddingServiceException.class)
    public ResponseEntity<ErrorResponse>handledJinaAiException(
        EmbeddingServiceException ex,
        HttpServletRequest request

    ){
         ErrorResponse response = ErrorResponse.builder()
              .timestamp(LocalDateTime.now())
              .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
              .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
              .message(ex.getMessage())
              .path(request.getRequestURI())
              .build();
        
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
}
}