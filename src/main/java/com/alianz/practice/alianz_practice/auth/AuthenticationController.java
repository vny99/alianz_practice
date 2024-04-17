package com.alianz.practice.alianz_practice.auth;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alianz.practice.alianz_practice.requests.AuthenticationRequest;
import com.alianz.practice.alianz_practice.requests.RegistrationRequest;
import com.alianz.practice.alianz_practice.response.Response;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/alianz/auth/")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private Response response;

    private static final String SUCCESSFULL_REGISTRATION = "User registered successfully";
    private static final String SUCCESSFULL_LOGIN = "User logged in successfully";
    private static final String SUCCESSFULL_LOGOUT = "User logged out successfully";
    private static final String TOKEN_REFRESHED = "Token refreshed successfully";


    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody RegistrationRequest request, BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(response.buildResponse(getValidationErrors(result.getAllErrors()), HttpStatus.BAD_REQUEST));
        }
        authenticationService.register(request);
        return ResponseEntity.ok(response.buildResponse(SUCCESSFULL_REGISTRATION, HttpStatus.OK));
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AuthenticationRequest request, BindingResult result, HttpServletResponse httpResponse) {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(response.buildResponse(getValidationErrors(result.getAllErrors()), HttpStatus.BAD_REQUEST));
        }
        authenticationService.authenticateUser(request, httpResponse);
        return ResponseEntity.ok(response.buildResponse(SUCCESSFULL_LOGIN, HttpStatus.OK));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletResponse httpResponse) {
        authenticationService.logout(httpResponse);
        return ResponseEntity.ok(response.buildResponse(SUCCESSFULL_LOGOUT, HttpStatus.OK));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<Map<String, Object>> refreshToken(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        authenticationService.refreshToken(httpServletResponse, httpServletRequest);
        return ResponseEntity.ok(response.buildResponse(TOKEN_REFRESHED, HttpStatus.OK));
    }

    private List<String> getValidationErrors(List<ObjectError> allErrors) {
        return allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
    }
}
