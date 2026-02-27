package com.chapter.foodcourt.insfrastructure.output.jpa.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
