package com.software.course.Service.Impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.software.course.Entity.Alarm;
import com.software.course.Entity.Schedule;
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

}
