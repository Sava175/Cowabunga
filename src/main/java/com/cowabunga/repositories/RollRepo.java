package com.cowabunga.repositories;

import com.cowabunga.domain.Roll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RollRepo extends JpaRepository<Roll, Integer> {
    Roll findByName(String name);
    List<Roll> findAll();

}
