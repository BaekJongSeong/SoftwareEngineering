package com.software.course.Service.Impl;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.software.course.Entity.Alarm;
import com.software.course.Entity.Calendar;
import com.software.course.Entity.Location;
import com.software.course.Entity.Schedule;
import com.software.course.Repository.AlarmRepository;
import com.software.course.Repository.CalendarRepository;
import com.software.course.Repository.ScheduleRepository;
import com.software.course.Service.IAlarmService;
import com.software.course.Service.IScheduleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AlarmService implements IAlarmService {
		
	private final IScheduleService scheduleService;
	
	private final AlarmRepository alarmRepository;
		
	private final CalendarRepository calendarRepository;
		
	private final ScheduleRepository scheduleRepository;
	
	@Value("${API.key}")
	private String key;

	@Value("${API.password}")
	private String password;
	
	@Override
	public Alarm createAlarm(Date time, Schedule schedule) {
		return alarmRepository.save(Alarm.createAlarmEntity(time, schedule));
	}
	
	@Override
	public Alarm modifyAlarm(Date time, Schedule schedule) {
		Alarm alarm = schedule.getAlarmList().get(0);
		alarm.setAlarmBefore(time);
		alarmRepository.save(alarm);
		return alarm;
	}
	
	@Override
	public Alarm deleteAlarm(Schedule schedule) {
		Alarm alarm =schedule.getAlarmList().get(0);
		schedule.getAlarmList().remove(0);
		alarmRepository.delete(alarm);
		return alarm;
	}
	
	@Override
	@Transactional
	public String makeAlarmContent(Schedule schedule) throws IOException {
		String temp = this.pathSearching(schedule);
	//	String content="";
	//	Optional<Calendar> newCalendar = calendarRepository.findById(schedule.getCalendar().getCalendarId());
	//	Optional<Account> newAccount = accountRepository.findById(newCalendar.get().getAccount().getAccountId());
	//	content += newCalendar.get().getName() + "캘린더의 "+schedule.getName()+"에 대한 일정입니다.\n";
	//	content += "출발 장소는 "+newAccount.get().getLocation().getName()+"이며 도착 장소는 "+schedule.getLocation().getName()+"입니다\n";
		//content += "현재 출발 "++"분 전이며, 예상 소요 시간은 "++"분 입니다.";
		return temp;
	}
	
	
	@Override
	public Alarm getAlarmByScheduleId(String loginId,String calendarName,String scheduleName) {
		Schedule schedule = scheduleService.findByFetchCalendarId(loginId,calendarName,scheduleName);
		return schedule.getAlarmList().get(0);
	}
	
	@Override
	@Transactional
	public String makeAlarmTitle(Calendar calendar){
		Optional<Calendar> newCalendar = calendarRepository.findById(calendar.getCalendarId());
		return newCalendar.get().getAccount().getLoginId()+"님, 일정 알림입니다";
	}
	
	//@Transactional
    public String pathSearching(Schedule schedule) throws IOException {
    	String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
    	Optional<Schedule> oldSchedule = scheduleRepository.findById(schedule.getScheduleId());
    	Location start = oldSchedule.get().getCalendar().getAccount().getLocation();
    	Location dest = oldSchedule.get().getLocation();
    	String content="";
    	content += oldSchedule.get().getCalendar().getName() + "캘린더의 "+oldSchedule.get().getName()+"에 대한 일정입니다.\n";
    	content += "출발 장소는 "+start.getName()+"이며 도착 장소는 "+dest.getName()+"입니다\n";
    		
    	ByteBuffer buffer = StandardCharsets.UTF_8.encode(String.valueOf(start.getLatitude())+","+String.valueOf(start.getLongitude()));
    	String encode1 = StandardCharsets.UTF_8.decode(buffer).toString();
    	buffer = StandardCharsets.UTF_8.encode(String.valueOf(dest.getLatitude())+","+String.valueOf(dest.getLongitude()));
    	String encode2 = StandardCharsets.UTF_8.decode(buffer).toString();
    	URI uri = UriComponentsBuilder
    			.fromUriString("https://naveropenapi.apigw.ntruss.com")
    			.path("/map-direction-15/v1/driving")
    			.queryParam("start",encode1)
    			.queryParam("goal",encode2).encode().build().toUri();
    	
    	RestTemplate restTemplate = new RestTemplate();

    	RequestEntity<Void> req = RequestEntity.get(uri)
    	.header("X-NCP-APIGW-API-KEY-ID",key)
    	.header("X-NCP-APIGW-API-KEY",password)
    	.build();

    	ResponseEntity<String> result = restTemplate.exchange(req,String.class);
    	
    	System.out.println(result.toString());
    	
    	//String url="https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/driving?"
    	//		+ "start="+String.valueOf(start.get().getLatitude())+","+String.valueOf(start.get().getLongitude())
    	//		+ "&goal="+String.valueOf(dest.get().getLatitude())+","+String.valueOf(dest.get().getLongitude());
    	
    	return result.toString();
    }

}
