package br.com.marketplace.onlyfoils.service;

import br.com.marketplace.onlyfoils.dto.PurchaseRequest;
import br.com.marketplace.onlyfoils.model.Order;

public interface PurchaseService {

    Order purchase(PurchaseRequest request);
}

