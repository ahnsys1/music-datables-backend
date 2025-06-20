package com.music.application.controller;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.music.application.dto.EmployeeDTO;
import com.music.application.entity.Employee;
import com.music.application.service.EmployeeService;
import com.music.application.util.DateConverter;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeService employeeService;

    @Test
    void testCreateAndGetEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("IntegrationTest");
        employeeDTO.setLastName("Employee");
        employeeDTO.setTitle("QA");
        // Use DateConverter for dates (US notation yyyy-MM-dd)
        employeeDTO.setBirthDate(DateConverter.format(LocalDate.of(1990, 1, 10)));
        employeeDTO.setHireDate(DateConverter.format(LocalDate.of(2020, 1, 1)));
        employeeDTO.setAddress("123 Test St");
        employeeDTO.setCity("Testville");
        employeeDTO.setState("TS");
        employeeDTO.setCountry("Testland");
        employeeDTO.setPostalCode("12345");
        employeeDTO.setPhone("123-456-7890");
        employeeDTO.setFax("123-456-7891");
        employeeDTO.setEmail("integrationtest.employee@example.com");
        // employeeDTO.setReportsToId(null); // Optionally set if needed

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = objectMapper.writeValueAsString(employeeDTO); // Create
        String response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("IntegrationTest");

        // Find by name (using service for id)
        Employee employee = employeeService.findAll().stream()
                .filter(e -> "IntegrationTest".equals(e.getFirstName()))
                .findFirst().orElse(null);
        assertThat(employee).isNotNull();

        // Get by id
        mockMvc.perform(get("/api/employees/" + employee.getEmployeeId()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("IntegrationTest")));

        // Delete
        mockMvc.perform(delete("/api/employees/" + employee.getEmployeeId()))
                .andExpect(status().isNoContent());
    }
}
