package com.software.course.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.software.course.Entity.Calendar;

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
public class CalendarDto {
	
	private String prevcalendarName;
	
	private String calendarName;
	
	private Date createAt;  
	
	private SignupDto signupDto;
	
	public static CalendarDto createCalendarDto(Calendar calendar) {
		return CalendarDto.builder()
				.calendarName(calendar.getName())
				.createAt(calendar.getCreateAt())
				.signupDto(SignupDto.createSignupDto(calendar.getAccount())).build();
	}
	
	public static List<CalendarDto> createCalendarDtoList(List<Calendar> calendarList) {
		List<CalendarDto> dtoList = new ArrayList<>();
		for(Calendar calendar : calendarList)
			dtoList.add(createCalendarDto(calendar));
		return dtoList;
	}
}
