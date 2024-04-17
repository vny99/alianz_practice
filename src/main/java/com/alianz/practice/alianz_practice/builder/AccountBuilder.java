package com.alianz.practice.alianz_practice.builder;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.alianz.practice.alianz_practice.Entity.Account;
import com.alianz.practice.alianz_practice.Entity.Role;
import com.alianz.practice.alianz_practice.requests.RegistrationRequest;

public class AccountBuilder {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private Date dob;
    public static AccountBuilder from (RegistrationRequest request, PasswordEncoder encoder){
        return new AccountBuilder().withFirstName(request.getFirstName())
                .withLastName(request.getLastName())
                .withEmail(request.getEmail())
                .withPassword(encoder.encode(request.getPassword()))
                .withRole(request.getRole())
                .withDob(request.getDob());
    }
    private AccountBuilder withDob(Date dob) {
        this.dob = dob;
        return this;
    }
    private AccountBuilder withRole(String role) {
       this.role = Role.parse(role);
       return this;
    }
    private AccountBuilder withPassword(String encode) {
        this.password = encode;
        return this;
    }
    private AccountBuilder withEmail(String email) {
        this.email = email;
        return this;
    }
    private AccountBuilder withLastName(String lastName) {
       this.lastName = lastName;
       return this;
    }
    private AccountBuilder withFirstName(String firstName) {
       this.firstName = firstName;
       return this;
    }

    public Account build() {
        return new Account(null, firstName, lastName, email, password, dob, role);
    }
}
