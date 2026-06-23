package com.neeraj.assistant.common.exception;

import java.time.LocalDateTime;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    public ResponseEntity<ErrorResponse> handledNotFound(){

    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handledGenericException(){

    }

}
