package com.cowabunga.mapper;

import com.cowabunga.domain.Roll;
import com.cowabunga.dto.RollDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RollMapper {
    public static RollDto toRollDto(Roll roll){
        return  RollDto.builder()
                .name(roll.getName())
                .description(roll.getDescription())
                .price(roll.getPrice())
                .build();
    }
    public static List<RollDto> toRollDtoList(List<Roll> rolls) {
        List<RollDto> rollDtos = new ArrayList<>();
        for (Roll roll : rolls) {
            RollDto rollDto = toRollDto(roll);
            rollDtos.add(rollDto);
        }
        return rollDtos;
    }
}
