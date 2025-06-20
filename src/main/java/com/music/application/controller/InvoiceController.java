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

import com.music.application.dto.InvoiceDTO;
import com.music.application.mapper.InvoiceMapper;
import com.music.application.service.InvoiceService;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @GetMapping
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceService.findAll().stream().map(invoiceMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable Integer id) {
        return invoiceService.findById(id)
                .map(invoiceMapper::toDTO)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InvoiceDTO> createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        var invoice = invoiceMapper.toEntity(invoiceDTO);
        var saved = invoiceService.save(invoice); // Remove extra argument
        return ResponseEntity.ok(invoiceMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable Integer id, @RequestBody InvoiceDTO invoiceDTO) {
        var invoice = invoiceMapper.toEntity(invoiceDTO);
        invoice.setInvoiceId(id);
        var updated = invoiceService.save(invoice); // Remove extra argument
        return ResponseEntity.ok(invoiceMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Integer id) {
        invoiceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
