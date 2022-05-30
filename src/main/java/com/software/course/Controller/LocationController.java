package com.software.course.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software.course.Entity.Location;
import com.software.course.Model.LocationDto;
import com.software.course.Model.PathDto;
import com.software.course.Model.ResDto1;
import com.software.course.Service.ILocationService;
import com.software.course.Service.IScheduleService;

import lombok.RequiredArgsConstructor;

/**
 * @author Jongseong Baek
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocationController {

	private final ILocationService locationService;
	
	private final IScheduleService scheduleService;
	
	@GetMapping("location/{loginId}/{calendarName}/{scheduleName}")
    public ResponseEntity<ResDto1<LocationDto>> getLocation (
    		@PathVariable String loginId,
			@PathVariable String calendarName,
			@PathVariable String scheduleName
    ) {
		Location location = locationService.getLocationByScheduleId(loginId,calendarName,scheduleName);
		return new ResponseEntity<>(ResDto1.createResDto(LocationDto.createLocationDto(
				loginId,location),1,0), new HttpHeaders(),HttpStatus.OK);
	}
		
	@PutMapping("/location")
    public ResponseEntity<ResDto1<LocationDto>> modifyLocation (@RequestBody PathDto pathDto) {
		Location location = locationService.modifyLocation(pathDto);
		return new ResponseEntity<>(ResDto1.createResDto(LocationDto.createLocationDto(
				pathDto.getLocationDto().getLoginId(),location),1,0), new HttpHeaders(),HttpStatus.OK);
	}
	
	@DeleteMapping("/location")
	public ResponseEntity<ResDto1<LocationDto>> deleteLocation (@RequestBody PathDto pathDto) {
		Location location = locationService.deleteLocation(pathDto);
		return new ResponseEntity<>(ResDto1.createResDto(LocationDto.builder().name(location.getName())
				.latitude(location.getLatitude()).longitude(location.getLongitude()).build(),1,0), new HttpHeaders(),HttpStatus.OK);
	}

}
