package br.com.marketplace.onlyfoils.service;

import br.com.marketplace.onlyfoils.dto.CreateListingRequest;
import br.com.marketplace.onlyfoils.dto.ListingResponse;
import br.com.marketplace.onlyfoils.model.*;
import br.com.marketplace.onlyfoils.model.fab.FabCardPrint;
import br.com.marketplace.onlyfoils.repository.ListingRepository;
import br.com.marketplace.onlyfoils.service.impl.ListingServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListingServiceTest {

    @InjectMocks
    private ListingServiceImpl listingService;

    @Mock
    private ListingRepository listingRepository;

    @Mock
    private UserService userService;

    @Mock
    private CardPrintService cardPrintService;

    @Test
    void shouldCreateListingSuccessfully() {

        CreateListingRequest request = new CreateListingRequest(
                1L,
                10L,
                CardCondition.NM,
                Language.EN,
                5,
                false,
                false,
                new BigDecimal("100.00")
        );

        User seller = new User();
        seller.setId(1L);

        CardPrint cardPrint = new FabCardPrint();
        cardPrint.setId(10L);

        when(userService.getEntityById(1L)).thenReturn(seller);
        when(cardPrintService.getById(10L)).thenReturn(cardPrint);
        when(listingRepository.save(any(Listing.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Listing listing = listingService.create(request);

        assertThat(listing.getSeller()).isEqualTo(seller);
        assertThat(listing.getCardPrint()).isEqualTo(cardPrint);
        assertThat(listing.getQuantity()).isEqualTo(5);
        assertThat(listing.getPrice()).isEqualByComparingTo("100.00");
    }

    @Test
    void shouldFailWhenPriceIsZeroOrNegative() {

        CreateListingRequest request = new CreateListingRequest(
                1L,
                10L,
                CardCondition.NM,
                Language.EN,
                5,
                false,
                false,
                BigDecimal.ZERO
        );

        assertThatThrownBy(() -> listingService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Price must be greater than zero");

        verifyNoInteractions(listingRepository);
    }

    @Test
    void shouldReturnListingById() {

        Listing listing = new Listing();
        listing.setId(1L);

        when(listingRepository.findById(1L))
                .thenReturn(Optional.of(listing));

        Listing result = listingService.getById(1L);

        assertThat(result).isEqualTo(listing);
    }

    @Test
    void shouldThrowWhenListingNotFound() {

        when(listingRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> listingService.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Listing not found");
    }

    @Test
    void shouldReserveStockSuccessfully() {

        Listing listing = new Listing();
        listing.setId(1L);
        listing.setQuantity(10);

        when(listingRepository.findByIdForUpdate(1L))
                .thenReturn(Optional.of(listing));

        Listing result = listingService.reserveStock(1L, 3);

        assertThat(result.getQuantity()).isEqualTo(7);
    }

    @Test
    void shouldFailWhenNotEnoughStock() {

        Listing listing = new Listing();
        listing.setId(1L);
        listing.setQuantity(2);

        when(listingRepository.findByIdForUpdate(1L))
                .thenReturn(Optional.of(listing));

        assertThatThrownBy(() -> listingService.reserveStock(1L, 5))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Not enough stock");
    }

    @Test
    void shouldReleaseReservation() {

        Listing listing = new Listing();
        listing.setId(1L);
        listing.setQuantity(5);

        when(listingRepository.findByIdForUpdate(1L))
                .thenReturn(Optional.of(listing));

        listingService.releaseReservation(1L, 2);

        assertThat(listing.getQuantity()).isEqualTo(7);
    }

    @Test
    void shouldReturnOnlyAvailableListingsOrderedByPrice() {

        CardPrint cardPrint = new FabCardPrint();
        cardPrint.setId(10L);

        User seller = new User();
        seller.setId(2L);

        Listing l1 = new Listing();
        l1.setId(1L);
        l1.setQuantity(2);
        l1.setPrice(new BigDecimal("50.00"));
        l1.setSeller(seller);
        l1.setCardPrint(cardPrint);

        Listing l2 = new Listing();
        l2.setId(2L);
        l2.setQuantity(0); // indisponível
        l2.setPrice(new BigDecimal("30.00"));
        l2.setSeller(seller);
        l2.setCardPrint(cardPrint);

        when(listingRepository.findByCardPrintIdOrderByPriceAsc(10L))
                .thenReturn(List.of(l2, l1));

        List<ListingResponse> result =
                listingService.findByCardPrintOrderedByPrice(10L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getCardPrintId()).isEqualTo(10L);
    }


}

