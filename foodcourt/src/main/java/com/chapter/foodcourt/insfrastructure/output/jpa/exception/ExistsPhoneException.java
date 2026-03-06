package com.chapter.foodcourt.insfrastructure.output.jpa.exception;

public class ExistsPhoneException extends RuntimeException {
    public ExistsPhoneException(String message) {
        super(message);
    }
}
