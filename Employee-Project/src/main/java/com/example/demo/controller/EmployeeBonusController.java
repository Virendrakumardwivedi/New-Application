package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tci")
@Slf4j
public class EmployeeBonusController {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    public EmployeeBonusController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/employee-bonus")
    public ResponseEntity<String> saveEmployeeBonus(@RequestBody Map<String, List<Employee>> request) {
    
    	log.info("inside employee post mapping");
        List<Employee> employees = request.get("employees");
        employeeRepository.saveAll(employees);
        return ResponseEntity.status(HttpStatus.CREATED).body("Employees Created successfully.");
        
    }

   
    @GetMapping("/employee-bonus")
    public ResponseEntity<List<Map<String, Object>>> getEmployeeBonus(
            @RequestParam @DateTimeFormat(pattern = "MMM-dd-yyyy") LocalDate date) {

        log.info("inside employee get mapping");
        List<Employee> employees = employeeRepository.findAll();

        Map<String, List<Employee>> groupedByCurrency = employees.stream()
                .filter(employee -> !employee.getJoiningDate().isAfter(date) && (employee.getExitDate() == null || !employee.getExitDate().isBefore(date)))
                .collect(Collectors.groupingBy(Employee::getCurrency));

        List<Map<String, Object>> response = new ArrayList<>();
        for (String currency : groupedByCurrency.keySet()) {
            Map<String, Object> currencyMap = new HashMap<>();
            currencyMap.put("currency", currency);

            List<Map<String, Object>> employeeList = groupedByCurrency.get(currency).stream()
                    .map(emp -> {
                        Map<String, Object> empMap = new HashMap<>();
                        empMap.put("empName", emp.getEmpName());
                        empMap.put("amount", emp.getAmount());
                        return empMap;
                    })
                    .collect(Collectors.toList());

            currencyMap.put("employees", employeeList);
            response.add(currencyMap);
        }

        return ResponseEntity.ok(response);
    }


}
