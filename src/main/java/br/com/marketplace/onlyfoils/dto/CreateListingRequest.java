package br.com.marketplace.onlyfoils.dto;

import br.com.marketplace.onlyfoils.model.CardCondition;
import br.com.marketplace.onlyfoils.model.Language;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateListingRequest(

        @NotNull
        Long sellerId,

        @NotNull
        Long cardPrintId,

        @NotNull
        CardCondition condition,

        @NotNull
        Language language,

        @Min(1)
        int quantity,

        boolean signed,

        boolean altered,

        @NotNull
        @DecimalMin(value = "0.01", inclusive = true)
        BigDecimal price
) {
}
