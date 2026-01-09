package br.com.marketplace.onlyfoils.repository;

import br.com.marketplace.onlyfoils.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByNameIgnoreCase(String name);

    List<Card> findByNameContainingIgnoreCase(String name);

}