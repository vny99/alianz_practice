package com.alianz.practice.alianz_practice.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alianz.practice.alianz_practice.requests.RegistrationRequest;
import com.alianz.practice.alianz_practice.response.Response;
import com.alianz.practice.alianz_practice.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/alianz/user")
public class AccountController {

    @Autowired private Response response;

    @Autowired private AccountService accountService;

    private static final String ACCOUNT_DELETED_SUCCESSFULL = "Account deleted Successfull : ";

    private static final String ACCOUNT_UPDATAION_SUCCESSFULL = "Account updated Successfull : ";
    
    @GetMapping("/get")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Map<String, Object>> getAccount (@RequestParam String email){
        return ResponseEntity.ok(response.buildResponse(accountService.getUserProfile(email), HttpStatus.OK));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAllAccounts (){
        return ResponseEntity.ok(response.buildResponse(accountService.getAllAccounts(), HttpStatus.OK));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteAccount (@RequestParam String email){
        accountService.deleteAccount(email);
        return ResponseEntity.ok(response.buildResponse(ACCOUNT_DELETED_SUCCESSFULL+email, HttpStatus.OK));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> updateAccount (@Valid @RequestBody RegistrationRequest request,@RequestParam String email, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.ok(response.buildResponse(getValidationErrors(result.getAllErrors()), HttpStatus.BAD_REQUEST));
        }
        accountService.updateAccount(email, request);
        return ResponseEntity.ok(response.buildResponse(ACCOUNT_UPDATAION_SUCCESSFULL+email, HttpStatus.OK));
    }

    private List<String> getValidationErrors(List<ObjectError> allErrors) {
        return allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
    }
}
