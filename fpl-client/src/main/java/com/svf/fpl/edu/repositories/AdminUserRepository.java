package com.svf.fpl.edu.repositories;

import com.svf.fpl.edu.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    AdminUser findByEmail(String email);

    AdminUser findByUserName(String userName);
}
