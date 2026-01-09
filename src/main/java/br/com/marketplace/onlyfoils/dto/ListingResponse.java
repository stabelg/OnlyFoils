package br.com.marketplace.onlyfoils.dto;

import br.com.marketplace.onlyfoils.model.CardCondition;
import br.com.marketplace.onlyfoils.model.Language;
import br.com.marketplace.onlyfoils.model.Listing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingResponse{
    private Long id;
    private Long sellerId;
    private Long cardPrintId;
    private CardCondition condition;
    private Language language;
    private int quantity;
    private boolean signed;
    private boolean altered;
    private BigDecimal price;

    public static ListingResponse from(Listing listing) {
        return new ListingResponse(
                listing.getId(),
                listing.getSeller().getId(),
                listing.getCardPrint().getId(),
                listing.getCondition(),
                listing.getLanguage(),
                listing.getQuantity(),
                listing.isSigned(),
                listing.isAltered(),
                listing.getPrice()
        );
    }
}
