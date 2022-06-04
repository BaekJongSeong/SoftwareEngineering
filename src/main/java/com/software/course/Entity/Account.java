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
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "account")
public class Account {
	
	@Id
    @GeneratedValue
    @Column(name = "account_id")
    private int accountId;
	
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;  

    @Column(unique = true, name="login_id")
    private String loginId;
    
    @JsonIgnore
    private String password;

    private String name;
    
    @Column(name = "email_address")
    private String emailAddress;
    
    private String phone;
    
    private int status;
    
    @OneToOne(fetch = FetchType.LAZY)
    private Location location;
	
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountGroup accountGroup;
    
    @OneToMany(mappedBy = "account", fetch=FetchType.LAZY , cascade = CascadeType.ALL)
	 private List<Calendar> calendarList = new LinkedList<>();
    
	 public static Account createAccountEntity(SignupDto signupDto, String password, AccountGroup accountGroup, Location location) {
		return Account.builder()
			.createAt(new Timestamp(System.currentTimeMillis()))
	         .loginId(signupDto.getLoginId())
	         .password(password)
	         .name(signupDto.getName())
	         .emailAddress(signupDto.getEmail())
	         .phone(signupDto.getPhone())
	         .status(0)
	         .location(location)
	         .accountGroup(accountGroup).build();
		}
}
