package com.example.transactional_service.exception;



public class TransferFailedException extends RuntimeException {

    public TransferFailedException(String message) {
        super(message);
    }
}