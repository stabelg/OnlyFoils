package br.com.marketplace.onlyfoils.repository;

import br.com.marketplace.onlyfoils.model.Order;
import br.com.marketplace.onlyfoils.model.OrderStatus;
import br.com.marketplace.onlyfoils.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Compras de um usuário
    List<Order> findByBuyerOrderByIdDesc(User buyer);

    // Pedidos por status
    List<Order> findByStatus(OrderStatus status);


    // Pedidos de um comprador por status
    List<Order> findByBuyerAndStatusOrderByIdDesc(
            User buyer,
            OrderStatus status
    );
}
