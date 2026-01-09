package br.com.marketplace.onlyfoils.service.impl;

import br.com.marketplace.onlyfoils.model.CardPrint;
import br.com.marketplace.onlyfoils.repository.CardPrintRepository;
import br.com.marketplace.onlyfoils.service.CardPrintService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CardPrintServiceImpl implements CardPrintService {

    private final CardPrintRepository cardPrintRepository;

    @Override
    public CardPrint getById(Long id) {
        return cardPrintRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("CardPrint not found: " + id)
                );
    }
}

