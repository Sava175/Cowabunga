package com.cowabunga;

import com.cowabunga.domain.Roll;
import com.cowabunga.domain.User;
import com.cowabunga.domain.UserRole;
import com.cowabunga.dto.RollDto;
import com.cowabunga.dto.UserDto;
import com.cowabunga.repositories.UserRepo;
import com.cowabunga.repositories.UserRoleRepo;
import com.cowabunga.services.RollService;
import com.cowabunga.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class CowabungaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CowabungaApplication.class, args);
    }


    @Bean
    CommandLineRunner init(BCryptPasswordEncoder bCryptPasswordEncoder, UserRoleRepo roleRepository, UserService userService, UserRepo userRepo, RollService rollService) {
        return args -> {

            UserRole adminRole = roleRepository.findByName("ADMIN");
            if (adminRole == null) {
                adminRole = new UserRole();
                adminRole.setName("ADMIN");
                roleRepository.save(adminRole);
            }

            UserRole clientRole = roleRepository.findByName("CLIENT");
            if (clientRole == null) {
                clientRole = new UserRole();
                clientRole.setName("CLIENT");
                roleRepository.save(clientRole);
            }

            UserRole blockedRole = roleRepository.findByName("BLOCKED");
            if (blockedRole == null) {
                blockedRole = new UserRole();
                blockedRole.setName("BLOCKED");
                roleRepository.save(blockedRole);
            }

            UserDto admin = new UserDto();
            admin.setEmail("admin@gmail.com");
            admin.setPassword("123456");
            admin.setName("Max");
            admin.setSurname("Pain");
            admin.setMobile("0735435587");
            admin.setAdmin(true);

            userService.signup(admin);


            UserDto client = new UserDto();
            client.setEmail("client@gmail.com");
            client.setPassword("123456");
            client.setName("Alex");
            client.setSurname("Smith");
            client.setMobile("073834383");
            client.setAdmin(false);

            userService.signup(client);


            User blocked = new User();
            blocked.setEmail("blocked@gmail.com");
            blocked.setPassword(bCryptPasswordEncoder.encode("123456"));
            blocked.setName("Alex");
            blocked.setSurname("Smith");
            blocked.setMobile("073834384");
            blocked.getRoles().add(blockedRole);

            blocked = userRepo.save(blocked);


            RollDto philadelphia = new RollDto();
            philadelphia.setName("philadelphia");
            philadelphia.setDescription("tender salmon on top");
            philadelphia.setPrice(345);
            philadelphia = rollService.createRoll(philadelphia);

            RollDto dragon = new RollDto();
            dragon.setName("Dragon");
            dragon.setDescription("Dragon with fried shrimp");
            dragon.setPrice(375);
            dragon = rollService.createRoll(dragon);

            RollDto california = new RollDto();
            california.setName("California");
            california.setDescription("California with cream and eel");
            california.setPrice(365);
            california = rollService.createRoll(california);














        };
    }
}
