package com.software.course.Service.Impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
//import java.util.Calendar;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.software.course.Entity.Calendar;
import com.software.course.Entity.Location;
import com.software.course.Entity.Schedule;
import com.software.course.Model.ScheduleDto;
import com.software.course.Repository.CalendarRepository;
import com.software.course.Repository.LocationRepository;
import com.software.course.Repository.ScheduleRepository;
import com.software.course.Service.ICalendarService;
import com.software.course.Service.IScheduleService;

import lombok.RequiredArgsConstructor;

/**
 * @author Jongseong Baek
 */

@RequiredArgsConstructor
@Service
public class ScheduleService implements IScheduleService{
			
	private final ICalendarService calendarService;
	
	private final LocationRepository locationRepository;
	
	private final CalendarRepository calendarRepository;
	
	private final ScheduleRepository scheduleRepository;
	
	@Override
	public Schedule createSchedule(String loginId, ScheduleDto scheduleDto, Location location) {
    	Calendar calendar =calendarService.findByFetchLoginId(loginId,scheduleDto.getCalendarName());
		Schedule schedule = Schedule.createScheduleEntity(scheduleDto,location, calendar);
	    return scheduleRepository.save(schedule);
	}
	
	@Override
	public Schedule modifySchedule(String loginId, ScheduleDto scheduleDto) {
		Schedule schedule =this.findByFetchCalendarId(loginId,scheduleDto.getCalendarName(),scheduleDto.getPrevScheduleName());
		schedule.setName(scheduleDto.getCalendarName());
		schedule.setDay(scheduleDto.getDay());
		schedule.setText(scheduleDto.getScheduleName());
		schedule.setTime(scheduleDto.getTime());
		scheduleRepository.save(schedule);
	    return schedule;
	}
	
	@Override
	@Transactional
	public Schedule deleteSchedule(String loginId,ScheduleDto scheduleDto) {
		Calendar calendar = calendarRepository.findByFetchCalendar(calendarService.findByFetchLoginId(
				loginId, scheduleDto.getCalendarName()).getCalendarId()).orElseThrow(() -> new UsernameNotFoundException("not found calendar"));
		
		Schedule returnSchedule=null;
		for(Schedule schedule : calendar.getScheduleList())
			if(schedule.getName().equals(scheduleDto.getScheduleName()))
				{returnSchedule= schedule;calendar.getScheduleList().remove(schedule); break;}
		
		//returnSchedule.getLocation().setSchedule(null);
		scheduleRepository.delete(returnSchedule);
	    return returnSchedule;
	}
	
	@Override
	public Schedule findByFetchCalendarId(String loginId, String calendarName, String scheduleName) {
		Calendar calendar = calendarRepository.findByFetchCalendar(calendarService.findByFetchLoginId(
				loginId, calendarName).getCalendarId()).orElseThrow(() -> new UsernameNotFoundException("not found calendar"));
		
		Schedule returnSchedule=null;
		for(Schedule schedule : calendar.getScheduleList())
			if(schedule.getName().equals(scheduleName))
				{returnSchedule= schedule; break;}
		
		return returnSchedule;
	}
	
	@Override
	public List<Schedule> findByFetchCalendarIdOrThrow(String loginId,String calendarName) {
		Calendar calendar = calendarRepository.findByFetchCalendar(calendarService.findByFetchLoginId(
				loginId, calendarName).getCalendarId()).orElseThrow(() -> new UsernameNotFoundException("not found calendar"));
		return calendar.getScheduleList();
	}
	
	@Override
	public List<Schedule> findScheduleForAlarm(){
		List<Schedule> scheduleList = scheduleRepository.findAll();
		List<Schedule> returnList = new ArrayList<>();
		
		for(Schedule schedule : scheduleList) {
			if((long)(schedule.getTime().getTime() - new Date().getTime()) < 0)
				continue;
			java.util.Calendar cal1 = java.util.Calendar.getInstance();
			java.util.Calendar cal2 = java.util.Calendar.getInstance();
			cal1.setTime(schedule.getTime());
			cal2.setTime(new Timestamp(System.currentTimeMillis()));
			if((long)(cal1.getTime().getTime() - cal2.getTime().getTime()) / 60000 >= 600)  // 분으로 600분, 즉 10시간 미만이면 ㄱ
				continue;
			returnList.add(schedule);
		}
		return returnList;
	}
}
