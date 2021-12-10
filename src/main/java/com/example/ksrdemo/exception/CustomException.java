package com.example.ksrdemo.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private String message;

    public CustomException(String message){
        this.message =message;
    }
}
