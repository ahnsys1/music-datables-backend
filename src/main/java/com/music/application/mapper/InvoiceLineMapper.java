package com.music.application.mapper;

import org.springframework.stereotype.Component;

import com.music.application.dto.InvoiceLineDTO;
import com.music.application.entity.InvoiceLine;

@Component
public class InvoiceLineMapper {

    public InvoiceLineDTO toDTO(InvoiceLine invoiceLine) {
        InvoiceLineDTO dto = new InvoiceLineDTO();
        dto.setInvoiceLineId(invoiceLine.getInvoiceLineId());
        dto.setUnitPrice(invoiceLine.getUnitPrice());
        dto.setQuantity(invoiceLine.getQuantity());
        if (invoiceLine.getInvoice() != null) {
            dto.setInvoiceId(invoiceLine.getInvoice().getInvoiceId());
        }
        if (invoiceLine.getTrack() != null) {
            dto.setTrackId(invoiceLine.getTrack().getTrackId());
        }
        return dto;
    }

    public InvoiceLine toEntity(InvoiceLineDTO dto) {
        InvoiceLine invoiceLine = new InvoiceLine();
        invoiceLine.setInvoiceLineId(dto.getInvoiceLineId());
        invoiceLine.setUnitPrice(dto.getUnitPrice());
        invoiceLine.setQuantity(dto.getQuantity());
        // Note: Invoice and Track needs to be set separately using InvoiceService/TrackService if needed
        return invoiceLine;
    }
}
