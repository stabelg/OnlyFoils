package br.com.marketplace.onlyfoils.service.impl;

import br.com.marketplace.onlyfoils.dto.PurchaseItemRequest;
import br.com.marketplace.onlyfoils.dto.PurchaseRequest;
import br.com.marketplace.onlyfoils.model.*;
import br.com.marketplace.onlyfoils.repository.OrderItemRepository;
import br.com.marketplace.onlyfoils.repository.OrderRepository;
import br.com.marketplace.onlyfoils.repository.UserRepository;
import br.com.marketplace.onlyfoils.service.ListingService;
import br.com.marketplace.onlyfoils.service.PaymentService;
import br.com.marketplace.onlyfoils.service.PurchaseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ListingService listingService;
    private final PaymentService paymentService;

    public PurchaseServiceImpl(
            UserRepository userRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ListingService listingService,
            PaymentService paymentService
    ) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.listingService = listingService;
        this.paymentService = paymentService;
    }

    @Override
    public Order purchase(PurchaseRequest request) {

        if (request.items() == null || request.items().isEmpty()) {
            throw new IllegalArgumentException("Purchase must contain at least one item");
        }

        User buyer = userRepository.findById(request.buyerId())
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found"));

        Order order = new Order();
        order.setBuyer(buyer);
        order.setStatus(OrderStatus.CREATED);
        order.setTotalPrice(BigDecimal.ZERO);

        orderRepository.save(order);

        // 1️⃣ Reserva de estoque (LOCK + rollback automático)
        for (PurchaseItemRequest item : request.items()) {
            listingService.reserveStock(item.listingId(), item.quantity());
        }

        // 2️⃣ Criação dos itens
        for (PurchaseItemRequest item : request.items()) {

            Listing listing = listingService.getById(item.listingId());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setListing(listing);
            orderItem.setSeller(listing.getSeller());
            orderItem.setQuantity(item.quantity());
            orderItem.setPriceAtPurchase(listing.getPrice());

            orderItemRepository.save(orderItem);

            order.setTotalPrice(
                    order.getTotalPrice().add(
                            listing.getPrice()
                                    .multiply(BigDecimal.valueOf(item.quantity()))
                    )
            );
        }

        // 3️⃣ Pagamento pendente
        order.setStatus(OrderStatus.PAYMENT_PENDING);

        // 4️⃣ Pagamento assíncrono (fora da transação DB)
        paymentService.processPaymentAsync(order.getId());

        return order;
    }
}

