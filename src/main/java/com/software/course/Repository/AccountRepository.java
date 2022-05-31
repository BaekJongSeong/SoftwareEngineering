package com.software.course.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.software.course.Entity.Account;

/**
 * @author Jongseong Baek
 */

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByLoginId(String loginId);
        
    @Query("select DISTINCT c from Account c left join fetch c.calendarList where c.loginId=?1")
    Optional<Account> findByFetchAccount(String loginId);
    
}
