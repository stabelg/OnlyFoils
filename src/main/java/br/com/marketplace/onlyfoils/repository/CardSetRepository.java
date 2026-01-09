package br.com.marketplace.onlyfoils.repository;


import br.com.marketplace.onlyfoils.model.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardSetRepository extends JpaRepository<CardSet, Long> {

    Optional<CardSet> findByCode(String code);

    List<CardSet> findByReleaseYear(int releaseYear);

    List<CardSet> findByNameContainingIgnoreCase(String name);
}
