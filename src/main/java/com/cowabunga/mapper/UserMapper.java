package com.cowabunga.mapper;

import com.cowabunga.domain.User;
import com.cowabunga.domain.UserRole;
import com.cowabunga.dto.UserDto;
import com.cowabunga.dto.UserRoleDto;
import com.cowabunga.dto.UserUpdateDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setMobile(user.getMobile())

                .setRoles(new HashSet<>(user
                        .getRoles()
                        .stream()
                        .map(role -> new ModelMapper().map(role, UserRoleDto.class))
                        .collect(Collectors.toSet())));
    }




    public static UserUpdateDto userToUserUpdateDto(User user){
        return UserUpdateDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(user.getPassword())
                .mobile(user.getMobile())
                .roles(new HashSet<>(user
                        .getRoles()
                        .stream()
                        .map(role -> new ModelMapper().map(role, UserRoleDto.class))
                        .collect(Collectors.toSet())))
                .build();
    }


        public UserDto mapUserToDto(User user) {
            UserDto userDto = new UserDto();
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());
            userDto.setName(user.getName());
            userDto.setSurname(user.getSurname());
            userDto.setMobile(user.getMobile());
            userDto.setRoles(mapUserRoleSetToDto(user.getRoles()));
            return userDto;
        }
    private Set<UserRoleDto> mapUserRoleSetToDto(Set<UserRole> roles) {
        Set<UserRoleDto> roleDtos = new HashSet<>();
        for (UserRole role : roles) {
            UserRoleDto roleDto = new UserRoleDto();
            // Map the properties from UserRole to UserRoleDto
            roleDto.setName(role.getName());
            // ... map other properties if needed
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }
}
