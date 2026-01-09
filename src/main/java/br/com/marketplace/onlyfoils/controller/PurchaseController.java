package br.com.marketplace.onlyfoils.controller;

import br.com.marketplace.onlyfoils.dto.PurchaseRequest;
import br.com.marketplace.onlyfoils.dto.PurchaseResponse;
import br.com.marketplace.onlyfoils.model.Order;
import br.com.marketplace.onlyfoils.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<PurchaseResponse> purchase(
                @RequestBody @Valid PurchaseRequest request
    ) {

        Order order = purchaseService.purchase(request);

        PurchaseResponse response = new PurchaseResponse(
                order.getId(),
                order.getStatus(),
                order.getTotalPrice()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}

