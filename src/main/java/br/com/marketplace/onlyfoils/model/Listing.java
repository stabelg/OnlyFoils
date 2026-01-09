package br.com.marketplace.onlyfoils.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Table(
        indexes = {
                @Index(name = "idx_listing_card_print", columnList = "card_print_id"),
                @Index(name = "idx_listing_price", columnList = "price"),
                @Index(name = "idx_listing_seller", columnList = "seller_id")
        }
)
@Entity
@Data
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne(optional = false)
    @JoinColumn(name = "card_print_id")
    private CardPrint cardPrint;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardCondition condition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int reservedQuantity;

    @Column(nullable = false)
    private boolean signed;

    @Column(nullable = false)
    private boolean altered;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}

