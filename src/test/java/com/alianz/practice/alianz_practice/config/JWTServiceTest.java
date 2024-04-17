package com.alianz.practice.alianz_practice.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.alianz.practice.alianz_practice.Entity.Account;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.annotation.Resource;

public class JWTServiceTest {
    
    @InjectMocks
    @Resource
    private JWTService jwtService;

    @Mock
    JwtParserBuilder jwtParserBuilder;

    @Mock
    JwtParser jwtParser;

    @Mock
    Jws<Claims> jws;


    private Claims claims;




    private static final String TOKEN= "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqb2huQGV4LmNvbSIsImlhdCI6MTcxMzMzMTc0NCwiZXhwIjoxOTAyNDYxMzQ0fQ.m1SNqzZ2JeZ9MXVHihlEMRb0dG31BXRPyNY1bsHlJKDb0cLdnTA3Y9mCYAyf8zUT";

    private static final String INVALID_TOKEN ="invalid Token";

    private static final String SAMPLE_USER ="john@ex.com";

    private static final String INVALID_TOKEN_MESSAGE = "JWT strings must contain exactly 2 period characters. Found: 0";

    /**
     * set up for intiailizing the mocks
     * @throws Exception on Error
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        claims = Jwts.claims();
    }
    @Test
    public void testExtractUser() {
        claims.setSubject(SAMPLE_USER);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.APRIL, 15);
        Date expirationDate = calendar.getTime();
        claims.setExpiration(expirationDate);
    
        when(jwtParserBuilder.setSigningKey(anyString())).thenReturn(jwtParserBuilder);
        when(jwtParserBuilder.build()).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(anyString())).thenReturn(jws);
        when(jws.getBody()).thenReturn(claims);


        String user = jwtService.extractUser(TOKEN);
    
        assertEquals(SAMPLE_USER, user);
    }

    @Test
    public void testExtractUserWithInvalidToken() {
        when(jwtParserBuilder.setSigningKey(anyString())).thenReturn(jwtParserBuilder);
        when(jwtParserBuilder.build()).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(anyString())).thenReturn(jws);

        Exception exception = assertThrows(MalformedJwtException.class, () -> {
            jwtService.extractUser(INVALID_TOKEN);
        });

        assertEquals(INVALID_TOKEN_MESSAGE, exception.getMessage());
    }

    @Test
    public void testGenerateToken() {
        Account account = new Account();
        String token = jwtService.generateToken(account);
       
        assertNotNull(token);
    }

    @Test
    public void testIsTokenValid(){
        claims.setSubject(SAMPLE_USER);
    
        when(jwtParserBuilder.setSigningKey(anyString())).thenReturn(jwtParserBuilder);
        when(jwtParserBuilder.build()).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(anyString())).thenReturn(jws);
        when(jws.getBody()).thenReturn(claims);

        Account account = new Account();
        account.setEmail(SAMPLE_USER);
        boolean isValid = jwtService.isTokenValid(TOKEN, account);
        assertTrue(isValid);
    }

    @Test
    public void testIsTokenValidWithInvalidToken(){
        when(jwtParserBuilder.setSigningKey(anyString())).thenReturn(jwtParserBuilder);
        when(jwtParserBuilder.build()).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(anyString())).thenReturn(jws);
        when(jws.getBody()).thenReturn(claims);

        Exception exception = assertThrows(MalformedJwtException.class, () -> {
            jwtService.extractUser(INVALID_TOKEN);
        });

        assertEquals(INVALID_TOKEN_MESSAGE, exception.getMessage());
    }
   
}
