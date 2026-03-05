package com.chapter.foodcourt.domain.exception;

public class ClientHasActiveOrderException extends RuntimeException {
    public ClientHasActiveOrderException(String message) {
        super(message);
    }
}
