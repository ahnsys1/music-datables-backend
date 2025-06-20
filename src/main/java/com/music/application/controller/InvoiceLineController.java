package com.music.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.music.application.dto.InvoiceLineDTO;
import com.music.application.mapper.InvoiceLineMapper;
import com.music.application.service.InvoiceLineService;

@RestController
@RequestMapping("/api/invoicelines")
public class InvoiceLineController {

    @Autowired
    private InvoiceLineMapper invoiceLineMapper;

    @Autowired
    private InvoiceLineService invoiceLineService;

    @GetMapping
    public List<InvoiceLineDTO> getAllInvoiceLines() {
        return invoiceLineService.findAll().stream().map(invoiceLineMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceLineDTO> getInvoiceLineById(@PathVariable Integer id) {
        return invoiceLineService.findById(id)
                .map(invoiceLineMapper::toDTO)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InvoiceLineDTO> createInvoiceLine(@RequestBody InvoiceLineDTO invoiceLineDTO) {
        var invoiceLine = invoiceLineMapper.toEntity(invoiceLineDTO);
        var saved = invoiceLineService.save(invoiceLine); // Remove extra arguments
        return ResponseEntity.ok(invoiceLineMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceLineDTO> updateInvoiceLine(@PathVariable Integer id, @RequestBody InvoiceLineDTO invoiceLineDTO) {
        var invoiceLine = invoiceLineMapper.toEntity(invoiceLineDTO);
        invoiceLine.setInvoiceLineId(id);
        var updated = invoiceLineService.save(invoiceLine); // Remove extra arguments
        return ResponseEntity.ok(invoiceLineMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoiceLine(@PathVariable Integer id) {
        invoiceLineService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
