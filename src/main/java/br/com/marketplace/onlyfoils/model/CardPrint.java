package br.com.marketplace.onlyfoils.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public abstract class CardPrint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    protected Card card;

    @ManyToOne
    protected CardSet set;

    @Enumerated(EnumType.STRING)
    protected FoilType foilType;

    protected String collectorNumber;
}
