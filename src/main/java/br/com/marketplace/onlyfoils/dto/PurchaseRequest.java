package br.com.marketplace.onlyfoils.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PurchaseRequest(

        @NotNull
        Long buyerId,

        @NotEmpty
        List<@Valid PurchaseItemRequest> items
) {}

