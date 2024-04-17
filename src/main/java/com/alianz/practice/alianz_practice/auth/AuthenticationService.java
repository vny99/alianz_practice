package com.alianz.practice.alianz_practice.auth;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alianz.practice.alianz_practice.Entity.Account;
import com.alianz.practice.alianz_practice.builder.AccountBuilder;
import com.alianz.practice.alianz_practice.config.JWTService;
import com.alianz.practice.alianz_practice.exceptions.UserAlreadyExistsException;
import com.alianz.practice.alianz_practice.repository.AccountRespository;
import com.alianz.practice.alianz_practice.requests.AuthenticationRequest;
import com.alianz.practice.alianz_practice.requests.RegistrationRequest;

import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {

 
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;;

    @Autowired
    private AccountRespository repo;
    

    public void register(RegistrationRequest request) {
       if(repo.findByEmail(request.getEmail()).isPresent()) {
           throw new UserAlreadyExistsException("User already exists");
       }
      Account account =  AccountBuilder.from(request,encoder).build();
        repo.save(account);
        jwtService.generateToken(account);
    }

    public void authenticateUser(AuthenticationRequest request, HttpServletResponse response) {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())); 
        Account account = repo.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = jwtService.generateToken(account);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }

    public void logout (HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public void refreshToken(HttpServletResponse response, HttpServletRequest request) {
        String pastToken = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("token")){
                pastToken = cookie.getValue();
                break;
            }
        }
        String user = jwtService.extractUser(pastToken);
        Account account = repo.findByEmail(user).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = jwtService.generateToken(account);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }


}
