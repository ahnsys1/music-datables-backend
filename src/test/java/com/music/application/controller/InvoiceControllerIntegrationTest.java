package com.music.application.controller;

import java.text.SimpleDateFormat;

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
import com.music.application.dto.InvoiceDTO;
import com.music.application.entity.Customer;
import com.music.application.entity.Invoice;
import com.music.application.service.CustomerService;
import com.music.application.service.InvoiceService;
import com.music.application.util.DateConverter;

@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private CustomerService customerService;

    @Test
    void testCreateAndGetInvoice() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat(DateConverter.DATE_FORMAT)); // Use DateConverter's pattern
        // Create customer
        Customer customer = new Customer();
        customer.setFirstName("IntegrationTest");
        customer.setLastName("InvoiceCustomer");
        customer.setEmail("integration.invoice@example.com");
        customer = customerService.save(customer);
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setCustomerId(customer.getCustomerId());
        invoiceDTO.setInvoiceDate(DateConverter.format(DateConverter.parse("2025-05-13"))); // Use DateConverter for date string
        invoiceDTO.setTotal(100.0);
        String json = objectMapper.writeValueAsString(invoiceDTO);
        // Create
        String response = mockMvc.perform(post("/api/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("100.0");

        // Find by total (using service for id)
        Invoice invoice = invoiceService.findAll().stream()
                .filter(i -> i.getTotal() == 100.0)
                .findFirst().orElse(null);
        assertThat(invoice).isNotNull();

        // Get by id
        mockMvc.perform(get("/api/invoices/" + invoice.getInvoiceId()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("100.0")));

        // Delete
        mockMvc.perform(delete("/api/invoices/" + invoice.getInvoiceId()))
                .andExpect(status().isNoContent());
        customerService.deleteById(customer.getCustomerId());
    }
}
