package com.example.ksrdemo.api.schema.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SignupRequest {
    @ApiModelProperty(example = "kanghyeok33")
    private String username;
    @ApiModelProperty(example = "password")
    private String password;
    @ApiModelProperty(example = "khissup@naver.com")
    private String email;

}
