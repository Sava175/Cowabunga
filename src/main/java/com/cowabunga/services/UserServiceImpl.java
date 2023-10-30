package com.cowabunga.services;

import com.cowabunga.domain.User;
import com.cowabunga.domain.UserRole;
import com.cowabunga.dto.UserDto;
import com.cowabunga.dto.UserRoleDto;
import com.cowabunga.dto.UserUpdateDto;
import com.cowabunga.mapper.UserMapper;
import com.cowabunga.repositories.UserRepo;
import com.cowabunga.repositories.UserRoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;

    @Override
    public UserDto blockingUser(String email) {
        UserRole blocked = userRoleRepo.findByName("BLOCKED");
        User user = userRepo.findByEmail(email);
        UserRole clientRole = null;
        for (UserRole role : user.getRoles()) {
            if (role.getName().equals("CLIENT")) {
                clientRole = role;
                break;
            }
        }
        if (clientRole != null) {
            user.getRoles().remove(clientRole);
        }
        user.getRoles().add(blocked);
        user = userRepo.save(user);
        return new UserDto()
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setMobile(user.getMobile())
                .setRoles(user.getRoles().stream().map(role -> new UserRoleDto().setName(role.getName())).collect(Collectors.toSet()));
    }


    @Override
    public UserDto findUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepo.findByEmail(email));
        User some;
        if (user.isPresent()) {
            some = user.get();
            return new UserDto()
                    .setEmail(some.getEmail())
                    .setPassword(some.getPassword())
                    .setName(some.getName())
                    .setSurname(some.getSurname())
                    .setMobile(some.getMobile())
                    .setRoles(some.getRoles().stream().map(role -> new UserRoleDto().setName(role.getName())).collect(Collectors.toSet()));
        }
        throw new RuntimeException("User with email :" + email + " not found");
    }

    @Override
    public UserDto signup(UserDto userDto) {
        User user = userRepo.findByEmail(userDto.getEmail());
        UserRole userRole;
        if (user == null) {
            if (userDto.isAdmin()) {
                userRole = userRoleRepo.findByName("ADMIN");
            }
            else {
                userRole = userRoleRepo.findByName("CLIENT");
            }


            user = new User();
            user.setEmail(userDto.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            user.setRoles(new HashSet<>(Arrays.asList(userRole)));
            user.setName(userDto.getName());
            user.setSurname(userDto.getSurname());
            user.setMobile(userDto.getMobile());
            return UserMapper.toUserDto(userRepo.save(user));
        }
        throw new RuntimeException("User duplicate email");
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepo.findByEmail(email);
    }

    @Override
    public UserUpdateDto updateUser(User userCurrent, User userForm) {

        userCurrent.setName(userForm.getName());
        userCurrent.setSurname(userForm.getSurname());
        userCurrent.setEmail(userForm.getEmail());
        userCurrent.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        userCurrent.setMobile(userForm.getMobile());
        userCurrent = userRepo.save(userCurrent);

        return UserMapper.userToUserUpdateDto(userCurrent);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepo.deleteById(id);
    }



    //    @Override
//    public UserUpdateDto blockingUser(String email) {
//
//        User user = userRepo.findByEmail(email);
//        UserRole userRole = userRoleRepo.findByName("ADMIN");
//
//
////        UserRole clientRole = null;
////        for (UserRole role : user.getRoles()) {
////            if (role.getName().equals("CLIENT")) {
////                clientRole = role;
////                break;
////            }
////        }
////        if (clientRole != null) {
////            user.getRoles().remove(clientRole);
////        }
////        user.getRoles().add(blockedRole);
//        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
//        user = userRepo.save(user);
//        return UserMapper.userToUserUpdateDto(user);
////        user.setRoles(new HashSet<>(Arrays.asList(userRole)))
//    }
}
