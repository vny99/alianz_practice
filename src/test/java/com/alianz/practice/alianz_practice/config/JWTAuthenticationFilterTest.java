package com.alianz.practice.alianz_practice.config;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.alianz.practice.alianz_practice.Entity.Account;
import com.alianz.practice.alianz_practice.Entity.Role;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilterTest {
    @InjectMocks
    @Resource
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JWTService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private FilterChain filterChain;


    private static final String AUTH_WHITELIST = "/alianz/auth/**";
    
    private static final String AUTH_BLOCKLIST ="/api/get";

    private static final String USER_NOT_FOUND = "User not found";


    /**
     * set up mocks for tests
     * @throws Exception on Error
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testDoFilterInternalForWhiltListedMatches() throws Exception {
        when(httpServletRequest.getCookies()).thenReturn(new Cookie[1]);
        when(httpServletRequest.getServletPath()).thenReturn(AUTH_WHITELIST);
        
        jwtAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testDoFilterInternalWithNoCookies() throws Exception {
        when(httpServletRequest.getCookies()).thenReturn(null);
        when(httpServletRequest.getServletPath()).thenReturn(AUTH_BLOCKLIST);
        
        jwtAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testDoFilterInternalForNullToken() throws Exception {
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("token", null);
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        when(httpServletRequest.getServletPath()).thenReturn(AUTH_BLOCKLIST);
        
        jwtAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(httpServletRequest, httpServletResponse);
    }


    @Test
    public void testDoFilterInternalForBadCredentials() throws Exception {
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("token", "token");
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        when(httpServletRequest.getServletPath()).thenReturn(AUTH_BLOCKLIST);
        when(jwtService.extractUser("token")).thenReturn("user");
        when(userDetailsService.loadUserByUsername("user")).thenThrow(new UsernameNotFoundException(USER_NOT_FOUND));

        Exception e = assertThrows(UsernameNotFoundException.class, () -> {
            jwtAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
        });
        e.getMessage().equals(USER_NOT_FOUND);
    }

    @Test
    public void testDoFilterInternalForValidToken() throws Exception {
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("token", "token");
        Account account = createAccount();
        when(httpServletRequest.getCookies()).thenReturn(cookies);
        when(httpServletRequest.getServletPath()).thenReturn(AUTH_BLOCKLIST);
        when(jwtService.extractUser("token")).thenReturn("user");
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(account);
        when(jwtService.isTokenValid(anyString(), any(Account.class))).thenReturn(true);
       
        jwtAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(httpServletRequest, httpServletResponse);
    }

    private Account createAccount() {
        Account account = new Account();
        account.setRole(Role.ADMIN);
        return account;
    }

}
