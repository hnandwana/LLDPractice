package com.lldpractice.designpatterns.structural.facade.subsystem;

import java.time.LocalDateTime;

public class InvoiceService {
    public String generateInvoice(String customerId, String productId, int quantity, double amount) {
        System.out.println("Generating invoice for customer: " + customerId);
        String invoiceId = "INV-" + LocalDateTime.now()+" "+customerId+" "+productId;
        System.out.println("Invoice ID: " + invoiceId);
        return invoiceId;
    }
}
