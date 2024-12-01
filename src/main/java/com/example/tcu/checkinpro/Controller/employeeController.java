package com.example.tcu.checkinpro.Controller;

import com.example.tcu.checkinpro.DTO.employeeDTO;
import com.example.tcu.checkinpro.Entity.employeeEntity;
import com.example.tcu.checkinpro.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Employee")
public class employeeController {

    private final EmployeeService employeeService;

    @Autowired
    public employeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@RequestBody employeeDTO employeeDTO) {
        return employeeService.addEmployee(employeeDTO);
    }

    @GetMapping("/allEmployees")
    public List<employeeEntity> allEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/updateEmployee/{employee_id}")
    public String updateEmployee(@PathVariable Integer employee_id, @RequestBody employeeDTO employeeDTO) {
        return employeeService.updateEmployee(employee_id, employeeDTO);
    }

    @DeleteMapping("/deleteEmployee/{employee_id}")
    public String deleteEmployee(@PathVariable Integer employee_id) {
        return employeeService.deleteEmployee(employee_id);
    }
}