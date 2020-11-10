package com.samuelbraga.rotinabackend.config.security;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samuelbraga.rotinabackend.exceptions.BaseException;
import com.samuelbraga.rotinabackend.modules.user.models.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthenticationService {
  
  @Value("${jwt.secret}")
  private String SECRET;

  @Value("${jwt.expiration}")
  private String EXPIRATION;
  
  public String createToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
        
    return Jwts.builder()
      .setIssuer("Rotina API")
      .setSubject(user.getId().toString())
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(EXPIRATION)))
      .signWith(SignatureAlgorithm.HS256, SECRET)
      .compact();
  }
  
  public boolean isTokenValid(String token) {
    try {
      Jwts.parser()
        .setSigningKey(SECRET)
        .parseClaimsJws(token);
      
      return true;
    } catch (Exception error) {
      return false;
    }
  }
  
  public UUID getUserId(String token) {
    Claims body = Jwts.parser()
      .setSigningKey(SECRET)
      .parseClaimsJws(token)
      .getBody();

    String id = body.getSubject();
    return UUID.fromString(id);
  }
}