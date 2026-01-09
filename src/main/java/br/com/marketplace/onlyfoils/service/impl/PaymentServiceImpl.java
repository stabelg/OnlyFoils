package br.com.marketplace.onlyfoils.service.impl;

import br.com.marketplace.onlyfoils.service.OrderService;
import br.com.marketplace.onlyfoils.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderService orderService;

    @Async
    @Override
    public void processPaymentAsync(Long orderId) {

        try {
            // Simula gateway externo
            Thread.sleep(2000);

            orderService.confirmPayment(orderId);

        } catch (Exception e) {

            orderService.cancelOrder(orderId);
        }
    }
}


