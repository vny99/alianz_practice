package com.alianz.practice.alianz_practice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alianz.practice.alianz_practice.Entity.Account;
import com.alianz.practice.alianz_practice.builder.AccountBuilder;
import com.alianz.practice.alianz_practice.builder.UserProfileBuilder;
import com.alianz.practice.alianz_practice.repository.AccountRespository;
import com.alianz.practice.alianz_practice.requests.RegistrationRequest;
import com.alianz.practice.alianz_practice.response.UserProfile;

@Service
public class AccountService {
    @Autowired
    private AccountRespository accountRespository;

    @Autowired
    private PasswordEncoder encoder;

    public Account getAccount(String email) {
        return accountRespository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserProfile getUserProfile(String email) {
        return UserProfileBuilder.from(accountRespository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"))).build() ;
    }

    public List<UserProfile> getAllAccounts() {
        return accountRespository.findAll().stream().map(UserProfileBuilder::from).map(UserProfileBuilder::build).toList();
    }

    public void updateAccount(String email, RegistrationRequest request) {
        Account account = AccountBuilder.from(request, encoder).build();
        Account oldAccount = getAccount(email);
        account.setId(oldAccount.getId());
        accountRespository.save(account);
    }
    
    public void deleteAccount(String email) {
        Account account = getAccount(email);
        accountRespository.delete(account);
    }
    
}
