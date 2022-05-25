package com.software.course.Model;

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
public class LoginDto{

    @NotNull
    private String loginId;
    
    @NotNull
    private String password;
    
}
