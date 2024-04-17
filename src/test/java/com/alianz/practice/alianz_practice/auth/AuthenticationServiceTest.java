package com.alianz.practice.alianz_practice.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.alianz.practice.alianz_practice.Entity.Account;
import com.alianz.practice.alianz_practice.Entity.Role;
import com.alianz.practice.alianz_practice.config.JWTService;
import com.alianz.practice.alianz_practice.exceptions.UserAlreadyExistsException;
import com.alianz.practice.alianz_practice.repository.AccountRespository;
import com.alianz.practice.alianz_practice.requests.AuthenticationRequest;
import com.alianz.practice.alianz_practice.requests.RegistrationRequest;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationServiceTest {
    @InjectMocks
    @Resource
    private AuthenticationService authenticationService;

    @Mock
    private AccountRespository repo;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JWTService jwtService;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock 
    private AuthenticationManager authenticationManager;

    private static final String USER_ALREADY_EXISTS = "User already exists";
    

    /**
     * set up for the test
     * 
     * @throws Exception on Error
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegister() {
        RegistrationRequest request = createRegistrationRequest();

        when(repo.findByEmail(anyString())).thenReturn(Optional.empty());
        when(encoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(Account.class))).thenReturn("token");

        authenticationService.register(request);
        verify(repo, times(1)).save(any(Account.class));
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        RegistrationRequest request = createRegistrationRequest();

        when(repo.findByEmail(anyString())).thenReturn(Optional.of(new Account()));

        Exception e = assertThrows(UserAlreadyExistsException.class, () -> {
            authenticationService.register(request);
        });
        assertEquals(USER_ALREADY_EXISTS, e.getMessage());
    }


    @Test
    public void testAuthenticateUser() {
        AuthenticationRequest request = createAuthenticationRequest();

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(repo.findByEmail(anyString())).thenReturn(Optional.of(new Account()));
        authenticationService.authenticateUser(request, httpServletResponse);

        verify(httpServletResponse, times(1)).addCookie(any());
    }

    @Test
    public void testLogout() {
        authenticationService.logout(httpServletResponse);
        verify(httpServletResponse, times(1)).addCookie(any());
    }

    @Test
    public void testRefreshToken(){
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("token", "token");
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        when(jwtService.extractUser(anyString())).thenReturn("email");
        when(repo.findByEmail(anyString())).thenReturn(Optional.of(new Account()));
        when(jwtService.generateToken(any(Account.class))).thenReturn("token");

        authenticationService.refreshToken(httpServletResponse, httpServletRequest);
        verify(httpServletResponse, times(1)).addCookie(any());
    }

    private AuthenticationRequest createAuthenticationRequest() {
        return new AuthenticationRequest("email", "password");
    }

    private RegistrationRequest createRegistrationRequest() {
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("email");
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setPassword("password");
        request.setRole(Role.ADMIN.name());
        return request;
    }
}
