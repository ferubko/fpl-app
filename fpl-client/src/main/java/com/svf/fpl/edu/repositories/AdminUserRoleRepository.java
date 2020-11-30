package com.svf.fpl.edu.repositories;

import com.svf.fpl.edu.entity.AdminUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRoleRepository extends JpaRepository<AdminUserRole, Integer> {
    AdminUserRole findByRole(String role);
}
