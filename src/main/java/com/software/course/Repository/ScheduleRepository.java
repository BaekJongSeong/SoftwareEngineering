package com.software.course.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.software.course.Entity.Schedule;

/**
 * @author Jongseong Baek
 */

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

	Schedule findByName(String name);
	
	Integer deleteByName(String name);
	
    @Query("select DISTINCT c from Schedule c left join fetch c.alarmList where c.scheduleId=?1")
	Schedule findByFetchScheduleId(Integer scheduleId);
}
