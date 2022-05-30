package com.software.course.Model;

import java.util.Date;

import com.software.course.Entity.Alarm;

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
public class AlarmDto {

	private String loginId;
	
	private Date time;
	
	public static AlarmDto createAlarmDto(String loginId, Alarm alarm) {
		return AlarmDto.builder()
				.loginId(loginId)
				.time(alarm.getAlarmBefore()).build();

	}
}
