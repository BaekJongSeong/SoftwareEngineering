package com.software.course.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.software.course.Entity.Calendar;

/**
 * @author Jongseong Baek
 */

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
	
	@Query("select DISTINCT c from Calendar c left join fetch c.scheduleList where c.calendarId=?1")
    Optional<Calendar> findByFetchCalendar(Integer calendarId);
}
