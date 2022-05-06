package com.example.ksrdemo.api.schema.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestBody {

  @ApiModelProperty(example = "kanghyeok33")
  private String username;

  @ApiModelProperty(example = "password")
  private String password;
}
