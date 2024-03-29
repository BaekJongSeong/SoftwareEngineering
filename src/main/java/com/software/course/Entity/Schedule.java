package com.software.course.Entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.software.course.Model.ScheduleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jongseong Baek
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedule")
public class Schedule {
	
	@Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private int scheduleId;
	
    private String name;
    
    private String text;
    
    private int day;
    
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    
    @OneToOne(fetch = FetchType.LAZY)
	private Location location;
   
    @ManyToOne(fetch = FetchType.LAZY)
    private Calendar calendar;
    
   @OneToMany(mappedBy = "schedule", fetch=FetchType.LAZY , cascade = CascadeType.ALL)
	 private List<Alarm> alarmList = new LinkedList<>();
   
   public static Schedule createScheduleEntity(ScheduleDto scheduleDto,Location location, Calendar calendar) {
	   return Schedule.builder()
 			.name(scheduleDto.getScheduleName())
 			.text(scheduleDto.getText())
 			.day(scheduleDto.getDay())
 			.time(scheduleDto.getTime())
 			.location(location)
 			.calendar(calendar).build();
 		}
}
