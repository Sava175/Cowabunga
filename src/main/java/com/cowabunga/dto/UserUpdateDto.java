package com.cowabunga.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class UserUpdateDto {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String mobile;
    private Set<UserRoleDto> roles;
}
