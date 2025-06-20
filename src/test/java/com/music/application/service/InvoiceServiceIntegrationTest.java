package com.music.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.music.application.entity.Customer;
import com.music.application.entity.Invoice;

@SpringBootTest
public class InvoiceServiceIntegrationTest {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private CustomerService customerService;

    private Invoice invoice;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer = customerService.save(customer);

        invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setInvoiceDate(java.time.LocalDate.now());
        invoice.setTotal(10.0);
        invoice = invoiceService.save(invoice);
    }

    @Test
    void testCreateAndFindInvoice() {
        Invoice found = invoiceService.findById(invoice.getInvoiceId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getTotal()).isEqualTo(10.0);
    }

    @Test
    void testUpdateInvoice() {
        invoice.setTotal(20.0);
        Invoice updated = invoiceService.save(invoice);
        assertThat(updated.getTotal()).isEqualTo(20.0);
    }

    @Test
    void testDeleteInvoice() {
        Integer id = invoice.getInvoiceId();
        invoiceService.deleteById(id);
        assertThat(invoiceService.findById(id)).isEmpty();
    }

    @Test
    void testCreateInvoice() {
        Invoice newInvoice = new Invoice();
        newInvoice.setCustomer(invoice.getCustomer());
        newInvoice.setInvoiceDate(java.time.LocalDate.now());
        newInvoice.setTotal(99.99);
        Invoice saved = invoiceService.save(newInvoice);
        assertThat(saved.getInvoiceId()).isNotNull();
        assertThat(saved.getTotal()).isEqualTo(99.99);
    }

    @Test
    void testReadInvoice() {
        Invoice found = invoiceService.findById(invoice.getInvoiceId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getTotal()).isEqualTo(10.0);
    }

    @Test
    void testUpdateInvoiceCRUD() {
        invoice.setTotal(123.45);
        Invoice updated = invoiceService.save(invoice);
        assertThat(updated.getTotal()).isEqualTo(123.45);
    }

    @Test
    void testDeleteInvoiceCRUD() {
        Invoice newInvoice = new Invoice();
        newInvoice.setCustomer(invoice.getCustomer());
        newInvoice.setInvoiceDate(java.time.LocalDate.now());
        newInvoice.setTotal(55.55);
        Invoice saved = invoiceService.save(newInvoice);
        Integer id = saved.getInvoiceId();
        invoiceService.deleteById(id);
        assertThat(invoiceService.findById(id)).isEmpty();
    }
}
