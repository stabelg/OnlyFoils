package br.com.marketplace.onlyfoils.dto;

import br.com.marketplace.onlyfoils.model.OrderStatus;

import java.math.BigDecimal;

public record PurchaseResponse(
        Long orderId,
        OrderStatus status,
        BigDecimal totalPrice
) {}
