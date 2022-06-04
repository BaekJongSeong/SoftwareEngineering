package com.software.course.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.software.course.Entity.AccountGroup;

/**
 * @author Jongseong Baek
 */

public interface AccountGroupRepository extends JpaRepository<AccountGroup, Integer> {
	
	@Query("select DISTINCT c from AccountGroup c where c.useAt=?1")
	List<AccountGroup> findUnused(int use);
}
