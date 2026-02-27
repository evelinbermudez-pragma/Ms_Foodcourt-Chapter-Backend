package com.chapter.foodcourt.domain.exception;

public class UserIsNotOwnerException extends RuntimeException{
    public UserIsNotOwnerException(String message){
        super(message);
    }
}
