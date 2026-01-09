package br.com.marketplace.onlyfoils.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Data
public abstract class CardPrint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "card_id", nullable = false)
    protected Card card;

    @ManyToOne(optional = false)
    @JoinColumn(name = "set_id", nullable = false)
    protected CardSet set;

    @Enumerated(EnumType.STRING)
    protected FoilType foilType;

    protected String collectorNumber;
}
