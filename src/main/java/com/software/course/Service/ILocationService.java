package com.software.course.Service;

import com.software.course.Entity.Location;
import com.software.course.Model.PathDto;

public interface ILocationService {
	
	public Location createLocation(String name, Double latitude, Double longitude);
	
    public Location modifyLocation(PathDto pathDto);
    
    public Location modifyLocationAccount(Location location,String name, Double latitude, Double longitude);

    public Location deleteLocation(PathDto pathDto);
    
    public Location findByFetchScheduleId(PathDto pathDto);
    
    public String pathSearching(PathDto pathDto);
}
