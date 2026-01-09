package br.com.marketplace.onlyfoils.service;

import br.com.marketplace.onlyfoils.dto.CreateListingRequest;
import br.com.marketplace.onlyfoils.dto.ListingResponse;
import br.com.marketplace.onlyfoils.model.Card;
import br.com.marketplace.onlyfoils.model.Listing;

import java.util.List;

public interface ListingService {

    Listing create(CreateListingRequest request);

    List<Listing> findAvailableListingsByCard(Card card);

    Listing getById(Long id);

    Listing reserveStock(Long listingId, int quantity);

    void confirmReservation(Long listingId, int quantity);

    void releaseReservation(Long listingId, int quantity);
    List<ListingResponse> findByCardPrintOrderedByPrice(Long cardPrintId);
}

