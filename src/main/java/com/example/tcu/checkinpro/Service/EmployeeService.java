package com.example.tcu.checkinpro.Service;

import com.example.tcu.checkinpro.Entity.employeeEntity;
import com.example.tcu.checkinpro.DTO.employeeDTO;
import com.example.tcu.checkinpro.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

public ResponseEntity<String> addEmployee(employeeDTO employeeDTO) {
    Optional<employeeEntity> checkEmployee = employeeRepository.findByEmail(employeeDTO.getEmail());
    if (checkEmployee.isPresent()) {
        return new ResponseEntity<>("{\"message\": \"Employee with this email already exists\"}", HttpStatus.CONFLICT);
    } else {
        employeeEntity employeeEntity = new employeeEntity(
                employeeDTO.getName(),
                employeeDTO.getEmail(),
                employeeDTO.getPassword(),
                employeeDTO.getPhone(),
                employeeDTO.getDepartment(),
                employeeDTO.getStatus()
        );

        employeeRepository.save(employeeEntity);

        String response = String.format(
            "{\"message\": \"Employee added\", \"employee\": {\"id\": %d, \"name\": \"%s\", \"email\": \"%s\", \"phone\": \"%s\", \"department\": \"%s\", \"status\": \"%s\"}}",
            employeeEntity.getId(),
            employeeEntity.getName(),
            employeeEntity.getEmail(),
            employeeEntity.getPhone(),
            employeeEntity.getDepartment(),
            employeeEntity.getStatus()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
    public List<employeeEntity> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public String updateEmployee(Integer employeeId, employeeDTO employeeDTO) {
        Optional<employeeEntity> employeeEntity = employeeRepository.findById(Long.valueOf(employeeId));

        if (employeeEntity.isPresent()) {
            employeeEntity oldEmployee = employeeEntity.get();
            oldEmployee.setName(employeeDTO.getName());
            oldEmployee.setEmail(employeeDTO.getEmail());
            oldEmployee.setPhone(employeeDTO.getPhone());
            oldEmployee.setDepartment(employeeDTO.getDepartment());
            oldEmployee.setStatus(employeeDTO.getStatus());
            employeeRepository.save(oldEmployee);
            return "Employee updated";
        } else {
            return "Employee not found";
        }
    }

    public String deleteEmployee(Integer employeeId) {
        Optional<employeeEntity> employeeEntity = employeeRepository.findById(Long.valueOf(employeeId));
        if (employeeEntity.isEmpty()) {
            return "Employee not found";
        } else {
            employeeRepository.deleteById(Long.valueOf(employeeId));
            return "Employee deleted";
        }
    }
}