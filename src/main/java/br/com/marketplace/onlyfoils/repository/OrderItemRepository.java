package br.com.marketplace.onlyfoils.repository;

import br.com.marketplace.onlyfoils.model.Listing;
import br.com.marketplace.onlyfoils.model.Order;
import br.com.marketplace.onlyfoils.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Itens de um pedido
    List<OrderItem> findByOrder(Order order);

    // Itens de um listing específico (histórico)
    List<OrderItem> findByListing(Listing listing);

    // Itens de vários pedidos (ex: batch)
    List<OrderItem> findByOrderIdIn(List<Long> orderIds);
}

