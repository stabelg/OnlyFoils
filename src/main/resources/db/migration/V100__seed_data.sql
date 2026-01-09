INSERT INTO card (name, dtype)
VALUES
    ('Bravo, Showstopper', 'FAB_CARD'),
    ('Dorinthea Ironsong', 'FAB_CARD'),
    ('Katsu, the Wanderer', 'FAB_CARD');

INSERT INTO app_user (username, email, cpf, reputation, store)
VALUES
    ('alice',   'alice@test.com',   '12345678901', 4.80, false),
    ('bob',     'bob@test.com',     '98765432100', 4.95, true),
    ('charlie', 'charlie@test.com', '11122233344', 4.50, false);

INSERT INTO address (
    user_id, cep, street, number, complement,
    district, city, state, main_address
)
VALUES
    -- Alice
    (1, '01001000', 'Praça da Sé', '100', 'Apto 12',
     'Sé', 'São Paulo', 'SP', true),

    -- Bob (lojista)
    (2, '20040002', 'Rua da Quitanda', '50', null,
     'Centro', 'Rio de Janeiro', 'RJ', true),

    -- Charlie
    (3, '30130010', 'Av. Afonso Pena', '1500', 'Sala 901',
     'Centro', 'Belo Horizonte', 'MG', true);


INSERT INTO card_set (dtype, name, code, release_year)
VALUES ('FAB_SET', 'Welcome to Rathe', 'WTR', 2019);

INSERT INTO fab_card_set (id)
VALUES (1);

INSERT INTO fab_card (card_type, fab_class, talent, pitch)
VALUES
    ('HERO', 'GUARDIAN', NULL, 'THREE'),
    ('HERO', 'WARRIOR',  NULL, 'TWO'),
    ('HERO', 'NINJA',    NULL, 'ONE');

INSERT INTO card_print (card_id, set_id, foil_type, collector_number, dtype)
VALUES
    (1, 1, 'FOIL', 'WTR001', 'FAB_PRINT'),
    (2, 1, 'NON_FOIL',    'WTR002', 'FAB_PRINT'),
    (3, 1, 'SPECIAL_FOIL',    'WTR003', 'FAB_PRINT');

INSERT INTO listing (
    seller_id, card_print_id,
    condition, language,
    quantity, signed, altered, price
)
VALUES
    (2, 1, 'NM', 'EN', 2, false, false, 120.00),
    (2, 2, 'NM', 'EN', 5, false, false, 35.00),
    (3, 3, 'LP', 'EN', 1, false, false, 25.00);


INSERT INTO orders (
    buyer_id, status, total_price
)
VALUES
    (1, 'PAID', 155.00);

INSERT INTO order_item (
    order_id, listing_id, seller_id, quantity, price_at_purchase
)
VALUES
    (1, 1, 2, 1, 120.00),
    (1, 2, 2, 1, 35.00);

