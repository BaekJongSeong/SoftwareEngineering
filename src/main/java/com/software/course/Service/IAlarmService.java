package com.software.course.Service;

import java.io.IOException;
import java.util.Date;

import com.software.course.Entity.Alarm;
import com.software.course.Entity.Schedule;
import com.software.course.Model.ScheduleDto;

public interface IAlarmService {

	public Alarm createAlarm(Date time, Schedule schedule);
	
	public Alarm modifyAlarm(Date time, Schedule schedule);
	
	public Alarm deleteAlarm(Schedule schedule);
	
	public String makeAlarmContent(ScheduleDto scheduleDto, Schedule schedule) throws IOException ;
}
