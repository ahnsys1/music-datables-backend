package com.music.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.music.application.entity.Employee;

@SpringBootTest
public class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setFirstName("Jane");
        employee.setLastName("Smith");
        employee.setTitle("Manager");
        employee = employeeService.save(employee);
    }

    @Test
    void testCreateAndFindEmployee() {
        Employee found = employeeService.findById(employee.getEmployeeId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("Jane");
    }

    @Test
    void testCreateEmployeeCRUD() {
        employee = new Employee();
        employee.setFirstName("CRUD");
        employee.setLastName("Employee");
        employee.setTitle("Tester");
        Employee saved = employeeService.save(employee);
        assertThat(saved.getEmployeeId()).isNotNull();
        assertThat(saved.getFirstName()).isEqualTo("CRUD");
    }

    @Test
    void testReadEmployeeCRUD() {
        employee = new Employee();
        employee.setFirstName("Read");
        employee.setLastName("Employee");
        employee.setTitle("Reader");
        Employee saved = employeeService.save(employee);
        Employee found = employeeService.findById(saved.getEmployeeId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("Reader");
    }

    @Test
    void testUpdateEmployee() {
        employee.setTitle("Director");
        Employee updated = employeeService.save(employee);
        assertThat(updated.getTitle()).isEqualTo("Director");
    }

    @Test
    void testUpdateEmployeeCRUD() {
        employee = new Employee();
        employee.setFirstName("To");
        employee.setLastName("Update");
        employee.setTitle("Updater");
        Employee saved = employeeService.save(employee);
        saved.setTitle("Updated Title");
        Employee updated = employeeService.save(saved);
        assertThat(updated.getTitle()).isEqualTo("Updated Title");
    }

    @Test
    void testDeleteEmployee() {
        Integer id = employee.getEmployeeId();
        employeeService.deleteById(id);
        assertThat(employeeService.findById(id)).isEmpty();
    }

    @Test
    void testDeleteEmployeeCRUD() {
        employee = new Employee();
        employee.setFirstName("To");
        employee.setLastName("Delete");
        employee.setTitle("Deleter");
        Employee saved = employeeService.save(employee);
        Integer id = saved.getEmployeeId();
        employeeService.deleteById(id);
        assertThat(employeeService.findById(id)).isEmpty();
    }
}
