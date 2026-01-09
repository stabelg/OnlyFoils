package br.com.marketplace.onlyfoils.service;

import br.com.marketplace.onlyfoils.model.Order;
import br.com.marketplace.onlyfoils.model.User;

public interface OrderService {

    Order createOrder(User buyer);

    Order addItem(
            Long orderId,
            Long listingId,
            int quantity
    );

    void confirmPayment(Long orderId);

    void cancelOrder(Long orderId);

    Order finalizeOrder(Long orderId);
}

