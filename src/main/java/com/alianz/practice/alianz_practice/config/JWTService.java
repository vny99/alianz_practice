package com.alianz.practice.alianz_practice.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
   private static String SECRET_KEY = "3F4428472B4B6250655368566D597133743677397A244326452948404D635166";

   public String extractUser(String token) {
      return extractClaim(token, Claims::getSubject);
   }

   public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
   }

   private Claims extractAllClaims(String token) {
      return getJwtParserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
   }

   private JwtParserBuilder getJwtParserBuilder() {
      return Jwts.parserBuilder();
   }

   private Key getSigningKey() {
      return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
   }

   public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
      // Calendar calendar = Calendar.getInstance();
      // calendar.set(20, Calendar.APRIL, 15);
      // Date expirationDate = calendar.getTime();
      return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 3600000)).signWith(getSigningKey()).compact();
   }

   public String generateToken(UserDetails userDetails) {
      return generateToken(new HashMap<>(), userDetails);
   }

   public boolean isTokenValid(String token, UserDetails userDetails) {
      final String username = extractUser(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
   }

   private boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
   }

   private Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
   }

}
