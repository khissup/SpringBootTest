package com.example.ksrdemo.api.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CommonResponse {
  private String message;
  private String detail;

  public CommonResponse(String message) {
    this.message = message;
  }
}
