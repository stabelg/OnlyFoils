package br.com.marketplace.onlyfoils.service.impl;

import br.com.marketplace.onlyfoils.dto.CreateListingRequest;
import br.com.marketplace.onlyfoils.dto.ListingResponse;
import br.com.marketplace.onlyfoils.model.Card;
import br.com.marketplace.onlyfoils.model.CardPrint;
import br.com.marketplace.onlyfoils.model.Listing;
import br.com.marketplace.onlyfoils.model.User;
import br.com.marketplace.onlyfoils.repository.ListingRepository;
import br.com.marketplace.onlyfoils.service.CardPrintService;
import br.com.marketplace.onlyfoils.service.ListingService;
import br.com.marketplace.onlyfoils.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final UserService userService;
    private final CardPrintService cardPrintService;


    @Override
    public Listing create(CreateListingRequest request) {

        User seller = userService.getEntityById(request.sellerId());

        CardPrint cardPrint = cardPrintService.getById(request.cardPrintId());

        if (request.price().signum() <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        Listing listing = new Listing();
        listing.setSeller(seller);
        listing.setCardPrint(cardPrint);
        listing.setCondition(request.condition());
        listing.setLanguage(request.language());
        listing.setQuantity(request.quantity());
        listing.setReservedQuantity(0);
        listing.setSigned(request.signed());
        listing.setAltered(request.altered());
        listing.setPrice(request.price());

        return listingRepository.save(listing);
    }

    @Override
    @Transactional
    public List<Listing> findAvailableListingsByCard(Card card) {
        return listingRepository
                .findByCardPrintCardAndQuantityGreaterThan(card, 0);
    }

    @Override
    @Transactional
    public Listing getById(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found"));
    }

    @Override
    public Listing reserveStock(Long listingId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }

        Listing listing = listingRepository
                .findByIdForUpdate(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found"));

        if (listing.getQuantity() < quantity) {
            throw new IllegalStateException(
                    "Not enough stock for listing " + listing.getId()
            );
        }

        // 🔒 reserva física (estoque já sai)
        listing.setQuantity(listing.getQuantity() - quantity);

        return listing;
    }

    @Override
    public void confirmReservation(Long listingId, int quantity) {
        // Estoque já foi abatido na reserva
        // Método existe apenas para semântica do fluxo
    }

    @Override
    public void releaseReservation(Long listingId, int quantity) {

        Listing listing = listingRepository
                .findByIdForUpdate(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found"));

        listing.setQuantity(listing.getQuantity() + quantity);
    }

    @Override
    public List<ListingResponse> findByCardPrintOrderedByPrice(Long cardPrintId) {

        List<Listing> listings =
                listingRepository.findByCardPrintIdOrderByPriceAsc(cardPrintId);

        return listings.stream()
                .filter(l -> l.getQuantity() > 0) // apenas disponíveis
                .map(ListingResponse::from)
                .toList();
    }
}


