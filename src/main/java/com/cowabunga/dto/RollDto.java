package com.cowabunga.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RollDto {
    private Integer id;
    private String name;
    private String description;
    private int price;
    private LocalDateTime date;
//    private byte[] data;
}
