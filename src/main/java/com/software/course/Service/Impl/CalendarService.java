package com.software.course.Service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.software.course.Entity.Account;
import com.software.course.Entity.Calendar;
import com.software.course.Repository.AccountRepository;
import com.software.course.Repository.CalendarRepository;
import com.software.course.Service.ICalendarService;

import lombok.RequiredArgsConstructor;

/**
 * @author Jongseong Baek
 */

@RequiredArgsConstructor
@Service
public class CalendarService implements ICalendarService{
	
	private final AccountRepository accountRepository;
	
	private final CalendarRepository calendarRepository;
	
	
	//for test
	@Override
	public List<Calendar> findByLoginIdAndNameOrThrow(String loginId, String calendarName, int flag) {
		Account account = accountRepository.findByFetchAccount(loginId).orElseThrow(() 
        		-> new UsernameNotFoundException("not found account"));
		if(flag == 0) {
			for(Calendar calendar : account.getCalendarList())
				if(calendar.getName().equals(calendarName))
					return new ArrayList<Calendar>(Arrays.asList(calendar));
		}
		
		return account.getCalendarList();
	}
	
	@Override
	public Calendar createCalendar(String loginId, String calendarName, Account account) {
		Calendar calendar = Calendar.createCalendarEntity(account, calendarName);
		calendarRepository.save(calendar);
	    return calendar;
	}
	
	@Override
	public Calendar modifyCalendar(String loginId,String prevCalendarName, String calendarName) {
		Calendar calendar =this.findByFetchLoginId(loginId,prevCalendarName);
		calendar.setName(calendarName);
		calendarRepository.save(calendar);
	    return calendar;
	}
	
	@Override
	public Calendar deleteCalendar(String loginId, String calendarName) {
		Account account = accountRepository.findByFetchAccount(loginId).orElseThrow(() 
        		-> new UsernameNotFoundException("not found account"));
		Calendar returnCalendar=null;
		for(Calendar calendar: account.getCalendarList())
			if(calendar.getName().equals(calendarName))
				{returnCalendar=calendar;account.getCalendarList().remove(calendar);break;}
		
		calendarRepository.deleteById(returnCalendar.getCalendarId());
		return returnCalendar;
	}
	
	@Override
	public Calendar findByFetchLoginId(String loginId, String calendarName) {
		Account account = accountRepository.findByFetchAccount(loginId).orElseThrow(() 
        		-> new UsernameNotFoundException("not found account"));
	
		Calendar returnCalendar=null;
		for(Calendar calendar: account.getCalendarList())
			if(calendar.getName().equals(calendarName))
				{returnCalendar= calendar;break;}
		
		return returnCalendar;
	}
	
	
}
