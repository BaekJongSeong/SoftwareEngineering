package com.software.course.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.software.course.Entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

}
