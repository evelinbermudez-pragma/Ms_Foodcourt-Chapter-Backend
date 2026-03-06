package com.chapter.foodcourt.insfrastructure.output.jpa.exception;

public class ExistsRestaurantException extends RuntimeException{
    public ExistsRestaurantException(String message) {
        super(message);
    }
}
