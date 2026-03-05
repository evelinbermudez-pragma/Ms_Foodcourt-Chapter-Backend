package com.chapter.foodcourt.domain.exception;

public class OrderNotPendingException extends RuntimeException {
    public OrderNotPendingException(String message) {
        super(message);
    }
}
