package br.com.marketplace.onlyfoils.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    @Column(precision = 3, scale = 2)
    private BigDecimal reputation;

    private boolean buyer;
    private boolean seller;
}
