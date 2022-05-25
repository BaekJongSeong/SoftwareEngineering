package com.software.course.Service.Impl;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.software.course.Entity.Account;
import com.software.course.Entity.AccountGroup;
import com.software.course.Entity.Location;
import com.software.course.Model.LoginDto;
import com.software.course.Model.SignupDto;
import com.software.course.Repository.AccountGroupRepository;
import com.software.course.Repository.AccountRepository;
import com.software.course.Service.IAccountService;
import com.software.course.Service.ILocationService;

import lombok.RequiredArgsConstructor;

/**
 * @author Jongseong Baek
 */

@RequiredArgsConstructor
@Service
public class AccountService implements IAccountService {
	
	private final ILocationService locationService;

    private final AccountRepository accountRepository;
    
    private final AccountGroupRepository accountGroupRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    @Override
	public Account checkForSignup(SignupDto signupDto) {
	    return accountRepository.findByLoginId(signupDto.getLoginId())
	    		.orElseGet((()-> this.createAccount(signupDto)));
	}
	
    @Override
	public Account createAccount(SignupDto signupDto) {
    	Location location = locationService.createLocation(signupDto.getLocationName(),signupDto.getLatitude(), signupDto.getLongitude());
    	Account account = Account.createAccountEntity(signupDto,
	    		passwordEncoder.encode(signupDto.getPassword()),this.findUnusedOrThrow(0),location);
	    accountRepository.save(account);
	    account.setStatus(1);
	    return account;
	}
	
    @Override
	public Account modifyAccount(SignupDto signupDto) {
		Account account = this.findByLoginIdOrReturn(signupDto.getLoginId());
		account.setEmailAddress(signupDto.getEmail());
		account.setName(signupDto.getName());
		account.setPhone(signupDto.getPhone());
		//account.setPassword(signupDto.getPassword());
		locationService.modifyLocationAccount(account.getLocation(), signupDto.getLocationName(),
				signupDto.getLatitude(), signupDto.getLongitude());
		return accountRepository.save(account);
	}
	
	
	@Override
	public Account findByLoginIdOrReturn(String loginId){
		return accountRepository.findByLoginId(loginId)
				.orElseGet((null));
	}
	
	@Override
	public boolean passwordMatch(Account account, LoginDto loginDto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(loginDto.getPassword(),account.getPassword());
	}
	
	@Override
	public AccountGroup findUnusedOrThrow(int use){
		List<AccountGroup> accountGroupList = accountGroupRepository.findUnused(use);
	    	
	    return accountGroupList.get(0);
	}
}
