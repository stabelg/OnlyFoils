package br.com.marketplace.onlyfoils.service;

import br.com.marketplace.onlyfoils.model.*;
import br.com.marketplace.onlyfoils.repository.OrderItemRepository;
import br.com.marketplace.onlyfoils.repository.OrderRepository;
import br.com.marketplace.onlyfoils.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ListingService listingService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private User buyer;
    private Order order;
    private Listing listing;

    @BeforeEach
    void setUp() {
        buyer = new User();
        buyer.setId(1L);

        order = new Order();
        order.setId(1L);
        order.setBuyer(buyer);
        order.setStatus(OrderStatus.CREATED);
        order.setTotalPrice(BigDecimal.ZERO);

        listing = new Listing();
        listing.setId(10L);
        listing.setPrice(BigDecimal.valueOf(100));
    }

    @Test
    void shouldCreateOrder() {

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Order result = orderService.createOrder(buyer);

        assertEquals(OrderStatus.CREATED, result.getStatus());
        assertEquals(BigDecimal.ZERO, result.getTotalPrice());
        assertEquals(buyer, result.getBuyer());

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void shouldAddItemAndReserveStock() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(listingService.reserveStock(10L, 2))
                .thenReturn(listing);

        Order result = orderService.addItem(1L, 10L, 2);

        assertEquals(BigDecimal.valueOf(200), result.getTotalPrice());

        verify(listingService).reserveStock(10L, 2);
        verify(orderItemRepository).save(any(OrderItem.class));
    }

    @Test
    void shouldFailWhenAddingItemToFinalizedOrder() {

        order.setStatus(OrderStatus.PAID);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        assertThrows(IllegalStateException.class, () ->
                orderService.addItem(1L, 10L, 1)
        );

        verifyNoInteractions(listingService);
    }


    @Test
    void shouldConfirmPaymentAndConfirmReservations() {

        OrderItem item = new OrderItem();
        item.setListing(listing);
        item.setQuantity(2);

        order.getItems().add(item);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        orderService.confirmPayment(1L);

        assertEquals(OrderStatus.PAID, order.getStatus());

        verify(listingService)
                .confirmReservation(10L, 2);
    }

    @Test
    void shouldFailConfirmPaymentIfNotCreated() {

        order.setStatus(OrderStatus.CANCELED);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        assertThrows(IllegalStateException.class, () ->
                orderService.confirmPayment(1L)
        );
    }

    @Test
    void shouldCancelOrderAndReleaseStock() {

        OrderItem item = new OrderItem();
        item.setListing(listing);
        item.setQuantity(1);

        order.getItems().add(item);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        orderService.cancelOrder(1L);

        assertEquals(OrderStatus.CANCELED, order.getStatus());

        verify(listingService)
                .releaseReservation(10L, 1);
    }

    @Test
    void shouldFinalizeOrder() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        Order result = orderService.finalizeOrder(1L);

        assertEquals(OrderStatus.PAID, result.getStatus());
    }
}


