package com.software.course.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.software.course.Entity.Calendar;
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
public class ScheduleDto {

	private String loginId;
	
	private LocationDto locationDto;
	
	private String calendarName;
	
	private String prevScheduleName;
	
	private String scheduleName;
	
	private String text;
	
	private Integer hour;
	
	private Integer minute;
	
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date time;
	
	private Integer day;
	
	public static ScheduleDto createScheduleDto(String loginId,String calendarName, Schedule schedule) {
    	return ScheduleDto.builder()
    			.loginId(loginId)
    			.locationDto(LocationDto.createLocationDto(loginId, schedule.getLocation()))
    			.calendarName(calendarName)
    			.scheduleName(schedule.getName())
			    .text(schedule.getText())
			    .time(schedule.getTime())
			    .day(schedule.getDay()).build();
	}
	
	public static List<ScheduleDto> createScheduleDtoList(String loginId,String calendarName,List<Schedule> scheduleList) {
		List<ScheduleDto> dtoList = new ArrayList<>();
		for(Schedule schedule : scheduleList)
			dtoList.add(createScheduleDto(loginId,calendarName,schedule));
		return dtoList;
	}
	
}
