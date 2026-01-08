CREATE TABLE card (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      dtype VARCHAR(50) NOT NULL
);

CREATE TABLE card_set (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          code VARCHAR(20),
                          release_year INT,
                          dtype VARCHAR(50) NOT NULL
);

CREATE TABLE card_print (
                            id BIGSERIAL PRIMARY KEY,
                            card_id BIGINT NOT NULL,
                            set_id BIGINT NOT NULL,

                            foil_type VARCHAR(30),
                            collector_number VARCHAR(50),
                            dtype VARCHAR(50) NOT NULL,

                            CONSTRAINT fk_card_print_card
                                FOREIGN KEY (card_id) REFERENCES card(id),

                            CONSTRAINT fk_card_print_set
                                FOREIGN KEY (set_id) REFERENCES card_set(id)
);
