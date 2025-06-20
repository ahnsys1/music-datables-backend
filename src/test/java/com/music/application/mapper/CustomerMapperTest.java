package com.music.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.music.application.dto.CustomerDTO;
import com.music.application.entity.Customer;

public class CustomerMapperTest {

    private final CustomerMapper mapper = new CustomerMapper();

    @Test
    void testToDTOAndToEntity() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("Jane");
        customer.setLastName("Smith");
        customer.setEmail("jane.smith@example.com");

        CustomerDTO dto = mapper.toDTO(customer);
        assertEquals(1, dto.getCustomerId());
        assertEquals("Jane", dto.getFirstName());
        assertEquals("Smith", dto.getLastName());
        assertEquals("jane.smith@example.com", dto.getEmail());

        Customer entity = mapper.toEntity(dto);
        assertEquals(1, entity.getCustomerId());
        assertEquals("Jane", entity.getFirstName());
        assertEquals("Smith", entity.getLastName());
        assertEquals("jane.smith@example.com", entity.getEmail());
    }
}
