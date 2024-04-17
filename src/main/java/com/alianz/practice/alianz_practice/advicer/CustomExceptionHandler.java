package com.alianz.practice.alianz_practice.advicer;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alianz.practice.alianz_practice.exceptions.ProductCouldNotBeAdderException;
import com.alianz.practice.alianz_practice.exceptions.ProductsCanNotBeLoadedException;
import com.alianz.practice.alianz_practice.exceptions.UserAlreadyExistsException;
import com.alianz.practice.alianz_practice.response.Response;

@RestControllerAdvice
public class CustomExceptionHandler {

    @Autowired
    private Response response; 


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Map<String, Object> handleProductNotFound(NoSuchElementException exception){
       return response.buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductsCanNotBeLoadedException.class)
    public Map<String, Object> handleproductsCanNotBeLoaded(ProductsCanNotBeLoadedException exception){
        return response.buildResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ProductCouldNotBeAdderException.class)
    public Map<String, Object> handleProductCouldNotBeAdded(ProductCouldNotBeAdderException exception){
        return response.buildResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, Object> handleIllegalArgumentException(IllegalArgumentException exception){
        return response.buildResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public Map<String, Object> handleUserAlreadyExistsException(UserAlreadyExistsException exception){
        return response.buildResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Map<String, Object> handleUsernameNotFoundException(UsernameNotFoundException exception){
        return response.buildResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    public Map<String, Object> handleBadCredentialsException(BadCredentialsException exception){
        return response.buildResponse(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

}
