package com.alianz.practice.alianz_practice.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.Binding;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;

import com.alianz.practice.alianz_practice.Entity.Role;
import com.alianz.practice.alianz_practice.requests.RegistrationRequest;
import com.alianz.practice.alianz_practice.response.Response;
import com.alianz.practice.alianz_practice.response.UserProfile;
import com.alianz.practice.alianz_practice.service.AccountService;

import jakarta.annotation.Resource;

public class AccountControllerTest {
    @InjectMocks @Resource private AccountController accountController;

    @Mock private AccountService accountService;

    @Mock private Response response;

    @Mock private BindingResult bindingResult;

    private static final String STATE = "STATE";
    private static final String STATUS = "STATUS";
    private static final String PAYLOAD = "PAYLOAD";
    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "ERROR";
    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PASSWORD = "password";

    private static final String USER_NOT_FOUND = "User not found";

    private static final String ACCOUNT_DELETED_SUCCESSFULL = "Account deleted Successfull : ";

    private static final String ACCOUNT_UPDATAION_SUCCESSFULL = "Account updated Successfull : ";

    private Map<String, Object> responseMap;

    /**
     * set up mocks for tests
     * 
     * @throws Exception on Error
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        responseMap = new TreeMap<>();
    }   

    @Test
    public void testGetAccount(){
        UserProfile userProfile = createUserProfile();
        when(accountService.getUserProfile(anyString())).thenReturn(userProfile);
        when(response.buildResponse(any(), any(HttpStatus.class))).thenReturn(buildResponse(userProfile, HttpStatus.OK));

        Map<String, Object> responseMap = accountController.getAccount(EMAIL).getBody();

        assertNotNull(responseMap);
        assertEquals(SUCCESS, responseMap.get(STATE));
        assertEquals(HttpStatus.OK, responseMap.get(STATUS));
        assertEquals(userProfile, responseMap.get(PAYLOAD));
    }

    @Test
    public void testGetAccountNotFound(){
        when(accountService.getUserProfile(anyString())).thenThrow(new UsernameNotFoundException(USER_NOT_FOUND));

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            accountController.getAccount(EMAIL);
        });

        assertEquals(USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testGetAllAccounts(){
        UserProfile userProfile = createUserProfile();
        when(accountService.getAllAccounts()).thenReturn(List.of(userProfile));
        when(response.buildResponse(any(), any(HttpStatus.class))).thenReturn(buildResponse(List.of(userProfile), HttpStatus.OK));

        Map<String, Object> responseMap = accountController.getAllAccounts().getBody();

        assertNotNull(responseMap);
        assertEquals(SUCCESS, responseMap.get(STATE));
        assertEquals(HttpStatus.OK, responseMap.get(STATUS));
        assertEquals(List.of(userProfile), responseMap.get(PAYLOAD));
    }

    @Test
    public void testGetAllAccountsEmpty(){
        when(accountService.getAllAccounts()).thenReturn(List.of());
        when(response.buildResponse(any(), any(HttpStatus.class))).thenReturn(buildResponse(List.of(), HttpStatus.OK));

        Map<String, Object> responseMap = accountController.getAllAccounts().getBody();

        assertNotNull(responseMap);
        assertEquals(SUCCESS, responseMap.get(STATE));
        assertEquals(HttpStatus.OK, responseMap.get(STATUS));
        assertEquals(List.of(), responseMap.get(PAYLOAD));
    }

    @Test
    public void testDeleteAccount(){
        Mockito.doNothing().when(accountService).deleteAccount(anyString());
        when(response.buildResponse(any(), any(HttpStatus.class))).thenReturn(buildResponse(ACCOUNT_DELETED_SUCCESSFULL + EMAIL, HttpStatus.OK));

        Map<String, Object> responseMap = accountController.deleteAccount(EMAIL).getBody();

        assertNotNull(responseMap);
        assertEquals(SUCCESS, responseMap.get(STATE));
        assertEquals(HttpStatus.OK, responseMap.get(STATUS));
        assertEquals(ACCOUNT_DELETED_SUCCESSFULL + EMAIL, responseMap.get(PAYLOAD));
    }

    @Test
    public void testDeleteAccountNotFound(){
        Mockito.doThrow(new UsernameNotFoundException(USER_NOT_FOUND)).when(accountService).deleteAccount(anyString());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            accountController.deleteAccount(EMAIL);
        });

        assertEquals(USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testUpdateAccount(){
        RegistrationRequest request = createRegistrationRequest();
        Mockito.doNothing().when(accountService).updateAccount(anyString(), any(RegistrationRequest.class));
        when(response.buildResponse(any(), any(HttpStatus.class))).thenReturn(buildResponse(ACCOUNT_UPDATAION_SUCCESSFULL + EMAIL, HttpStatus.OK));
        when(bindingResult.hasErrors()).thenReturn(false);

        Map<String, Object> responseMap = accountController.updateAccount(request, EMAIL, bindingResult).getBody();

        assertNotNull(responseMap);
        assertEquals(SUCCESS, responseMap.get(STATE));
        assertEquals(HttpStatus.OK, responseMap.get(STATUS));
        assertEquals(ACCOUNT_UPDATAION_SUCCESSFULL + EMAIL, responseMap.get(PAYLOAD));
    }

    @Test
    public void testUpdateAccountWithErrors(){
        RegistrationRequest request = createRegistrationRequest();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(response.buildResponse(any(), any(HttpStatus.class))).thenReturn(buildResponse(List.of(), HttpStatus.BAD_REQUEST));

        Map<String, Object> responseMap = accountController.updateAccount(request, EMAIL, bindingResult).getBody();

        assertNotNull(responseMap);
        assertEquals(ERROR, responseMap.get(STATE));
        assertEquals(HttpStatus.BAD_REQUEST, responseMap.get(STATUS));
    }

    @Test
    public void testUpdateAccountNotFound(){
        RegistrationRequest request = createRegistrationRequest();
        Mockito.doThrow(new UsernameNotFoundException(USER_NOT_FOUND)).when(accountService).updateAccount(anyString(), any(RegistrationRequest.class));

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            accountController.updateAccount(request, EMAIL, bindingResult);
        });

        assertEquals(USER_NOT_FOUND, exception.getMessage());
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

    private RegistrationRequest createRegistrationRequest() {
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail(EMAIL);
        request.setFirstName(FIRST_NAME);
        request.setLastName(LAST_NAME);
        request.setPassword(PASSWORD);
        request.setRole(Role.ADMIN.name());
        return request;
    }

    private UserProfile createUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail(EMAIL);
        userProfile.setFirstName(FIRST_NAME);
        userProfile.setLastName(LAST_NAME);
        userProfile.setRole(Role.ADMIN.name());
        return userProfile;
    }
}
