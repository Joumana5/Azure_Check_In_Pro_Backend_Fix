package com.example.tcu.checkinpro.Authentication;

import com.example.tcu.checkinpro.DTO.LoginDTO;
import com.example.tcu.checkinpro.Service.UserService;
import com.example.tcu.checkinpro.Util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class Login {

    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public Login(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginRequest, HttpServletRequest request) {
        logger.debug("Login attempt for email: {}", loginRequest.getEmail());
        boolean isAuthenticated = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (isAuthenticated) {
            logger.debug("Authentication successful for email: {}", loginRequest.getEmail());
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword(), new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtil.generateToken(loginRequest.getEmail());
            logger.debug("Generated token for email: {}", loginRequest.getEmail());
            return token;
        } else {
            logger.debug("Authentication failed for email: {}", loginRequest.getEmail());
            return "Invalid email or password";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Logout attempt");
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "Logout successful";
    }
}