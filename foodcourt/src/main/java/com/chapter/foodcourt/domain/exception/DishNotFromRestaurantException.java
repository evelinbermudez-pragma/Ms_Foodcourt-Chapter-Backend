package com.chapter.foodcourt.domain.exception;

public class DishNotFromRestaurantException extends RuntimeException {
    public DishNotFromRestaurantException(String message) {
        super(message);
    }
}
