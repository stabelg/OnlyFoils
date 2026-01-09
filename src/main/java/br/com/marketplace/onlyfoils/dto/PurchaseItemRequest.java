package br.com.marketplace.onlyfoils.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PurchaseItemRequest(

        @NotNull
        Long listingId,

        @Min(1)
        int quantity
) {}

