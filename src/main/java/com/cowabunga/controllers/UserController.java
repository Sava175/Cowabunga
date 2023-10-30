package com.cowabunga.controllers;

import com.cowabunga.domain.User;
import com.cowabunga.dto.UserCreateRequestDto;
import com.cowabunga.dto.UserDto;
import com.cowabunga.dto.UserUpdateDto;
import com.cowabunga.exeption.UserUpdateException;
import com.cowabunga.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/signup")
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("userCreateRequestDto", new UserCreateRequestDto());
        return modelAndView;
    }

    @PostMapping(value = "/signup")
    public ModelAndView createNewAdmin(@Valid @ModelAttribute("userCreateRequestDto") UserCreateRequestDto userSignupFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("signup");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        } else {
            try {
                UserDto newUser = registerUser(userSignupFormCommand);
            } catch (Exception exception) {
                bindingResult.rejectValue("email", "error.adminSignupFormCommand", exception.getMessage());
                return modelAndView;
            }
        }
        return new ModelAndView("redirect:/login");
    }


    private UserDto registerUser(UserCreateRequestDto userCreateRequestDto) {
        UserDto userDto = new UserDto();
        userDto.setEmail(userCreateRequestDto.getEmail());
        userDto.setPassword(userCreateRequestDto.getPassword());
        userDto.setName(userCreateRequestDto.getName());
        userDto.setSurname(userCreateRequestDto.getSurname());

        return userService.signup(userDto);
    }


    @GetMapping(value = {"/", "/login"})
    public ModelAndView login() {
        return new ModelAndView("login");
    }


    @GetMapping(value = "/welcome")
    public ModelAndView welcome() {
        return new ModelAndView("welcome");
    }
    @GetMapping(value = "/blocked")
    public ModelAndView blockedMan() {
        return new ModelAndView("blocked");
    }

    @GetMapping(value = "/welcomeadmin")
    public ModelAndView welcomeChief() {
        return new ModelAndView("welcomeadmin");
    }

    @GetMapping(value = "/wishes")
    public ModelAndView wishes() {
        return new ModelAndView("wishes");
    }


    @GetMapping("/profile")
    public String showUserProfile(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "profile";
    }


    @PostMapping("/profile")
    public String updateUserProfile(@ModelAttribute("user") User userForm) {
        try {
            UserUpdateDto some = userService.updateUser(userService.getCurrentUser(), userForm);
            return "redirect:/login";
        } catch (RuntimeException e) {
            throw new UserUpdateException("something wrong with update " + e.getMessage(), e);
        }
    }

    @PostMapping("/blockUser")
    public ModelAndView blockUser(@RequestParam("email") String email) {
        try {
            UserDto some = userService.blockingUser(email);
            return new ModelAndView("redirect:/blocked");
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("uuups");
            modelAndView.addObject("message", "An error occurred while blocking the user.");
            return modelAndView;
        }
    }


    @GetMapping("/delete")// here to look
    public ModelAndView deleteUser() {
        try {
            User currentUser = userService.getCurrentUser();
            userService.deleteUser(currentUser.getId());
            return new ModelAndView("redirect:/login");
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("uuups");
            modelAndView.addObject("message", "An error occurred while deleting the user.");
            return modelAndView;
        }
    }



    @GetMapping(value = "/findaccount")
    public ModelAndView findAccountForm() {
        return new ModelAndView("findaccount");
    }

    @PostMapping(value = "/findaccount/submit")
    public ModelAndView submitEmail(@RequestParam("email") String email) {
        ModelAndView modelAndView = new ModelAndView("userinformation");
        modelAndView.addObject("email", email);
        UserDto user = userService.findUserByEmail(email);
        if (user != null) {
            modelAndView.addObject("user", user);
        }
        return modelAndView;
    }
    @GetMapping("/userinformation")
    public ModelAndView showUserInformation(@ModelAttribute("user") UserDto user) {
        ModelAndView modelAndView = new ModelAndView("userinformation");
        try {
            modelAndView.addObject("user", user);
        } catch (Exception e) {
            modelAndView.addObject("error", "An error occurred while processing the request.");
        }
        return modelAndView;
    }


}
