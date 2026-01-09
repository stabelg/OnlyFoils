package br.com.marketplace.onlyfoils.repository;

import br.com.marketplace.onlyfoils.model.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    void shouldFindItemsByOrder() {
        List<OrderItem> items = orderItemRepository.findByOrderIdIn(List.of(1L));

        assertThat(items).hasSize(2);
    }
}
