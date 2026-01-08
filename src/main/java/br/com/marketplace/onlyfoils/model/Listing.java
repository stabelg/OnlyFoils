package br.com.marketplace.onlyfoils.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User seller;

    @ManyToOne
    private CardPrint cardPrint;

    @Enumerated(EnumType.STRING)
    private CardCondition condition;

    @Enumerated(EnumType.STRING)
    private Language language;

    private boolean signed;
    private boolean altered;

    private int quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}

