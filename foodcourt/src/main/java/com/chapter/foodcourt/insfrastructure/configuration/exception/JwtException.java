package com.chapter.foodcourt.insfrastructure.configuration.exception;

public class JwtException extends RuntimeException{
    public  JwtException(String msg) {
        super(msg);
    }
}
