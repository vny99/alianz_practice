package com.alianz.practice.alianz_practice.auth;

import java.util.TreeMap;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public class AuthencticationControllerTest {
    
    // @InjectMocks
    // @Resource
    // private AuthenticationController authenticationController;

    // @Mock
    // private AuthenticationService authenticationService;

    // @Mock
    // private BindingResult bindingResult;

    // @Mock
    // private Response response;

    // Map<String, Object> responseMap;

    private static final String STATE = "STATE";
    private static final String STATUS = "STATUS";
    private static final String PAYLOAD = "PAYLOAD";
    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "ERROR";
    private static final String SUCCESSFULL_REGISTRATION = "User registered successfully";
    private static final String SUCCESSFULL_LOGIN = "User logged in successfully";
    private static final String SUCCESSFULL_LOGOUT = "User logged out successfully";
    private static final String TOKEN_REFRESHED = "Token refreshed successfully";

    /**
     * set up mocks for tests
     * @throws Exception on Error
     */
    // @Before
    // public void setUp() throws Exception {
    //     MockitoAnnotations.initMocks(this);
    //     responseMap = new TreeMap<>();
    // }

    // @Test
    // public void testRegisterUser() {
    //     RegistrationRequest registrationRequest = createRegistrationRequest();
    //     when(bindingResult.hasErrors()).thenReturn(false);
    //     doNothing().when(authenticationService).register(registrationRequest);
    //     when(response.buildResponse(any(), any(HttpStatus.class))).thenReturn(buildResponse(SUCCESSFULL_REGISTRATION, HttpStatus.OK));

    //     Map<String, Object> response = authenticationController.registerUser(registrationRequest, bindingResult).getBody();
    //     assertNotNull(response);
    //     assertEquals(HttpStatus.OK, response.get(STATUS));
    //     assertEquals(SUCCESS, response.get(STATE));
    //     assertEquals(SUCCESSFULL_REGISTRATION, response.get(PAYLOAD));

    // }

    // @Test
    // public void testRegisterUserWithErrors() {
    //     RegistrationRequest registrationRequest = createRegistrationRequest();
    //     when(bindingResult.hasErrors()).thenReturn(true);
    //     when(response.buildResponse(any(), any(HttpStatus.class))).thenReturn(buildResponse(new ArrayList<>(), HttpStatus.BAD_REQUEST));

    //     Map<String, Object> response = authenticationController.registerUser(registrationRequest, bindingResult).getBody();
    //     assertNotNull(response);
    //     assertEquals(HttpStatus.BAD_REQUEST, response.get(STATUS));
    //     assertEquals(ERROR, response.get(STATE));
    // }

    // @Test
    // public void testLogin() {
    //     AuthenticationRequest authenticationRequest = createAuthenticationRequest();
    //     when(bindingResult.hasErrors()).thenReturn(false);
    //     doNothing().when(authenticationService).authenticateUser(authenticationRequest, null);
    //     when(response.buildResponse(any(), any(HttpStatus.class))).thenReturn(buildResponse(SUCCESSFULL_LOGIN, HttpStatus.OK));

    //     Map<String, Object> response = authenticationController.login(authenticationRequest, bindingResult, null).getBody();
    //     assertNotNull(response);
    //     assertEquals(HttpStatus.OK, response.get(STATUS));
    //     assertEquals(SUCCESS, response.get(STATE));
    //     assertEquals(SUCCESSFULL_LOGIN, response.get(PAYLOAD));
    // }

    // @Test
    // public void testLoginWithErrors() {
    //     AuthenticationRequest authenticationRequest = createAuthenticationRequest();
    //     when(bindingResult.hasErrors()).thenReturn(true);
    //     when(response.buildResponse(any(), any(HttpStatus.class))).thenReturn(buildResponse(new ArrayList<>(), HttpStatus.BAD_REQUEST));

    //     Map<String, Object> response = authenticationController.login(authenticationRequest, bindingResult, null).getBody();
    //     assertNotNull(response);
    //     assertEquals(HttpStatus.BAD_REQUEST, response.get(STATUS));
    //     assertEquals(ERROR, response.get(STATE));
    // }

    // @Test
    // public void testLogout() {
    //     doNothing().when(authenticationService).logout(null);
    //     when(response.buildResponse(any(), any(HttpStatus.class))).thenReturn(buildResponse(SUCCESSFULL_LOGOUT, HttpStatus.OK));

    //     Map<String, Object> response = authenticationController.logout(null).getBody();
    //     assertNotNull(response);
    //     assertEquals(HttpStatus.OK, response.get(STATUS));
    //     assertEquals(SUCCESS, response.get(STATE));
    //     assertEquals(SUCCESSFULL_LOGOUT, response.get(PAYLOAD));
    // }

    // @Test
    // public void testRefreshToken() {
    //     doNothing().when(authenticationService).refreshToken(null, null);
    //     when(response.buildResponse(any(), any(HttpStatus.class))).thenReturn(buildResponse(TOKEN_REFRESHED, HttpStatus.OK));

    //     Map<String, Object> response = authenticationController.refreshToken(null, null).getBody();
    //     assertNotNull(response);
    //     assertEquals(HttpStatus.OK, response.get(STATUS));
    //     assertEquals(SUCCESS, response.get(STATE));
    //     assertEquals(TOKEN_REFRESHED, response.get(PAYLOAD));
    // }

    // private AuthenticationRequest createAuthenticationRequest() {
    //     return new AuthenticationRequest("email", "password");
    // }

    // private RegistrationRequest createRegistrationRequest() {
    //     RegistrationRequest request = new RegistrationRequest();
    //     request.setEmail("email");
    //     request.setFirstName("firstName");
    //     request.setLastName("lastName");
    //     request.setPassword("password");
    //     request.setRole(Role.ADMIN.name());
    //     return request;
    // }

    // public Map<String, Object> buildResponse(Object payLoad, HttpStatus status) {
    //     if (HttpStatus.OK.equals(status)) {
    //         responseMap.put(STATE, SUCCESS);
    //     } else {
    //         responseMap.put(STATE, ERROR);
    //     }
    //     responseMap.put(STATUS, status);
    //     responseMap.put(PAYLOAD, payLoad);
    //     return responseMap;
    // }

}
