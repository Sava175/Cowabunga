package com.cowabunga.services;

import com.cowabunga.domain.Roll;
import com.cowabunga.dto.RollDto;
import com.cowabunga.mapper.RollMapper;
import com.cowabunga.repositories.RollRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RollServiceImpl implements RollService{
    @Autowired
    private RollRepo rollRepo;
    @Override
    public RollDto createRoll (RollDto rollDto) {
        Roll roll = rollRepo.findByName(rollDto.getName());
        if (roll == null){
            roll = new Roll();
            roll.setName(rollDto.getName());
            roll.setDescription(rollDto.getDescription());
            roll.setPrice(rollDto.getPrice());
            return RollMapper.toRollDto(rollRepo.save(roll));
        }throw new RuntimeException("User duplicate email");
    }

    @Override
    public List<RollDto> getAllRolls() {
        return RollMapper.toRollDtoList(rollRepo.findAll());
    }
}
