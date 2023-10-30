package com.cowabunga.repositories;

import com.cowabunga.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {
    UserRole findByName(String name);
}
