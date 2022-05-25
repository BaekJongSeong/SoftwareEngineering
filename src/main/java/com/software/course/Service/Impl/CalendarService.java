package com.software.course.Service.Impl;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public List<Calendar> findByLoginIdAndNameOrThrow(String loginId, String calendarName) {
		Account account = accountRepository.findByFetchAccount(loginId).orElseThrow(() 
        		-> new UsernameNotFoundException("not found account"));
	
		return account.getCalendarList();
	}
	
	@Override
	public Calendar createCalendar(String loginId, String calendarName, Account account) {
		Calendar calendar = Calendar.createCalendarEntity(account, calendarName);
		//System.out.println(calendarName);
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
