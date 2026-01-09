package br.com.marketplace.onlyfoils.model.fab;

import br.com.marketplace.onlyfoils.model.CardPrint;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("FAB_PRINT")
@Setter
@Getter
public class FabCardPrint extends CardPrint {

    @Enumerated(EnumType.STRING)
    private FabRarity fabRarity;
}