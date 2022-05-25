package com.software.course.Model;

import java.util.Date;

import com.software.course.Entity.Account;
import com.sun.istack.NotNull;

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
public class SignupDto{

    @NotNull
    private String loginId;
    
    private String password;
    
    private Date createAt;
    
    private String name;

    private String email;
    
    private String phone;
    
    private String locationName;
    
    private Double latitude;
    
    private Double longitude;
    
    public static SignupDto createSignupDto(Account account) {
    	    	
		return SignupDto.builder()
			    .createAt(account.getCreateAt())
			    .loginId(account.getLoginId())
			    .name(account.getName())
			    .email(account.getEmailAddress())
			    .phone(account.getPhone())
			    .locationName(account.getLocation().getName())
			    .latitude(account.getLocation().getLatitude())
			    .longitude(account.getLocation().getLongitude()).build();
	}
}