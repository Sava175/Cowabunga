package com.cowabunga.services;

import com.cowabunga.domain.Roll;
import com.cowabunga.dto.RollCreateDto;
import com.cowabunga.dto.RollDto;

import java.util.List;

public interface RollService {
    RollDto createRoll (RollDto rollDto);
    List<RollDto> getAllRolls();
}
