package com.example.ksrdemo.service.exception;

import com.example.ksrdemo.exception.CustomException;

public class EntityDoesNotExistException extends CustomException {

  public EntityDoesNotExistException(Long pk, Class clazz) {
    super(String.format("%s (id=%d)가 존재하지 않습니다.", clazz.getSimpleName(), pk));
  }
}
