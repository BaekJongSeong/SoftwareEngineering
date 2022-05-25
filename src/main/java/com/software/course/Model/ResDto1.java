package com.software.course.Model;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jongseong Baek
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResDto1<T> {

	@NotNull
	private String message;
	
	@NotNull
	private String state;
	
	//@NotNull
	private T Data;
	
	public static <T> ResDto1<T> createResDto(T data,int status, int flag){
		String message="";
		if(status==1) 
			message = (flag==1) ? "로그인이 성공하였습니다." : "등록/수정 완료되었습니다.";
		 else 
			message = (flag==1) ? "로그인이 실패하였습니다." : "DB에 이미 존재하거나, DB에 없는 정보입니다.";
		
		return new ResDto1<>(message,String.valueOf(status),data);//new Gson().toJson(list));
	}
}
