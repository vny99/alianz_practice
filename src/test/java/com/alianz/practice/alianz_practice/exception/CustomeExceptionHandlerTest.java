package com.alianz.practice.alianz_practice.exception;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.alianz.practice.alianz_practice.advicer.CustomExceptionHandler;
import com.alianz.practice.alianz_practice.exceptions.ProductCouldNotBeAdderException;
import com.alianz.practice.alianz_practice.exceptions.ProductsCanNotBeLoadedException;
import com.alianz.practice.alianz_practice.exceptions.UserAlreadyExistsException;
import com.alianz.practice.alianz_practice.response.Response;

import jakarta.annotation.Resource;

public class CustomeExceptionHandlerTest {

    @InjectMocks
    @Resource
    private CustomExceptionHandler customExceptionHandler;

    @Mock
    private Response response;

    Map<String, Object> responseMap;

    private static final String STATE = "STATE";
    private static final String STATUS = "STATUS";
    private static final String PAYLOAD = "PAYLOAD";
    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "ERROR";

    /**
     * set up mocks for tests
     * @throws Exception on Error
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        responseMap = new TreeMap <>();
    }

    @Test
    public void testHandleProductNotFound() {
        when(response.buildResponse("Product not found", HttpStatus.NOT_FOUND)).thenReturn(responseMap);
        assertEquals(responseMap, customExceptionHandler.handleProductNotFound(new NoSuchElementException("Product not found")));
    }

    @Test
    public void testHandleproductsCanNotBeLoaded() {
        when(response.buildResponse("Products can not be loaded", HttpStatus.BAD_REQUEST)).thenReturn(responseMap);
        assertEquals(responseMap, customExceptionHandler.handleproductsCanNotBeLoaded(new ProductsCanNotBeLoadedException("Products can not be loaded")));
    }

    @Test
    public void testHandleProductCouldNotBeAdded() {
        when(response.buildResponse("Product could not be added", HttpStatus.INTERNAL_SERVER_ERROR)).thenReturn(responseMap);
        assertEquals(responseMap, customExceptionHandler.handleProductCouldNotBeAdded(new ProductCouldNotBeAdderException("Product could not be added")));
    }

    @Test
    public void testHandleIllegalArgumentException() {
        when(response.buildResponse("Illegal Argument", HttpStatus.BAD_REQUEST)).thenReturn(responseMap);
        assertEquals(responseMap, customExceptionHandler.handleIllegalArgumentException(new IllegalArgumentException("Illegal Argument")));
    }

    @Test
    public void testHandleUserAlreadyExistsException() {
        when(response.buildResponse("User already exists", HttpStatus.BAD_REQUEST)).thenReturn(responseMap);
        assertEquals(responseMap, customExceptionHandler.handleUserAlreadyExistsException(new UserAlreadyExistsException("User already exists")));
    }

    @Test
    public void testHandleUsernameNotFoundException() {
        when(response.buildResponse("Username not found", HttpStatus.BAD_REQUEST)).thenReturn(responseMap);
        assertEquals(responseMap, customExceptionHandler.handleUsernameNotFoundException(new UsernameNotFoundException("Username not found")));
    }

    @Test
    public void testHandleBadCredentialsException() {
        when(response.buildResponse("Bad credentials", HttpStatus.FORBIDDEN)).thenReturn(responseMap);
        assertEquals(responseMap, customExceptionHandler.handleBadCredentialsException(new BadCredentialsException("Bad credentials")));
    }

    public Map<String, Object> buildResponse(Object payLoad, HttpStatus status) {
        if (HttpStatus.OK.equals(status)) {
            responseMap.put(STATE, SUCCESS);
        } else {
            responseMap.put(STATE, ERROR);
        }
        responseMap.put(STATUS, status);
        responseMap.put(PAYLOAD, payLoad);
        return responseMap;
    }
    
}
