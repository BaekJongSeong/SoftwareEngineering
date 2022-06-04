package com.software.course.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name = "place")
public class Place {
	
	@Id
    @GeneratedValue
    @Column(name = "place_id")
    private int placeId;
	
    private String name;  

    @ManyToOne
    private Location location;
    
    @ManyToOne
    private Schedule schedule;
    
    public static Place createPlaceEntity(String name,Location location, Schedule schedule) {
  		return Place.builder()
  			.name(name)
  			.location(location)
  			.schedule(schedule).build();
  		}
}
