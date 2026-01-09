package br.com.marketplace.onlyfoils.repository.fab;

import br.com.marketplace.onlyfoils.model.fab.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FabCardRepository extends JpaRepository<FabCard, Long> {

    List<FabCard> findByCardType(FabCardType cardType);

    List<FabCard> findByFabClass(FabClass fabClass);

    List<FabCard> findByTalent(FabTalent talent);

    List<FabCard> findByPitch(Pitch pitch);

    List<FabCard> findByFabClassAndTalent(FabClass fabClass, FabTalent talent);

    List<FabCard> findByCardTypeAndFabClass(
            FabCardType cardType,
            FabClass fabClass
    );
}
