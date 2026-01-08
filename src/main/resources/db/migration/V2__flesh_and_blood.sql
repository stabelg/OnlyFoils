CREATE TABLE fab_card (
                          id BIGINT PRIMARY KEY,

                          card_type VARCHAR(40),
                          fab_class VARCHAR(40),
                          talent VARCHAR(40),
                          pitch VARCHAR(10),

                          CONSTRAINT fk_fab_card_card
                              FOREIGN KEY (id) REFERENCES card(id)
);

CREATE TABLE fab_card_print (
                                id BIGINT PRIMARY KEY,

                                fab_rarity VARCHAR(40),

                                CONSTRAINT fk_fab_card_print
                                    FOREIGN KEY (id) REFERENCES card_print(id)
);
