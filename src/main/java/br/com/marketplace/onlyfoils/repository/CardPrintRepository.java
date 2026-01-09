package br.com.marketplace.onlyfoils.repository;

import br.com.marketplace.onlyfoils.model.Card;
import br.com.marketplace.onlyfoils.model.CardPrint;
import br.com.marketplace.onlyfoils.model.CardSet;
import br.com.marketplace.onlyfoils.model.FoilType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardPrintRepository extends JpaRepository<CardPrint, Long> {

    List<CardPrint> findByCard(Card card);

    List<CardPrint> findBySet(CardSet set);

    List<CardPrint> findByFoilType(FoilType foilType);

    List<CardPrint> findByCardAndSet(Card card, CardSet set);

    List<CardPrint> findBySetAndFoilType(CardSet set, FoilType foilType);

    List<CardPrint> findByCardAndFoilType(Card card, FoilType foilType);

    List<CardPrint> findByCollectorNumber(String collectorNumber);

}
