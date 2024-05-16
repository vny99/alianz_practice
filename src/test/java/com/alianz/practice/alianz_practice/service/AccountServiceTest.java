package com.alianz.practice.alianz_practice.service;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AccountServiceTest {
    // @InjectMocks
    // @Resource
    // private AccountService service;

    
    // @Mock private AccountRespository accountRespository;

    @Mock private PasswordEncoder encoder;

    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PASSWORD = "password";
    private static final String USER_NOT_FOUND = "User not found";


    /**
     * set up mocks 
     * @throws Exception on error
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    // @Test
    // public void testGetAccount() {
    //     Account expectedAccount = createAccount();
    //     when(accountRespository.findByEmail(anyString())).thenReturn(Optional.of(expectedAccount));

    //     Account account = service.getAccount(EMAIL);
        
    //     assertNotNull(account);
    //     assertEquals(expectedAccount, account);
    // }

    // @Test
    // public void testGetAccountNotFound() {
    //     when(accountRespository.findByEmail(anyString())).thenThrow(new UsernameNotFoundException(USER_NOT_FOUND));
        
    //     Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
    //         service.getAccount(EMAIL);
    //     });
        
    //     assertEquals(USER_NOT_FOUND, exception.getMessage());
    // }

    // @Test
    // public void testGetUserProfile() {
    //     Account expectedAccount = createAccount();
    //     when(accountRespository.findByEmail(anyString())).thenReturn(Optional.of(expectedAccount));
        
    //     UserProfile userProfile = service.getUserProfile(EMAIL);
        
    //     assertNotNull(userProfile);
    //     assertEquals(expectedAccount.getEmail(), userProfile.getEmail());
    //     assertEquals(expectedAccount.getFirstName(), userProfile.getFirstName());
    //     assertEquals(expectedAccount.getLastName(), userProfile.getLastName());
    //     assertEquals(expectedAccount.getRole().name(), userProfile.getRole());
    // }

    // @Test
    // public void testGetUserProfileNotFound() {
    //     when(accountRespository.findByEmail(anyString())).thenThrow(new UsernameNotFoundException(USER_NOT_FOUND));
       
    //     Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
    //         service.getUserProfile(EMAIL);
    //     });
       
    //     assertEquals(USER_NOT_FOUND, exception.getMessage());
    // }

    // @Test
    // public void testGetAllAccounts() {
    //     Account expectedAccount = createAccount();
    //     when(accountRespository.findAll()).thenReturn(List.of(expectedAccount));
        
    //     List<UserProfile> userProfiles = service.getAllAccounts();
        
    //     assertNotNull(userProfiles);
    //     assertEquals(1, userProfiles.size());
    //     UserProfile userProfile = userProfiles.get(0);
    //     assertEquals(expectedAccount.getEmail(), userProfile.getEmail());
    //     assertEquals(expectedAccount.getFirstName(), userProfile.getFirstName());
    //     assertEquals(expectedAccount.getLastName(), userProfile.getLastName());
    //     assertEquals(expectedAccount.getRole().name(), userProfile.getRole());
    // }

    // @Test
    // public void testGetAllAccountsEmpty() {
    //     when(accountRespository.findAll()).thenReturn(List.of());
        
    //     List<UserProfile> userProfiles = service.getAllAccounts();
        
    //     assertNotNull(userProfiles);
    //     assertEquals(0, userProfiles.size());
    // }

    // @Test
    // public void testDeleteAccount() {
    //     Account expectedAccount = createAccount();
    //     when(accountRespository.findByEmail(anyString())).thenReturn(Optional.of(expectedAccount));
        
    //     service.deleteAccount(EMAIL);
        
    //     verify(accountRespository, times(1)).delete(any(Account.class));
    // }

    // @Test
    // public void testDeleteAccountNotFound() {
    //     when(accountRespository.findByEmail(anyString())).thenThrow(new UsernameNotFoundException(USER_NOT_FOUND));
       
    //     Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
    //         service.deleteAccount(EMAIL);
    //     });
       
    //     assertEquals(USER_NOT_FOUND, exception.getMessage());
    // }

    // @Test
    // public void testUpdateAccount() {
    //     Account expectedAccount = createAccount();
    //     RegistrationRequest request = createRegistrationRequest();
    //     when(accountRespository.findByEmail(anyString())).thenReturn(Optional.of(expectedAccount));
    //     when(encoder.encode(anyString())).thenReturn(PASSWORD);
       
    //     service.updateAccount(EMAIL, request);
       
    //     verify(accountRespository, times(1)).save(any(Account.class));
    // }

    // @Test
    // public void testUpdateAccountNotFound() {
    //     RegistrationRequest request = createRegistrationRequest();
    //     when(accountRespository.findByEmail(anyString())).thenThrow(new UsernameNotFoundException(USER_NOT_FOUND));
       
    //     Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
    //         service.updateAccount(EMAIL, request);
    //     });
       
    //     assertEquals(USER_NOT_FOUND, exception.getMessage());
    // }

    // private Account createAccount() {
    //     Account account = new Account();
    //     account.setEmail(EMAIL);
    //     account.setFirstName(FIRST_NAME);
    //     account.setLastName(LAST_NAME);
    //     account.setPassword(PASSWORD);
    //     account.setRole(Role.ADMIN);
    //     return account;
    // }
    
    // private RegistrationRequest createRegistrationRequest() {
    //     RegistrationRequest request = new RegistrationRequest();
    //     request.setEmail(EMAIL);
    //     request.setFirstName(FIRST_NAME);
    //     request.setLastName(LAST_NAME);
    //     request.setPassword(PASSWORD);
    //     request.setRole(Role.ADMIN.name());
    //     return request;
    // }

}
