package com.music.application.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.music.application.dto.CustomerDTO;
import com.music.application.entity.Customer;
import com.music.application.service.EmployeeService;

@Component
public class CustomerMapper {

    @Autowired
    private EmployeeService employeeService;

    public CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setCompany(customer.getCompany());
        dto.setAddress(customer.getAddress());
        dto.setCity(customer.getCity());
        dto.setState(customer.getState());
        dto.setCountry(customer.getCountry());
        dto.setPostalCode(customer.getPostalCode());
        dto.setPhone(customer.getPhone());
        dto.setFax(customer.getFax());
        dto.setEmail(customer.getEmail());
        if (customer.getSupportRep() != null) {
            dto.setSupportRepId(customer.getSupportRep().getEmployeeId());
        }
        return dto;
    }

    public Customer toEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setCustomerId(dto.getCustomerId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setCompany(dto.getCompany());
        customer.setAddress(dto.getAddress());
        customer.setCity(dto.getCity());
        customer.setState(dto.getState());
        customer.setCountry(dto.getCountry());
        customer.setPostalCode(dto.getPostalCode());
        customer.setPhone(dto.getPhone());
        customer.setFax(dto.getFax());
        customer.setEmail(dto.getEmail());
        if (dto.getSupportRepId() != null) {
            customer.setSupportRep(employeeService.findById(dto.getSupportRepId()).get());
        }
        return customer;
    }
}
