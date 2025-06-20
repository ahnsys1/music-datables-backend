package com.music.application.mapper;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.music.application.dto.EmployeeDTO;
import com.music.application.entity.Employee;

public class EmployeeMapperTest {

    private final EmployeeMapper mapper = new EmployeeMapper();

    @Test
    void testToDTOAndToEntityDateConversion() {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setBirthDate(LocalDate.of(1990, 6, 19));
        employee.setHireDate(LocalDate.of(2020, 1, 1));

        EmployeeDTO dto = mapper.toDTO(employee);
        assertEquals("1990-06-19", dto.getBirthDate());
        assertEquals("2020-01-01", dto.getHireDate());

        Employee entity = mapper.toEntity(dto);
        assertEquals(LocalDate.of(1990, 6, 19), entity.getBirthDate());
        assertEquals(LocalDate.of(2020, 1, 1), entity.getHireDate());
    }

    @Test
    void testEmployeeDateMapping() {
        // Correct way to set a date
        LocalDate expectedDate = LocalDate.of(2023, 6, 20); // yyyy-MM-dd

        Employee employee = new Employee();
        employee.setHireDate(expectedDate);

        assertEquals(expectedDate, employee.getHireDate());
    }
}
