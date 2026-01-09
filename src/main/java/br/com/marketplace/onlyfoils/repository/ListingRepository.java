package br.com.marketplace.onlyfoils.repository;

import br.com.marketplace.onlyfoils.model.*;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from Listing l where l.id = :id")
    Optional<Listing> findByIdForUpdate(@Param("id") Long id);

    List<Listing> findByCardPrintCardAndQuantityGreaterThan(Card card, int quantity);

    List<Listing> findByCardPrint(CardPrint cardPrint);

    List<Listing> findByCardPrintId(Long cardPrintId);

    List<Listing> findBySeller(User seller);

    List<Listing> findByCondition(CardCondition condition);

    List<Listing> findByCardPrintIdAndCondition(
            Long cardPrintId,
            CardCondition condition
    );

    List<Listing> findByCardPrintIdOrderByPriceAsc(Long cardPrintId);

    List<Listing> findByCardPrintIdAndLanguageAndConditionOrderByPriceAsc(
            Long cardPrintId,
            Language language,
            CardCondition condition
    );


}
