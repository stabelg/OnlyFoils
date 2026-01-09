package br.com.marketplace.onlyfoils.repository.fab;

import br.com.marketplace.onlyfoils.model.fab.FabCardPrint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FabCardPrintRepository
        extends JpaRepository<FabCardPrint, Long> {
}
