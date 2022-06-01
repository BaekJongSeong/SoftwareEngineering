package com.software.course.Service;

import com.software.course.Entity.Account;
import com.software.course.Entity.AccountGroup;
import com.software.course.Model.LoginDto;
import com.software.course.Model.SignupDto;

public interface IAccountService {
    public Account checkForSignup(SignupDto signupDto);

    public Account createAccount(SignupDto signupDto);
        
    public Account modifyAccount(SignupDto signupDto);
           
    public Account findByLoginIdOrReturn(String loginId);
    
    public boolean passwordMatch(Account account,LoginDto loginDto);
    
	public AccountGroup findUnusedOrThrow(int use);
}
