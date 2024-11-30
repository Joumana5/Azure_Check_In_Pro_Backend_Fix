package com.example.tcu.checkinpro.Authentication;

import com.example.tcu.checkinpro.DTO.employeeDTO;
import com.example.tcu.checkinpro.Entity.employeeEntity;
import com.example.tcu.checkinpro.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class Register {

    private final UserService userService;

    @Autowired
    public Register(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody employeeEntity employee) {
        userService.registerUser(employee);
        return ResponseEntity.ok("User registered successfully");
    }
}