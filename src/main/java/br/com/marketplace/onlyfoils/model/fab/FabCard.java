package br.com.marketplace.onlyfoils.model.fab;

import br.com.marketplace.onlyfoils.model.Card;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("FAB_CARD")
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