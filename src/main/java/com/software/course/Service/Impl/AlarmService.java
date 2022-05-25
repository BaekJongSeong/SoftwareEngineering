package com.software.course.Service.Impl;

import java.io.IOException;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.software.course.Entity.Alarm;
import com.software.course.Entity.Schedule;
import com.software.course.Model.ScheduleDto;
import com.software.course.Repository.AlarmRepository;
import com.software.course.Repository.ScheduleRepository;
import com.software.course.Service.IAlarmService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AlarmService implements IAlarmService {
	
	private final AlarmRepository alarmRepository;
	
	private final ScheduleRepository scheduleRepository;
		
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
	public String makeAlarmContent(ScheduleDto scheduleDto, Schedule schedule) throws IOException {
		String temp = this.pathSearching(scheduleDto,schedule);
		String content="";
		content += scheduleDto.getCalendarName() + "캘린더의 "+schedule.getName()+"에 대한 일정입니다.\n";
		content += "출발 장소는 "+scheduleDto.getLocationDto().getName()+"이며 도착 장소는 "+schedule.getLocation().getName()+"입니다\n";
		//content += "현재 출발 "++"분 전이며, 예상 소요 시간은 "++"분 입니다.";
		return content;
	}
	
	
    public String pathSearching(ScheduleDto scheduleDto, Schedule schedule) throws IOException {
    	String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";

    	//String url = "https://map.kakao.com/?map_type=TYPE_MAP&"
    	//		+ "target=car&rt=%2C%2C523953%2C1084098&rt1="+scheduleDto.getLocationDto().getName()+"&rt2="+schedule.getLocation().getName();
    	String url = "https://map.kakao.com/link/from/"+scheduleDto.getLocationDto().getName()+","+scheduleDto.getLocationDto().getLatitude()+","+scheduleDto.getLocationDto().getLongitude()
    			+"/to/"+schedule.getLocation().getName()+","+schedule.getLocation().getLatitude()+","+schedule.getLocation().getLongitude();
    	
    	System.out.println(url);
    	
    	Document doc =Jsoup.connect(url).ignoreHttpErrors(true).get();//.execute();//Jsoup.connect(url).userAgent(userAgent).get();
    	System.out.println(doc.toString());
    	Element element =doc.getElementById("content");
    	System.out.println(element.toString());
    	//Elements element = doc.select("div.contents");
    	Integer temp = Integer.valueOf(element.select("span.num").text());
    	System.out.println(temp);
    	return "기다려";
    }

}
