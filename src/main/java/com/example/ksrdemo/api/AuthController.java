package com.example.ksrdemo.api;

import com.example.ksrdemo.api.schema.auth.*;
import com.example.ksrdemo.entity.Member;
import com.example.ksrdemo.entity.RefreshToken;
import com.example.ksrdemo.entity.repository.MemberRepository;
import com.example.ksrdemo.exception.CustomException;
import com.example.ksrdemo.exception.TokenRefreshException;
import com.example.ksrdemo.security.JWTUtils;
import com.example.ksrdemo.service.RefreshTokenService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JWTUtils jwtUtils;
  private final RefreshTokenService refreshTokenService;
  private final MemberRepository memberRepository;
  private final PasswordEncoder encoder;
  // start test
  @ApiOperation(value = "access 토큰 얻기 (로그인)")
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestBody loginRequestBody) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequestBody.getUsername(), loginRequestBody.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    Member member = (Member) authentication.getPrincipal();

    String accessToken = jwtUtils.generateJwtToken(member);
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(member.getId());

    return ResponseEntity.ok(
        AuthResponse.builder()
            .message("I am message")
            .accessToken(accessToken)
            .refreshToken(refreshToken.getToken())
            .id(member.getId())
            .username(member.getUsername())
            .email(member.getEmail())
            .build());
  }

  @ApiOperation(value = "refresh 토큰으로 access 토큰 재발급")
  @PostMapping("/refresh-token")
  public ResponseEntity<TokenRefreshResponse> reissueAccessToken(
      @RequestBody @Valid TokenRefreshRequestBody refreshRequestBody) {
    String requestRefreshToken = refreshRequestBody.getRefreshToken();

    RefreshToken refreshToken =
        refreshTokenService
            .findByToken(requestRefreshToken)
            .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "존재하지 않는 토큰입니다."));

    refreshTokenService.verifyExpiration(refreshToken);
    String token = jwtUtils.generateTokenFromUsername(refreshToken.getMember().getUsername());
    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
  }

  @ApiOperation(value = "회원가입 (호출스크립트 편의성을 위해 만듬)")
  @PostMapping("/signup")
  @Transactional
  public ResponseEntity<Member> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

    // Create new user's account
    Member member =
        new Member(
            signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()),
            15f);

    if (memberRepository.existsByUsernameOrEmail(
        signUpRequest.getUsername(), signUpRequest.getEmail())) {
      throw new CustomException("username 또는 email이 중복입니다.");
    }
    memberRepository.save(member);

    return ResponseEntity.ok(member);
  }
}
