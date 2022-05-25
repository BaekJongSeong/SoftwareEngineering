package com.software.course.Model;

import java.util.List;

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
public class ResDto2<T> {

	@NotNull
	private String message;
	
	@NotNull
	private String state;
	
	//@NotNull
	private List<T> Data;
	
	public static <T> ResDto2<T> createResDto(List<T> list,int status, int flag){
		String message="";
		if(status==1) 
			message = (flag==1) ? "로그인이 성공하였습니다." : "등록/수정 완료되었습니다.";
		 else 
			message = (flag==1) ? "로그인이 실패하였습니다." : "DB에 이미 존재하거나, DB에 없는 정보입니다.";
		
		return new ResDto2<>(message,String.valueOf(status),list);//new Gson().toJson(list));
	}
}
