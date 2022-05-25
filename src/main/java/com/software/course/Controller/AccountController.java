package com.software.course.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software.course.Entity.Account;
import com.software.course.Model.AccountDto;
import com.software.course.Model.LoginDto;
import com.software.course.Model.ResDto1;
import com.software.course.Model.SignupDto;
import com.software.course.Service.IAccountService;

import lombok.RequiredArgsConstructor;

/**
 * @author Jongseong Baek
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final IAccountService accountService;
  
    @PostMapping("/account")
    public ResponseEntity<ResDto1<AccountDto>> signup (
            @Validated @RequestBody SignupDto signupDto
    ) {
    	Account account = accountService.checkForSignup(signupDto);
    	return new ResponseEntity<>(ResDto1.createResDto(AccountDto.createAccountDto(
    			account),account.getStatus(),0),new HttpHeaders(),HttpStatus.OK);
    }

    @PutMapping("/account")
    public ResponseEntity<ResDto1<AccountDto>> modifyAccount (
            @Validated @RequestBody SignupDto signupDto
    ) {
    	Account account = accountService.modifyAccount(signupDto);
        return new ResponseEntity<>(ResDto1.createResDto(AccountDto.createAccountDto(
        		account),1,0),new HttpHeaders(),HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<ResDto1<AccountDto>> login (
            @Validated @RequestBody LoginDto loginDto
    ) {
    	Account account = accountService.findByLoginIdOrReturn(loginDto.getLoginId());
    	boolean result = (account!=null) ? accountService.passwordMatch(account,loginDto) : false;
        return new ResponseEntity<>(ResDto1.createResDto(AccountDto.createAccountDto(
        		account),(result==true)?1:0,1),new HttpHeaders(),HttpStatus.OK);
    }
}