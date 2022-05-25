package com.software.course.Controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software.course.Entity.Account;
import com.software.course.Entity.Calendar;
import com.software.course.Model.CalendarDto;
import com.software.course.Model.ResDto1;
import com.software.course.Model.ResDto2;
import com.software.course.Service.IAccountService;
import com.software.course.Service.ICalendarService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

/**
 * @author Jongseong Baek
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CalendarController {
	
	 private final ICalendarService calendarService;
	 
	 private final IAccountService accountService;
	 
	    @GetMapping("/calendar/{loginId}")
	    public ResponseEntity<ResDto2<CalendarDto>> getList (
	    		@PathVariable String loginId
	    ){
	    	List<Calendar> calendarList = calendarService.findByLoginIdAndNameOrThrow(loginId,null);
	    	return new ResponseEntity<>(ResDto2.createResDto(
	    			CalendarDto.createCalendarDtoList(calendarList),1,0),
	    			new HttpHeaders(),HttpStatus.OK);	    
	    	}
	    
	    @PostMapping("/calendar/{loginId}")
	    public ResponseEntity<ResDto1<CalendarDto>> createCalendar (
	    		@Validated @RequestBody CalendarDto calendarDto,
	    		@PathVariable String loginId
	    ){
			Account account =accountService.findByLoginIdOrReturn(loginId);
	    	Calendar calendar = calendarService.createCalendar(loginId,calendarDto.getCalendarName(),account);
	    	return new ResponseEntity<>(ResDto1.createResDto(CalendarDto.createCalendarDto(calendar),1,0),
	    			new HttpHeaders(),HttpStatus.OK);
	    }
	    
	    @PutMapping("/calendar/{loginId}")
	    public ResponseEntity<ResDto1<CalendarDto>> modifyCalendar (
	    		@PathVariable String loginId,
	    		@Validated @RequestBody CalendarDto reCalendar
	    ){
	    	Calendar calendar = calendarService.modifyCalendar(loginId,reCalendar.getPrevcalendarName(),reCalendar.getCalendarName());
	    	return new ResponseEntity<>(ResDto1.createResDto(CalendarDto.createCalendarDto(calendar),1,0),
	    			new HttpHeaders(),HttpStatus.OK);
	    }
	    
	    @DeleteMapping("/calendar/{loginId}")
	    public ResponseEntity<ResDto1<CalendarDto>> deleteCalendar (
	    		@PathVariable String loginId,
	    		@Validated @RequestBody CalendarDto reCalendar
	    ){
	    	Calendar calendar = calendarService.deleteCalendar(loginId,reCalendar.getCalendarName());
	    	return new ResponseEntity<>(ResDto1.createResDto(CalendarDto.builder().calendarName(calendar.getName()).build(),1,0),
	    			new HttpHeaders(),HttpStatus.OK);
	    }
	    
	    
}
