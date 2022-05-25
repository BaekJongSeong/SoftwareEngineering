package com.software.course.Model;

import com.software.course.Entity.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jongseong Baek
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

	private int accountId;

    private SignupDto signupDto;
    
    public static AccountDto createAccountDto(Account account) {
		SignupDto signupDto = SignupDto.createSignupDto(account);
    	return AccountDto.builder()
			    .signupDto(signupDto).build();
	}
}