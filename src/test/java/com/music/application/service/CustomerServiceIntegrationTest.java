package com.music.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.music.application.entity.Customer;

@SpringBootTest
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void testCreateAndFindCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Test");
        customer.setLastName("Customer");
        customer.setEmail("test@example.com");
        Customer saved = customerService.save(customer);
        assertThat(saved.getCustomerId()).isNotNull();
        Customer found = customerService.findById(saved.getCustomerId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("Test");
        customerService.deleteById(saved.getCustomerId());
    }

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("CRUD");
        customer.setLastName("Customer");
        customer.setEmail("crud@example.com");
        Customer saved = customerService.save(customer);
        assertThat(saved.getCustomerId()).isNotNull();
        assertThat(saved.getFirstName()).isEqualTo("CRUD");
    }

    @Test
    void testReadCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Read");
        customer.setLastName("Customer");
        customer.setEmail("read@example.com");
        Customer saved = customerService.save(customer);
        Customer found = customerService.findById(saved.getCustomerId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("read@example.com");
    }

    @Test
    void testUpdateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("To");
        customer.setLastName("Update");
        customer.setEmail("update@example.com");
        Customer saved = customerService.save(customer);
        saved.setEmail("updated@example.com");
        Customer updated = customerService.save(saved);
        assertThat(updated.getEmail()).isEqualTo("updated@example.com");
    }

    @Test
    void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("To");
        customer.setLastName("Delete");
        customer.setEmail("delete@example.com");
        Customer saved = customerService.save(customer);
        Integer id = saved.getCustomerId();
        customerService.deleteById(id);
        assertThat(customerService.findById(id)).isEmpty();
    }
}
