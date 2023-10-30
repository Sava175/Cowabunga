package com.cowabunga.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);


        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ADMIN".equals(auth.getAuthority())) {
                response.sendRedirect("/welcomeadmin");
            }
            else if ("CLIENT".equals(auth.getAuthority())) {
                response.sendRedirect("/welcome");
            }
            else if ("BLOCKED".equals(auth.getAuthority())) {
                response.sendRedirect("/blocked");
            }
        }
    }
}
