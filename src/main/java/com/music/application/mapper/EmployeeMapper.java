package com.music.application.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.music.application.dto.EmployeeDTO;
import com.music.application.entity.Employee;
import com.music.application.service.CustomerService;
import com.music.application.service.EmployeeService;

@Component
public class EmployeeMapper {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-mm-dd");

    public EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setTitle(employee.getTitle());
        if (employee.getReportsTo() != null) {
            dto.setReportsToId(employee.getReportsTo().getEmployeeId());
        }
        dto.setBirthDate(employee.getBirthDate() != null ? employee.getBirthDate().format(FORMATTER) : null);
        dto.setHireDate(employee.getHireDate() != null ? employee.getHireDate().format(FORMATTER) : null);
        dto.setAddress(employee.getAddress());
        dto.setCity(employee.getCity());
        dto.setState(employee.getState());
        dto.setCountry(employee.getCountry());
        dto.setPostalCode(employee.getPostalCode());
        dto.setPhone(employee.getPhone());
        dto.setFax(employee.getFax());
        dto.setEmail(employee.getEmail());
        return dto;
    }

    public Employee toEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setEmployeeId(dto.getEmployeeId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setTitle(dto.getTitle());
        employee.setBirthDate(dto.getBirthDate() != null && !dto.getBirthDate().isEmpty()
                ? LocalDate.parse(dto.getBirthDate(), FORMATTER)
                : null);
        employee.setHireDate(dto.getHireDate() != null && !dto.getHireDate().isEmpty()
                ? LocalDate.parse(dto.getHireDate(), FORMATTER) : null);
        employee.setAddress(dto.getAddress());
        employee.setCity(dto.getCity());
        employee.setState(dto.getState());
        employee.setCountry(dto.getCountry());
        employee.setPostalCode(dto.getPostalCode());
        employee.setPhone(dto.getPhone());
        employee.setFax(dto.getFax());
        employee.setEmail(dto.getEmail());
        if (dto.getCustomerIds() != null && !dto.getCustomerIds().isEmpty()) {
            employee.setCustomers(dto.getCustomerIds().stream()
                    .map(customerId -> customerService.findById(customerId).get())
                    .toList());

        }
        if (dto.getReportsToId() != null) {
            employee.setReportsTo(employeeService.findById(dto.getReportsToId()).get());
        }
        return employee;

    }

}
