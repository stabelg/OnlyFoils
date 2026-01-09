package br.com.marketplace.onlyfoils.controller;

import br.com.marketplace.onlyfoils.dto.CreateListingRequest;
import br.com.marketplace.onlyfoils.dto.ListingResponse;
import br.com.marketplace.onlyfoils.service.ListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ListingResponse create(
            @RequestBody @Valid CreateListingRequest request
    ) {
        return ListingResponse.from(
                listingService.create(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<ListingResponse>> getListings(
            @RequestParam Long cardPrintId,
            @RequestParam(required = false, defaultValue = "price") String orderBy
    ) {

        if (!"price".equalsIgnoreCase(orderBy)) {
            throw new IllegalArgumentException("Invalid orderBy: " + orderBy);
        }

        return ResponseEntity.ok(
                listingService.findByCardPrintOrderedByPrice(cardPrintId)
        );
    }
}

