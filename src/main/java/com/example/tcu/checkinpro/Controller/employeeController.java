package com.example.tcu.checkinpro.Controller;

import com.example.tcu.checkinpro.DTO.employeeDTO;
import com.example.tcu.checkinpro.Entity.employeeEntity;
import com.example.tcu.checkinpro.Service.EmployeeService;
import com.example.tcu.checkinpro.Service.CustomUserDetailsService;
import com.example.tcu.checkinpro.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Employee")
public class employeeController {

    private final EmployeeService employeeService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userService;

    @Autowired
    public employeeController(EmployeeService employeeService, JwtUtil jwtUtil, CustomUserDetailsService userService) {
        this.employeeService = employeeService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@RequestHeader("Authorization") String token, @RequestBody employeeDTO employeeDTO) {
        String email = jwtUtil.extractEmail(token);
        if (jwtUtil.isTokenValid(token, email)) {
            return employeeService.addEmployee(employeeDTO);
        } else {
            return "Unauthorized";
        }
    }

    @GetMapping("/allEmployees")
    public List<employeeEntity> allEmployees(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token);
        if (jwtUtil.isTokenValid(token, email)) {
            return employeeService.getAllEmployees();
        } else {
            return null; // or throw an exception
        }
    }

    @PutMapping("/updateEmployee/{employee_id}")
    public String updateEmployee(@RequestHeader("Authorization") String token, @PathVariable Integer employee_id, @RequestBody employeeDTO employeeDTO) {
        String email = jwtUtil.extractEmail(token);
        if (jwtUtil.isTokenValid(token, email)) {
            return employeeService.updateEmployee(employee_id, employeeDTO);
        } else {
            return "Unauthorized";
        }
    }

    @DeleteMapping("/deleteEmployee/{employee_id}")
    public String deleteEmployee(@RequestHeader("Authorization") String token, @PathVariable Integer employee_id) {
        String email = jwtUtil.extractEmail(token);
        if (jwtUtil.isTokenValid(token, email)) {
            return employeeService.deleteEmployee(employee_id);
        } else {
            return "Unauthorized";
        }
    }
}