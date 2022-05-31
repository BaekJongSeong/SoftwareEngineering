package com.software.course.Service.Impl;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.software.course.Entity.Account;
import com.software.course.Entity.Alarm;
import com.software.course.Entity.Calendar;
import com.software.course.Entity.Location;
import com.software.course.Entity.Schedule;
import com.software.course.Repository.AccountRepository;
import com.software.course.Repository.AlarmRepository;
import com.software.course.Repository.CalendarRepository;
import com.software.course.Repository.LocationRepository;
import com.software.course.Repository.ScheduleRepository;
import com.software.course.Service.IAlarmService;
import com.software.course.Service.IScheduleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AlarmService implements IAlarmService {
		
	private final IScheduleService scheduleService;
	
	private final AlarmRepository alarmRepository;
	
	private final ScheduleRepository scheduleRepository;
	
	private final AccountRepository accountRepository;

	private final CalendarRepository calendarRepository;
	
	private final LocationRepository locationRepository;

	
	@Override
	public Alarm createAlarm(Date time, Schedule schedule) {
		return alarmRepository.save(Alarm.createAlarmEntity(time, schedule));
	}
	
	@Override
	public Alarm modifyAlarm(Date time, Schedule schedule) {
		Schedule oldSchedule = scheduleRepository.findByFetchScheduleId(schedule.getScheduleId());
		Alarm alarm = oldSchedule.getAlarmList().get(0);
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
	public String makeAlarmTitle(Calendar calendar){
		Optional<Calendar> newCalendar = calendarRepository.findById(calendar.getCalendarId());
		
		//return newCalendar.get().getAccount().getLoginId()+"님, 일정 알림입니다";
		return "이젠되라";
	}
	
	//@Transactional
    public String pathSearching(Schedule schedule) throws IOException {
    	String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";

       	//String url = "https://map.kakao.com/link/from/"+scheduleDto.getLocationDto().getName()+","+scheduleDto.getLocationDto().getLatitude()+","+scheduleDto.getLocationDto().getLongitude()
    	//		+"/to/"+schedule.getLocation().getName()+","+schedule.getLocation().getLatitude()+","+schedule.getLocation().getLongitude();
    	Optional<Calendar> newCalendar = calendarRepository.findById(schedule.getCalendar().getCalendarId());
    	Optional<Account> newAccount = accountRepository.findById(newCalendar.get().getAccount().getAccountId());
    	Optional<Location> start = locationRepository.findById(newAccount.get().getLocation().getLocationId());
    	Optional<Location> dest = locationRepository.findById(schedule.getLocation().getLocationId());
    	String content="";
    	content += newCalendar.get().getName() + "캘린더의 "+schedule.getName()+"에 대한 일정입니다.\n";
    	content += "출발 장소는 "+start.get().getName()+"이며 도착 장소는 "+dest.get().getName()+"입니다\n";
    		
    	
    	//String url= "http://naverapp.naver.com/route/public?slat="+ String.valueOf(start.get().getLatitude())+"&slng="+String.valueOf(start.get().getLongitude())+"&sname="+start.get().getName()
    	//		+"&dlat="+String.valueOf(dest.get().getLatitude())+"&dlng="+String.valueOf(dest.get().getLongitude())+"&dname="+dest.get().getName();
    	ByteBuffer buffer = StandardCharsets.UTF_8.encode(String.valueOf(start.get().getLatitude())+","+String.valueOf(start.get().getLongitude()));
    	String encode1 = StandardCharsets.UTF_8.decode(buffer).toString();
    	buffer = StandardCharsets.UTF_8.encode(String.valueOf(dest.get().getLatitude())+","+String.valueOf(dest.get().getLongitude()));
    	String encode2 = StandardCharsets.UTF_8.decode(buffer).toString();
    	URI uri = UriComponentsBuilder
    			.fromUriString("https://naveropenapi.apigw.ntruss.com")
    			.path("/map-direction-15/v1/driving")
    			.queryParam("start",encode1)
    			.queryParam("goal",encode2).encode().build().toUri();
    	
    	RestTemplate restTemplate = new RestTemplate();

    	RequestEntity<Void> req = RequestEntity.get(uri)
    	.header("X-NCP-APIGW-API-KEY-ID","gwmimutb9e")
    	.header("X-NCP-APIGW-API-KEY","ldgWw0mxDmkIgrqKx8YSrnBIUmNgqP1o0t0NVjsy")
    	.build();

    	ResponseEntity<String> result = restTemplate.exchange(req,String.class);
    	
    	//String url="https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/driving?"
    	//		+ "start="+String.valueOf(start.get().getLatitude())+","+String.valueOf(start.get().getLongitude())
    	//		+ "&goal="+String.valueOf(dest.get().getLatitude())+","+String.valueOf(dest.get().getLongitude());
    	//System.out.println(url);
    	
    	//Document doc =Jsoup.connect(url).ignoreHttpErrors(true).get();//.execute();//Jsoup.connect(url).userAgent(userAgent).get();
    	//System.out.println(doc.toString());
    	/*Element element =doc.getElementById("content");
    	System.out.println(element.toString());
    	//Elements element = doc.select("div.contents");
    	Integer temp = Integer.valueOf(element.select("span.num").text());
    	System.out.println(temp);*/
    	
    	return "기다려";
    }

}
