package com.cowabunga.controllers;

import com.cowabunga.dto.RollDto;
import com.cowabunga.services.RollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RollController {
    @Autowired
    RollService rollService;
    @GetMapping("/rolls")
    public String getAllRolls(Model model) {
        List<RollDto> rolls = rollService.getAllRolls();
        model.addAttribute("rolls", rolls);
        return "rolls"; // This returns the "rolls.html" template to be rendered
    }
}
