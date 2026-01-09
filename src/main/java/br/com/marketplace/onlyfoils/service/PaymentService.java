package br.com.marketplace.onlyfoils.service;

public interface PaymentService {

    void processPaymentAsync(Long orderId);
}

