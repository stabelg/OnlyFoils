package br.com.marketplace.onlyfoils.repository;

import br.com.marketplace.onlyfoils.model.Order;
import br.com.marketplace.onlyfoils.model.OrderStatus;
import br.com.marketplace.onlyfoils.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindOrdersByBuyer() {
        User buyer = userRepository.findByUsername("alice").orElseThrow();

        List<Order> orders = orderRepository.findByBuyerOrderByIdDesc(buyer);

        assertThat(orders).hasSize(1);
        assertThat(orders.getFirst().getStatus()).isEqualTo(OrderStatus.PAID);
    }
}

