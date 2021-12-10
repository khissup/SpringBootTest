package com.example.ksrdemo.api.schema.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {


  private String message;

  private String accessToken;
  private String refreshToken;
  private Long id;
  private String username;
  private String email;
}
