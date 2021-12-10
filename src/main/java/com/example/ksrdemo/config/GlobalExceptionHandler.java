package com.example.ksrdemo.config;

import com.example.ksrdemo.api.schema.CommonResponse;
import com.example.ksrdemo.entity.exception.DomainException;
import com.example.ksrdemo.exception.CustomException;
import com.example.ksrdemo.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public CommonResponse handleValidationException(MethodArgumentNotValidException ex) {

    String errorMessages =
        String.join(
            ",",
            ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList()));

    return new CommonResponse(errorMessages);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public CommonResponse handleValidationException(ConstraintViolationException ex) {

    return new CommonResponse(ex.getLocalizedMessage());
  }


  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(CustomException.class)
  public CommonResponse handleCustomException(CustomException ex) {

    return new CommonResponse(ex.getLocalizedMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(DomainException.class)
  public CommonResponse handleDomainException(DomainException ex) {
    return new CommonResponse(ex.getMessage());
  }


  // 커스텀 Auth Exception

  @ExceptionHandler(BadCredentialsException.class)
  protected ResponseEntity<CommonResponse> handleBadCredentialsException(
      BadCredentialsException e) {

    return new ResponseEntity<>(
        new CommonResponse("아이디와 비밀번호가 일치하지 않습니다.", e.getLocalizedMessage()),
        HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(TokenRefreshException.class)
  protected ResponseEntity<CommonResponse> handleTokenRefreshException(TokenRefreshException e) {

    return new ResponseEntity<>(
        new CommonResponse("토큰을 재발급할수 없습니다.", e.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
    }
  //
  //  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  //  @ExceptionHandler(AccessDeniedException.class)
  //  protected CommonResponse handleAccessDeniedException(AccessDeniedException e) {
  //
  //    return new CommonResponse("access Denied");
  //  }
  //
  //    @ExceptionHandler(InsufficientAuthenticationException.class)
  //    protected ResponseEntity<CommonResponse>
  // handleInsufficientAuthenticationException(InsufficientAuthenticationException e) {
  //
  //        log.info("handleInsufficientAuthenticationException", e);
  //
  //        CommonResponse response = CommonResponse.builder()
  //                .code(ErrorCode.AUTHENTICATION_FAILED.getCode())
  //                .message(ErrorCode.AUTHENTICATION_FAILED.getMessage())
  //                .status(ErrorCode.AUTHENTICATION_FAILED.getStatus())
  //                .build();
  //
  //        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  //    }
}
