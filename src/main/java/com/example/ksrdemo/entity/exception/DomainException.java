package com.example.ksrdemo.entity.exception;

import com.example.demo.exception.CustomException;

public class DomainException extends CustomException {

    public DomainException(String message) {
        super(message);
    }
}
