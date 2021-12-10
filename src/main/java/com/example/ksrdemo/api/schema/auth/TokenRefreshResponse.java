package com.example.ksrdemo.api.schema.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenRefreshResponse {
  private String accessToken;
  private String refreshToken;

}