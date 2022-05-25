package com.software.course.Service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.software.course.Entity.Account;
import com.software.course.Entity.Location;
import com.software.course.Entity.Schedule;
import com.software.course.Model.PathDto;
import com.software.course.Repository.AccountRepository;
import com.software.course.Repository.LocationRepository;
import com.software.course.Service.ICalendarService;
import com.software.course.Service.ILocationService;
import com.software.course.Service.IScheduleService;

import lombok.RequiredArgsConstructor;

/**
 * @author Jongseong Baek
 */

@RequiredArgsConstructor
@Service
public class LocationService implements ILocationService{

	//private final IAccountService accountService;
	
	private final ICalendarService calendarService;
	
	private final IScheduleService scheduleService;
	
	private final AccountRepository accountRepository;
	
	private final LocationRepository locationRepository;
	
	@Override
    public Location createLocation(String name,Double latitude, Double longitude) {
    	return locationRepository.save(Location.createLocationEntity(name, latitude, longitude));
    }
	
    @Override
    public Location modifyLocation(PathDto pathDto) {
    	Location location = this.findByFetchScheduleId(pathDto);
    	location.setName(pathDto.getLocationDto().getName() != null 
    			? pathDto.getLocationDto().getName() : location.getName());
    	location.setLatitude(pathDto.getLocationDto().getLatitude() != null 
    			? pathDto.getLocationDto().getLatitude() : location.getLatitude());
    	location.setLongitude(pathDto.getLocationDto().getLongitude()!= null 
    			? pathDto.getLocationDto().getLongitude() : location.getLongitude());
    	return locationRepository.save(location);
    }
    
    @Override
    public Location modifyLocationAccount(Location location, String name,Double latitude, Double longitude) {
    	location.setName(name != null ? name : location.getName());
    	location.setLatitude(latitude !=null ? latitude : location.getLatitude());
    	location.setLongitude(longitude !=null ? longitude : location.getLongitude());
    	return locationRepository.save(location);
    }
    
    @Override
    @Transactional  //여기 문제있음!!!
    public Location deleteLocation(PathDto pathDto) {
    	Schedule schedule = scheduleService.findByFetchCalendarId(pathDto.getLoginId(),pathDto.getCalendarName(),pathDto.getScheduleName());
    	Location location = schedule.getLocation();
    	locationRepository.delete(location);
    	return location;
    }
    
    @Override
    public Location findByFetchScheduleId(PathDto pathDto) {
    	Schedule schedule = scheduleService.findByFetchCalendarId(pathDto.getLocationDto().getLoginId(),
    			pathDto.getCalendarName(), pathDto.getScheduleName());
    	return schedule.getLocation();
    }
    
}
