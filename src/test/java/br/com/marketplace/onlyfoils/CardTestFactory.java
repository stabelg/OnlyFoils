package br.com.marketplace.onlyfoils;

import br.com.marketplace.onlyfoils.model.fab.FabCard;

public class CardTestFactory {

    public static FabCard emptyFabCard() {
        return new FabCard();
    }

    public static FabCard fabCardWithId() {
        FabCard card = new FabCard();
        card.setId(1L);
        return card;
    }
}

