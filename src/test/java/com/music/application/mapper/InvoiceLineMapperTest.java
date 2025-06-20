package com.music.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.music.application.dto.InvoiceLineDTO;
import com.music.application.entity.InvoiceLine;

public class InvoiceLineMapperTest {

    private final InvoiceLineMapper mapper = new InvoiceLineMapper();

    @Test
    void testToDTOAndToEntity() {
        InvoiceLine invoiceLine = new InvoiceLine();
        invoiceLine.setInvoiceLineId(1);
        invoiceLine.setUnitPrice(9.99);
        invoiceLine.setQuantity(2);

        InvoiceLineDTO dto = mapper.toDTO(invoiceLine);
        assertEquals(1, dto.getInvoiceLineId());
        assertEquals(9.99, dto.getUnitPrice());
        assertEquals(2, dto.getQuantity());

        InvoiceLine entity = mapper.toEntity(dto);
        assertEquals(1, entity.getInvoiceLineId());
        assertEquals(9.99, entity.getUnitPrice());
        assertEquals(2, entity.getQuantity());
    }
}
