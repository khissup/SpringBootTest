package com.example.ksrdemo.security;

import com.example.demo.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Slf4j
@Component
public class JWTUtils {
  @Value("${jwt.base64Secret}")
  private String jwtSecret;

  @Value("${jwt.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Member userPrincipal) {
    return generateTokenFromUsername(userPrincipal.getUsername());
  }

  public String generateTokenFromUsername(String username) {
    System.out.println(String.valueOf(jwtExpirationMs));
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) throws UnsupportedEncodingException {
    return Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes("utf-8")))
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {

      return Jwts.parserBuilder()
              .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes("utf-8")))
              .build()
              .parseClaimsJws(authToken)
              .getBody()
          != null;

    } catch (MalformedJwtException e) {
      log.info("손상된(비정상적인) access 토큰입니다.");
    } catch (ExpiredJwtException e) {
      log.info("만료된 토큰입니다. 다시 로그인해주세요.");
    } catch (UnsupportedJwtException e) {
      log.info("포맷이 맞지 않는 access 토큰입니다.");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return false;
  }
}
