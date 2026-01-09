package br.com.marketplace.onlyfoils.service.impl;

import br.com.marketplace.onlyfoils.model.*;
import br.com.marketplace.onlyfoils.repository.OrderItemRepository;
import br.com.marketplace.onlyfoils.repository.OrderRepository;
import br.com.marketplace.onlyfoils.service.ListingService;
import br.com.marketplace.onlyfoils.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ListingService listingService;

    @Override
    public Order createOrder(User buyer) {
        Order order = new Order();
        order.setBuyer(buyer);
        order.setStatus(OrderStatus.CREATED);
        order.setTotalPrice(BigDecimal.ZERO);
        return orderRepository.save(order);
    }

    @Override
    public Order addItem(Long orderId, Long listingId, int quantity) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot add items to finalized order");
        }

        // 🔒 reserva pessimista
        Listing listing = listingService.reserveStock(listingId, quantity);

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setListing(listing);
        item.setQuantity(quantity);
        item.setPriceAtPurchase(listing.getPrice());

        orderItemRepository.save(item);

        BigDecimal itemTotal =
                listing.getPrice().multiply(BigDecimal.valueOf(quantity));

        order.setTotalPrice(order.getTotalPrice().add(itemTotal));

        return order;
    }

    @Override
    public void confirmPayment(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("Order cannot be confirmed");
        }

        // reserva já foi abatida fisicamente
        for (OrderItem item : order.getItems()) {
            listingService.confirmReservation(
                    item.getListing().getId(),
                    item.getQuantity()
            );
        }

        order.setStatus(OrderStatus.PAID);
    }

    @Override
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELED) {
            return;
        }

        // devolve estoque
        for (OrderItem item : order.getItems()) {
            listingService.releaseReservation(
                    item.getListing().getId(),
                    item.getQuantity()
            );
        }

        order.setStatus(OrderStatus.CANCELED);
    }

    @Override
    public Order finalizeOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("Order cannot be finalized");
        }

        order.setStatus(OrderStatus.PAID);
        return order;
    }
}

