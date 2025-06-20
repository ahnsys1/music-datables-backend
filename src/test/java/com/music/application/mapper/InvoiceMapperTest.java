package com.music.application.mapper;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.music.application.dto.InvoiceDTO;
import com.music.application.entity.Invoice;

public class InvoiceMapperTest {

    private final InvoiceMapper mapper = new InvoiceMapper();

    @Test
    void testToDTOAndToEntityDateConversion() {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setInvoiceDate(LocalDate.of(2025, 6, 19));

        InvoiceDTO dto = mapper.toDTO(invoice);
        assertEquals("19/06/2025", dto.getInvoiceDate());

        Invoice entity = mapper.toEntity(dto);
        assertEquals(LocalDate.of(2025, 6, 19), entity.getInvoiceDate());
    }
}
