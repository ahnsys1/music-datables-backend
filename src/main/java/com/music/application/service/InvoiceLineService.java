package com.music.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.music.application.entity.InvoiceLine;
import com.music.application.repository.InvoiceLineRepository;

@Service
@Transactional
public class InvoiceLineService {

    @Autowired
    private InvoiceLineRepository invoiceLineRepository;

    public List<InvoiceLine> findAll() {
        return invoiceLineRepository.findAll();
    }

    public Optional<InvoiceLine> findById(Integer id) {
        return invoiceLineRepository.findById(id);
    }

    public InvoiceLine save(InvoiceLine invoiceLine) {
        return invoiceLineRepository.save(invoiceLine);
    }

    public void deleteById(Integer id) {
        invoiceLineRepository.deleteById(id);
    }
}
