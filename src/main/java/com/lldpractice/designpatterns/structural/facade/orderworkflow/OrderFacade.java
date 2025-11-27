package com.lldpractice.designpatterns.structural.facade.orderworkflow;

import com.lldpractice.designpatterns.structural.facade.subsystem.*;

public class OrderFacade {

    private final EmailService emailService;
    private final InvoiceService invoiceService;
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final ShippingService shippingService;

    public OrderFacade() {
        this.emailService = new EmailService();
        this.invoiceService = new InvoiceService();
        this.paymentService = new PaymentService();
        this.inventoryService = new InventoryService();
        this.shippingService = new ShippingService();
    }

    public boolean placeOrder(String customerId, String productId, int quantity, double amount, String address) {
        boolean inventoryAvailable = inventoryService.checkStock(productId, quantity);
        if (!inventoryAvailable) {
            emailService.sendCancellationEmail(customerId, "Out of stock");
            return false;
        }
        inventoryService.reserveStock(productId, quantity);
        boolean paymentSuccess = paymentService.processPayment(customerId, amount);
        if (!paymentSuccess) {
            emailService.sendCancellationEmail(customerId, "Payment failed");
            return false;
        }
        inventoryService.updateStock(productId, quantity);
        String trackingNumber = shippingService.scheduleShipping(productId, address, quantity);
        String invoiceId = invoiceService.generateInvoice(customerId, productId, quantity, amount);
        emailService.sendConfirmationEmail(customerId, invoiceId);
        System.out.println("Order placed successfully! Tracking: " + trackingNumber);
        return true;

    }
}
