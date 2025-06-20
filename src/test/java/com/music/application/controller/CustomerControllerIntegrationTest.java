package com.music.application.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.music.application.dto.CustomerDTO;
import com.music.application.entity.Customer;
import com.music.application.service.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerService customerService;

    @Test
    void testCreateAndGetCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("IntegrationTest");
        customerDTO.setLastName("Customer");
        customerDTO.setCompany("IntegrationTest Inc.");
        customerDTO.setAddress("456 Customer Rd");
        customerDTO.setCity("Customerville");
        customerDTO.setState("CS");
        customerDTO.setCountry("Customerland");
        customerDTO.setPostalCode("54321");
        customerDTO.setPhone("987-654-3210");
        customerDTO.setFax("987-654-3211");
        customerDTO.setEmail("integrationtest@example.com");
        // customerDTO.setSupportRepId(null); // Optionally set if needed

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = objectMapper.writeValueAsString(customerDTO); // Create
        // Create
        String response = mockMvc.perform(post("/api/customers")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("IntegrationTest");

        // Find by email (using service for id)
        Customer customer = customerService.findAll().stream()
                .filter(c -> "integrationtest@example.com".equals(c.getEmail()))
                .findFirst().orElse(null);
        assertThat(customer).isNotNull();

        // Get by id
        mockMvc.perform(get("/api/customers/" + customer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("IntegrationTest")));

        // Delete
        mockMvc.perform(delete("/api/customers/" + customer.getCustomerId()))
                .andExpect(status().isNoContent());
    }
}
