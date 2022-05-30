package com.software.course.Controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software.course.Entity.Location;
import com.software.course.Entity.Schedule;
import com.software.course.Model.ResDto1;
import com.software.course.Model.ResDto2;
import com.software.course.Model.ScheduleDto;
import com.software.course.Service.IAlarmService;
import com.software.course.Service.ILocationService;
import com.software.course.Service.IScheduleService;

import lombok.RequiredArgsConstructor;

/**
 * @author Jongseong Baek
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScheduleController {
	
	private final IScheduleService scheduleService;
	
	private final ILocationService locationService;
	
	private final IAlarmService alarmService;
	
	@GetMapping("schedule/{loginId}/{calendarName}/{scheduleName}")
	public ResponseEntity<ResDto1<ScheduleDto>>getSchedule(
			@PathVariable String loginId,
			@PathVariable String calendarName,
			@PathVariable String scheduleName
	){
		Schedule schedule = scheduleService.findByFetchCalendarId(loginId,calendarName,scheduleName);
		return new ResponseEntity<>(ResDto1.createResDto(
				ScheduleDto.createScheduleDto(loginId,calendarName,schedule),1,0),
    			new HttpHeaders(),HttpStatus.OK);	
	}
	
	@GetMapping("schedule/{loginId}/{calendarName}")
	public ResponseEntity<ResDto2<ScheduleDto>>getScheduleList(
			@PathVariable String loginId,
			@PathVariable String calendarName
	){
		List<Schedule>scheduleList = scheduleService.findByFetchCalendarIdOrThrow(loginId,calendarName);
		return new ResponseEntity<>(ResDto2.createResDto(
				ScheduleDto.createScheduleDtoList(loginId,calendarName,scheduleList),1,0),
    			new HttpHeaders(),HttpStatus.OK);	
	}
	
	@PostMapping("/schedule")
	 public ResponseEntity<ResDto1<ScheduleDto>> createSchedule (
			 @RequestBody ScheduleDto scheduleDto
	) {
		Location location = locationService.createLocation(scheduleDto.getLocationDto().getName(),
    			scheduleDto.getLocationDto().getLatitude(),scheduleDto.getLocationDto().getLongitude());
		Schedule schedule = scheduleService.createSchedule(scheduleDto.getLoginId(), scheduleDto,location);
		alarmService.createAlarm(scheduleDto.getTime(),schedule);
		return new ResponseEntity<>(ResDto1.createResDto(ScheduleDto.createScheduleDto(scheduleDto.getLoginId(),
				scheduleDto.getCalendarName(),schedule),1,0), new HttpHeaders(),HttpStatus.OK);
	}
	
	@PutMapping("/schedule")
	 public ResponseEntity<ResDto1<ScheduleDto>> modifySchedule (
			 @RequestBody ScheduleDto scheduleDto
	) {
		Schedule schedule = scheduleService.modifySchedule(scheduleDto.getLoginId(), scheduleDto);
		locationService.modifyLocationAccount(schedule.getLocation(),scheduleDto.getLocationDto().getName(),
				scheduleDto.getLocationDto().getLatitude(),scheduleDto.getLocationDto().getLongitude());
		alarmService.modifyAlarm(scheduleDto.getTime(),schedule);
		return new ResponseEntity<>(ResDto1.createResDto(ScheduleDto.createScheduleDto(scheduleDto.getLoginId(),
				scheduleDto.getCalendarName(),schedule),1,0), new HttpHeaders(),HttpStatus.OK);
	}
	
	@DeleteMapping("/schedule")
	 public ResponseEntity<ResDto1<ScheduleDto>> deleteSchedule (
			 @RequestBody ScheduleDto scheduleDto
	) {
		Schedule schedule = scheduleService.deleteSchedule(scheduleDto.getLoginId(), scheduleDto);
		return new ResponseEntity<>(ResDto1.createResDto(ScheduleDto.builder().loginId(scheduleDto.getLoginId())
				.calendarName(scheduleDto.getCalendarName()).build(),1,0), new HttpHeaders(),HttpStatus.OK);
	}
}
