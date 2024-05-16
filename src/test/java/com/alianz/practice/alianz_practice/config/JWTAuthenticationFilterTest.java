package com.alianz.practice.alianz_practice.config;

public class JWTAuthenticationFilterTest {
    // @InjectMocks
    // @Resource
    // private JWTAuthenticationFilter jwtAuthenticationFilter;

    // @Mock
    // private JWTService jwtService;

    // @Mock
    // private UserDetailsService userDetailsService;

    // @Mock
    // private HttpServletRequest httpServletRequest;

    // @Mock
    // private HttpServletResponse httpServletResponse;

    // @Mock
    // private FilterChain filterChain;


    // private static final String AUTH_WHITELIST = "/alianz/auth/**";
    
    // private static final String AUTH_BLOCKLIST ="/api/get";

    // private static final String USER_NOT_FOUND = "User not found";


    // /**
    //  * set up mocks for tests
    //  * @throws Exception on Error
    //  */
    // @Before
    // public void setUp() throws Exception {
    //     MockitoAnnotations.initMocks(this);
    // }
    
    // @Test
    // public void testDoFilterInternalForWhiltListedMatches() throws Exception {
    //     when(httpServletRequest.getCookies()).thenReturn(new Cookie[1]);
    //     when(httpServletRequest.getServletPath()).thenReturn(AUTH_WHITELIST);
        
    //     jwtAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

    //     verify(filterChain, times(1)).doFilter(httpServletRequest, httpServletResponse);
    // }

    // @Test
    // public void testDoFilterInternalWithNoCookies() throws Exception {
    //     when(httpServletRequest.getCookies()).thenReturn(null);
    //     when(httpServletRequest.getServletPath()).thenReturn(AUTH_BLOCKLIST);
        
    //     jwtAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

    //     verify(filterChain, times(1)).doFilter(httpServletRequest, httpServletResponse);
    // }

    // @Test
    // public void testDoFilterInternalForNullToken() throws Exception {
    //     Cookie[] cookies = new Cookie[1];
    //     cookies[0] = new Cookie("token", null);
    //     when(httpServletRequest.getCookies()).thenReturn(cookies);
    //     when(httpServletRequest.getServletPath()).thenReturn(AUTH_BLOCKLIST);
        
    //     jwtAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

    //     verify(filterChain, times(1)).doFilter(httpServletRequest, httpServletResponse);
    // }


    // @Test
    // public void testDoFilterInternalForBadCredentials() throws Exception {
    //     Cookie[] cookies = new Cookie[1];
    //     cookies[0] = new Cookie("token", "token");
    //     when(httpServletRequest.getCookies()).thenReturn(cookies);
    //     when(httpServletRequest.getServletPath()).thenReturn(AUTH_BLOCKLIST);
    //     when(jwtService.extractUser("token")).thenReturn("user");
    //     when(userDetailsService.loadUserByUsername("user")).thenThrow(new UsernameNotFoundException(USER_NOT_FOUND));

    //     Exception e = assertThrows(UsernameNotFoundException.class, () -> {
    //         jwtAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
    //     });
    //     e.getMessage().equals(USER_NOT_FOUND);
    // }

    // @Test
    // public void testDoFilterInternalForValidToken() throws Exception {
    //     Cookie[] cookies = new Cookie[1];
    //     cookies[0] = new Cookie("token", "token");
    //     Account account = createAccount();
    //     when(httpServletRequest.getCookies()).thenReturn(cookies);
    //     when(httpServletRequest.getServletPath()).thenReturn(AUTH_BLOCKLIST);
    //     when(jwtService.extractUser("token")).thenReturn("user");
    //     when(userDetailsService.loadUserByUsername(anyString())).thenReturn(account);
    //     when(jwtService.isTokenValid(anyString(), any(Account.class))).thenReturn(true);
       
    //     jwtAuthenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

    //     verify(filterChain, times(1)).doFilter(httpServletRequest, httpServletResponse);
    // }

    // private Account createAccount() {
    //     Account account = new Account();
    //     account.setRole(Role.ADMIN);
    //     return account;
    // }

}
