package br.com.marketplace.onlyfoils.repository;

import br.com.marketplace.onlyfoils.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByNameIgnoreCase(String name);

    List<Card> findByNameContainingIgnoreCase(String name);

    List<Card> findBySetCode(String setCode);

}