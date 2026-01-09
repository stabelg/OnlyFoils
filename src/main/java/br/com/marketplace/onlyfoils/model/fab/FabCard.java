package br.com.marketplace.onlyfoils.model.fab;

import br.com.marketplace.onlyfoils.model.Card;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("FAB_CARD")
@Getter
@Setter
public class FabCard extends Card {

    @Enumerated(EnumType.STRING)
    private FabCardType cardType;

    @Enumerated(EnumType.STRING)
    private FabClass fabClass;

    @Enumerated(EnumType.STRING)
    private FabTalent talent;

    @Enumerated(EnumType.STRING)
    private Pitch pitch;
}