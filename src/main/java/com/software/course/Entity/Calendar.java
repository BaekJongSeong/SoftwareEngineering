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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.software.course.Model.SignupDto;

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
@Table(name = "calendar")
public class Calendar {
	
	@Id
    @GeneratedValue
    @Column(name = "calendar_id")
    private int calendarId;
	
	private String name;
	
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;  

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    
    @OneToMany(mappedBy = "calendar", fetch=FetchType.LAZY , cascade = CascadeType.REMOVE)
	 private List<Schedule> scheduleList = new LinkedList<>();
    
    public static Calendar createCalendarEntity(Account account, String calendarName) {
		return Calendar.builder()
			.name(calendarName)
			.createAt(new Timestamp(System.currentTimeMillis()))
			.account(account).build();
		}
}