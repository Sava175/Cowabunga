package com.cowabunga.configurations;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController, CustomErrorControllerINt {
    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public String handleError() {
        return "/uuups";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
