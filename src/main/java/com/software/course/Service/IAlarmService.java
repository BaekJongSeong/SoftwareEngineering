package com.software.course.Service;

import java.util.Date;

import com.software.course.Entity.Alarm;
import com.software.course.Entity.Schedule;

public interface IAlarmService {

	public Alarm createAlarm(Date time, Schedule schedule);
	
	public Alarm modifyAlarm(Date time, Schedule schedule);
	
	public Alarm deleteAlarm(Schedule schedule);
}
