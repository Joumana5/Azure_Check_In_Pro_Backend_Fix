package com.example.tcu.checkinpro.Service;

import com.example.tcu.checkinpro.Entity.employeeEntity;
import com.example.tcu.checkinpro.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public CustomUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<employeeEntity> employee = employeeRepository.findByEmail(username);

        if (employee.isEmpty()) throw new UsernameNotFoundException(username);

        employeeEntity e = employee.get();

        return new User(e.getEmail(), e.getPassword(), new ArrayList<>());

    }

}