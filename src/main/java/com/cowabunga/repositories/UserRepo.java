package com.cowabunga.repositories;

import com.cowabunga.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
