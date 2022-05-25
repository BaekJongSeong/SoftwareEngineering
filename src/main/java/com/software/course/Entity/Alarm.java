package com.software.course.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class Alarm {
	
	@Id
    @GeneratedValue
    @Column(name = "alarm_id")
    private Integer alarmId;
	
	@Column(name = "push_access")
	private int pushAccess;
	
	@Column(name = "alarm_before")
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date alarmBefore;

    @OneToOne(fetch = FetchType.LAZY)
    private Schedule schedule;
    
    public static Alarm createAlarmEntity(Date date, Schedule schedule) {
		return Alarm.builder()
			.pushAccess(1)
         .alarmBefore(date)
         .schedule(schedule).build();
		}
}
