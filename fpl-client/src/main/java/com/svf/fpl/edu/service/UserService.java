package com.svf.fpl.edu.service;

import com.svf.fpl.edu.entity.AdminUser;
import com.svf.fpl.edu.entity.AdminUserRole;
import com.svf.fpl.edu.repositories.AdminUserRepository;
import com.svf.fpl.edu.repositories.AdminUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private AdminUserRoleRepository adminUserRoleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUser findUserByEmail(String email) {
        return adminUserRepository.findByEmail(email);
    }

    public AdminUser findUserByUserName(String userName) {
        return adminUserRepository.findByUserName(userName);
    }

    public AdminUser saveUser(AdminUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        AdminUserRole userRole = adminUserRoleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        return adminUserRepository.save(user);
    }
}
