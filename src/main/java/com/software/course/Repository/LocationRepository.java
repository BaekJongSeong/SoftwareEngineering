package com.software.course.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.software.course.Entity.Location;

/**
 * @author Jongseong Baek
 */

public interface LocationRepository extends JpaRepository<Location, Integer> {

}
