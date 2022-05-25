package com.software.course.Model;

import com.software.course.Entity.Location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jongseong Baek
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
	
	private String loginId;
	
	private String name;
	
    private Double latitude;
    
    private Double longitude;
    
    public static LocationDto createLocationDto(String loginId, Location location) {
    	return LocationDto.builder()
    			.loginId(loginId)
    			.name(location.getName())
			    .latitude(location.getLatitude())
			    .longitude(location.getLongitude()).build();
	}
}
