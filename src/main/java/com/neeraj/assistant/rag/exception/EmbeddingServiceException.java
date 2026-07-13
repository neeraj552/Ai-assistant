package com.neeraj.assistant.rag.exception;

public class EmbeddingServiceException extends RuntimeException{

    public EmbeddingServiceException(String message){
        super(message);
    }

}
