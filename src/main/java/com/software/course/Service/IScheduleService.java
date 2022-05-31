package com.software.course.Service;

import java.util.List;

import com.software.course.Entity.Location;
import com.software.course.Entity.Schedule;
import com.software.course.Model.ScheduleDto;

public interface IScheduleService {

	public Schedule createSchedule(String loginId, ScheduleDto scheduleDto, Location location);
	
	public Schedule modifySchedule(String loginId, ScheduleDto scheduleDto);
	
	public Schedule deleteSchedule(String loginId, ScheduleDto scheduleDto);
	
	public Schedule findByFetchCalendarId(String loginId, String calendarName, String scheduleName);

	public List<Schedule> findByFetchCalendarIdOrThrow(String loginId,String calendarName);
	
	public List<Schedule> findScheduleForAlarm();
}
