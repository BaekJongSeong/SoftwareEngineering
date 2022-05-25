package com.software.course.Service;

import java.util.List;

import com.software.course.Entity.Account;
import com.software.course.Entity.Calendar;

public interface ICalendarService {
	
	public List<Calendar> findByLoginIdAndNameOrThrow(String loginId, String calendarName);
	
	public Calendar createCalendar(String loginId, String calendarName, Account account);
	
	public Calendar modifyCalendar(String loginId,String prevCalendarName, String calendarName);
	
	public Calendar deleteCalendar(String loginId, String calendarName);
	
	public Calendar findByFetchLoginId(String loginId, String calendarName);
}
