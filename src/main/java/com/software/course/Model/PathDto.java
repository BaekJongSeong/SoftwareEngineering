package com.software.course.Model;


import com.software.course.Entity.Schedule;

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
public class PathDto {
	
	   private String loginId;
			   
	   private String calendarName;
		
	   private String scheduleName;
	   
	   private LocationDto locationDto;
	   
	   public static PathDto createPathDto(String loginId, Schedule schedule) {
			return PathDto.builder()
				.loginId(loginId)
				.locationDto(LocationDto.createLocationDto(loginId,schedule.getLocation()))
				.calendarName(schedule.getCalendar().getName())
				.scheduleName(schedule.getName()).build();
		}

}
