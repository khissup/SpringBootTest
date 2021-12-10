package com.example.ksrdemo.api.schema.auth;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenRefreshRequestBody {
  @NotBlank private String refreshToken;
}
