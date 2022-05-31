package com.software.course.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
public class Location {
	
	@Id
    @GeneratedValue
    @Column(name = "location_id")
    private Integer locationId;
	
	private String name;
	
    private Double latitude;  

    private Double longitude;
    
    @OneToOne(mappedBy = "location", fetch=FetchType.LAZY , cascade = CascadeType.REMOVE)
    private Account account;
    
    @OneToOne(mappedBy = "location", fetch=FetchType.LAZY , cascade = CascadeType.REMOVE)
    private Schedule schedule;

    public static Location createLocationEntity(String name, Double latitude, Double longitude) {
  		return Location.builder()
  			.name(name)
  			.latitude(latitude)
  			.longitude(longitude).build();
  		}
}
