package com.software.course.Controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.software.course.FcmUtil;
import com.software.course.Entity.Alarm;
import com.software.course.Entity.Schedule;
import com.software.course.Model.AlarmDto;
import com.software.course.Model.ResDto1;
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
public class AlarmController {

	private final IAlarmService alarmService;
	
	private final IScheduleService scheduleService;
	
	private final ILocationService locationService;
	
	@GetMapping("alarm/{loginId}/{calendarName}/{scheduleName}")
	public ResponseEntity<ResDto1<AlarmDto>>getAlarm(
			@PathVariable String loginId,
			@PathVariable String calendarName,
			@PathVariable String scheduleName
	){
		Alarm alarm = alarmService.getAlarmByScheduleId(loginId,calendarName,scheduleName);
		return new ResponseEntity<>(ResDto1.createResDto(AlarmDto.createAlarmDto(loginId,
				alarm),1,0), new HttpHeaders(),HttpStatus.OK);
	}
		
	@PutMapping("/alarm")
    public ResponseEntity<ResDto1<ScheduleDto>> modifyAlarm (@RequestBody ScheduleDto scheduleDto) {
		Schedule schedule = scheduleService.findByFetchCalendarId(scheduleDto.getLoginId(), scheduleDto.getCalendarName(), scheduleDto.getScheduleName());
		Alarm alarm = alarmService.modifyAlarm(scheduleDto.getTime(),schedule);
		return new ResponseEntity<>(ResDto1.createResDto(ScheduleDto.createScheduleDto(scheduleDto.getLoginId(),
				scheduleDto.getCalendarName(),schedule),1,0), new HttpHeaders(),HttpStatus.OK);
	}
	
	@DeleteMapping("/alarm")
	public ResponseEntity<ResDto1<ScheduleDto>> deleteAlarm (@RequestBody ScheduleDto scheduleDto) {
		Schedule schedule = scheduleService.findByFetchCalendarId(scheduleDto.getLoginId(), scheduleDto.getCalendarName(), scheduleDto.getScheduleName());
		Alarm alarm = alarmService.deleteAlarm(schedule);
		return new ResponseEntity<>(ResDto1.createResDto(ScheduleDto.createScheduleDto(scheduleDto.getLoginId(),
				scheduleDto.getCalendarName(),schedule),1,0), new HttpHeaders(),HttpStatus.OK);
	}
	
	//schedular 태그
	@PostMapping("/alarm")
	public @ResponseBody String alarm(
			@RequestBody ScheduleDto scheduleDto
	) throws IOException {
		
		Schedule schedule = scheduleService.findByFetchCalendarId(scheduleDto.getLoginId(), scheduleDto.getCalendarName(), scheduleDto.getScheduleName());
		String tokenId = "edaRxEK6T5KFOEriMVFkZ3:APA91bGInRSO9Z5GwKj_hGT2A7kVgt0OcI5-lOX6HEFoTvhr3Ygvm3MXqGEu0b54BfLRgufbR73MBROM_RriYgH4N0yqkiYcidoEgcVuPlz45LdQez9bVHsQTDyP6wDNdTkqZGsJo7uS";
		String title = scheduleDto.getLoginId()+"님, 일정 알림입니다";
		String content = alarmService.makeAlarmContent(scheduleDto,schedule);
		FcmUtil fcmUtil = new FcmUtil();
		fcmUtil.send_FCM(tokenId,title,content);
		return "test";
	
	}
	
	
}
