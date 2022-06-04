package com.software.course.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.software.course.FirebaseCloudMessageService;
import com.software.course.Entity.Alarm;
import com.software.course.Entity.Schedule;
import com.software.course.Model.AlarmDto;
import com.software.course.Model.ResDto1;
import com.software.course.Model.ScheduleDto;
import com.software.course.Service.IAlarmService;
import com.software.course.Service.IScheduleService;

import lombok.RequiredArgsConstructor;

/**
 * @author Jongseong Baek
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AlarmController {
	
	@Value("${API.token}")
	private String token;
	
	private final IAlarmService alarmService;
	
	private final IScheduleService scheduleService;
		
	private final FirebaseCloudMessageService firebaseService;
	

	@GetMapping("/alarm/{loginId}/{calendarName}/{scheduleName}")
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
	
	@PostMapping("/alarm")
	public ResponseEntity getAlarmMessage(@RequestBody ScheduleDto scheduleDto) throws IOException, FirebaseMessagingException{
		Schedule schedule = scheduleService.findByFetchCalendarId(scheduleDto.getLoginId(), scheduleDto.getCalendarName(), scheduleDto.getScheduleName());
		String content = alarmService.makeAlarmContent(schedule);
		firebaseService.sendMessageTo(token,alarmService.makeAlarmTitle(schedule.getCalendar()),content);		
		return ResponseEntity.ok().build();
	}
	
	@Scheduled(cron = "0 0/5 * * * *")
	public void alarm() throws IOException, FirebaseMessagingException {
		List<Schedule> scheduleForAlarmList = scheduleService.findScheduleForAlarm();
		for(Schedule schedule : scheduleForAlarmList) {
			String content = alarmService.makeAlarmContent(schedule);
			firebaseService.sendMessageTo(token,alarmService.makeAlarmTitle(schedule.getCalendar()),content);
			System.out.println("전송 완료");
		}
	}
	
	
}
