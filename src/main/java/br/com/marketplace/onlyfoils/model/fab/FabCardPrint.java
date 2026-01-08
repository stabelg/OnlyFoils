package br.com.marketplace.onlyfoils.model.fab;

import br.com.marketplace.onlyfoils.model.CardPrint;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("FAB_PRINT")
public class FabCardPrint extends CardPrint {

    @Enumerated(EnumType.STRING)
    private FabRarity fabRarity;
}