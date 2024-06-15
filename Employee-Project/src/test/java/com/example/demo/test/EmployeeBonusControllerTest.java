package com.example.demo.test;


import com.example.demo.controller.EmployeeBonusController;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) // Use SpringRunner for JUnit 4 or @ExtendWith for JUnit 5
@WebMvcTest(EmployeeBonusController.class)
@AutoConfigureMockMvc
public class EmployeeBonusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeBonusController employeeBonusController;

    
    @Test
    public void saveEmployeeBonus_ValidInput_ShouldReturnCreated() throws Exception {
        Employee employee = new Employee("John Doe", "IT", 1000, "USD", LocalDate.of(2024, 6, 15), null);
        List<Employee> employees = Collections.singletonList(employee);

        when(employeeRepository.saveAll(any())).thenReturn(employees);

        mockMvc.perform(post("/tci/employee-bonus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("employees", employees))))
                .andExpect(status().isCreated())
                .andExpect(content().string("Employees Created successfully."));
    }


    
    @Test
    public void getEmployeeBonus_ValidDate_ShouldReturnFilteredEmployees() throws Exception {
        // Setup mock data
        Employee employee1 = new Employee("bob", "IT", 2800, "GBP", LocalDate.of(2024, 6, 1), null);
        Employee employee2 = new Employee("sam", "HR", 2500, "USD", LocalDate.of(2024, 6, 15), null);
        Employee employee3 = new Employee("susan", "Admin", 700, "USD", LocalDate.of(2024, 6, 15), null);
        Employee employee4 = new Employee("raj singh", "Finance", 5000, "INR", LocalDate.of(2024, 6, 1), null);
        Employee employee5 = new Employee("pratap m", "Marketing", 3000, "INR", LocalDate.of(2024, 6, 1), null);

        List<Employee> employees = Arrays.asList(employee1, employee2, employee3, employee4, employee5);

        // Mock repository behavior
        when(employeeRepository.findAll()).thenReturn(employees);

        // Perform GET request to controller method
        mockMvc.perform(get("/tci/employee-bonus")
                .param("date", "Jun-15-2024")) 
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3)) 
                .andExpect(jsonPath("$[0].currency").value("GBP"))
                .andExpect(jsonPath("$[0].employees.length()").value(1)) 
                .andExpect(jsonPath("$[0].employees[0].empName").value("bob"))
                .andExpect(jsonPath("$[0].employees[0].amount").value(2800.0))
                .andExpect(jsonPath("$[1].currency").value("USD"))
                .andExpect(jsonPath("$[1].employees.length()").value(2)) 
                .andExpect(jsonPath("$[1].employees[0].empName").value("sam"))
                .andExpect(jsonPath("$[1].employees[0].amount").value(2500.0))
                .andExpect(jsonPath("$[1].employees[1].empName").value("susan"))
                .andExpect(jsonPath("$[1].employees[1].amount").value(700.0))
                .andExpect(jsonPath("$[2].currency").value("INR"))
                .andExpect(jsonPath("$[2].employees.length()").value(2)) 
                .andExpect(jsonPath("$[2].employees[0].empName").value("raj singh"))
                .andExpect(jsonPath("$[2].employees[0].amount").value(5000.0))
                .andExpect(jsonPath("$[2].employees[1].empName").value("pratap m"))
                .andExpect(jsonPath("$[2].employees[1].amount").value(3000.0));
    }









}
