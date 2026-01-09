package br.com.marketplace.onlyfoils.service;

import br.com.marketplace.onlyfoils.dto.PurchaseItemRequest;
import br.com.marketplace.onlyfoils.dto.PurchaseRequest;
import br.com.marketplace.onlyfoils.model.*;
import br.com.marketplace.onlyfoils.repository.OrderItemRepository;
import br.com.marketplace.onlyfoils.repository.OrderRepository;
import br.com.marketplace.onlyfoils.repository.UserRepository;
import br.com.marketplace.onlyfoils.service.impl.PurchaseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ListingService listingService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    @Test
    void shouldPurchaseMultipleItemsSuccessfully() {
        User buyer = new User();
        buyer.setId(1L);

        Listing l1 = new Listing();
        l1.setId(10L);
        l1.setPrice(new BigDecimal("20.00"));

        Listing l2 = new Listing();
        l2.setId(20L);
        l2.setPrice(new BigDecimal("15.00"));

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(buyer));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(listingService.getById(10L)).thenReturn(l1);
        when(listingService.getById(20L)).thenReturn(l2);

        PurchaseRequest request = new PurchaseRequest(
                1L,
                List.of(
                        new PurchaseItemRequest(10L, 2),
                        new PurchaseItemRequest(20L, 1)
                )
        );

        Order order = purchaseService.purchase(request);

        assertThat(order.getStatus())
                .isEqualTo(OrderStatus.PAYMENT_PENDING);

        assertThat(order.getTotalPrice())
                .isEqualByComparingTo("55.00");

        verify(listingService).reserveStock(10L, 2);
        verify(listingService).reserveStock(20L, 1);
        verify(orderItemRepository, times(2)).save(any(OrderItem.class));
        verify(paymentService).processPaymentAsync(order.getId());
    }

    @Test
    void shouldFailWhenItemListIsEmpty() {
        PurchaseRequest request =
                new PurchaseRequest(1L, List.of());

        assertThatThrownBy(() ->
                purchaseService.purchase(request)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Purchase must contain at least one item");
    }
}

