package br.com.marketplace.onlyfoils.model.fab;

import br.com.marketplace.onlyfoils.model.CardSet;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FAB_SET")
public class FabCardSet extends CardSet {
}
