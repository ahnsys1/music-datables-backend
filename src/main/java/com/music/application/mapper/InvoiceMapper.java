package com.music.application.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.music.application.dto.InvoiceDTO;
import com.music.application.entity.Invoice;
import com.music.application.util.DateConverter;

@Component
public class InvoiceMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DateConverter.DATE_FORMAT);

    public InvoiceDTO toDTO(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setInvoiceId(invoice.getInvoiceId());
        if (invoice.getInvoiceDate() != null) {
            dto.setInvoiceDate(DateConverter.format(invoice.getInvoiceDate()));
        }
        dto.setBillingAddress(invoice.getBillingAddress());
        dto.setBillingCity(invoice.getBillingCity());
        dto.setBillingState(invoice.getBillingState());
        dto.setBillingCountry(invoice.getBillingCountry());
        dto.setBillingPostalCode(invoice.getBillingPostalCode());
        dto.setTotal(invoice.getTotal());
        if (invoice.getCustomer() != null) {
            dto.setCustomerId(invoice.getCustomer().getCustomerId());
        }
        return dto;
    }

    public Invoice toEntity(InvoiceDTO dto) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(dto.getInvoiceId());
        invoice.setInvoiceDate(DateConverter.parse(dto.getInvoiceDate()));
        invoice.setBillingAddress(dto.getBillingAddress());
        invoice.setBillingCity(dto.getBillingCity());
        invoice.setBillingState(dto.getBillingState());
        invoice.setBillingCountry(dto.getBillingCountry());
        invoice.setBillingPostalCode(dto.getBillingPostalCode());
        invoice.setTotal(dto.getTotal());
        // Note: Customer needs to be set separately using CustomerService if needed
        return invoice;
    }
}
