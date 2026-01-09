CREATE TABLE listing (
                         id BIGSERIAL PRIMARY KEY,

                         seller_id BIGINT NOT NULL,
                         card_print_id BIGINT NOT NULL,

                         condition VARCHAR(30) NOT NULL,
                         language VARCHAR(30) NOT NULL,

                         signed BOOLEAN NOT NULL DEFAULT false,
                         altered BOOLEAN NOT NULL DEFAULT false,

                         quantity INT NOT NULL,
                         reserved_quantity INT NOT NULL DEFAULT 0,
                         price NUMERIC(10,2) NOT NULL,

                         CONSTRAINT fk_listing_seller
                             FOREIGN KEY (seller_id) REFERENCES app_user(id),

                         CONSTRAINT fk_listing_card_print
                             FOREIGN KEY (card_print_id) REFERENCES card_print(id)
);

-- Índices (marketplace critical)
CREATE INDEX idx_listing_card_print
    ON listing (card_print_id);

CREATE INDEX idx_listing_price
    ON listing (price);

CREATE INDEX idx_listing_seller
    ON listing (seller_id);

CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,

                        buyer_id BIGINT NOT NULL,

                        status VARCHAR(30) NOT NULL,
                        total_price NUMERIC(10,2) NOT NULL,

                        CONSTRAINT fk_orders_buyer
                            FOREIGN KEY (buyer_id) REFERENCES app_user(id)
);

CREATE INDEX idx_orders_buyer
    ON orders (buyer_id);

CREATE INDEX idx_orders_status
    ON orders (status);

CREATE TABLE order_item (
                            id BIGSERIAL PRIMARY KEY,

                            order_id BIGINT NOT NULL,
                            listing_id BIGINT NOT NULL,
                            seller_id BIGINT NOT NULL,

                            quantity INT NOT NULL,
                            price_at_purchase NUMERIC(10,2) NOT NULL,

                            CONSTRAINT fk_order_item_order
                                FOREIGN KEY (order_id) REFERENCES orders(id),

                            CONSTRAINT fk_order_item_listing
                                FOREIGN KEY (listing_id) REFERENCES listing(id),

                            CONSTRAINT fk_order_item_seller
                                FOREIGN KEY (seller_id) REFERENCES app_user(id)
);

CREATE INDEX idx_order_item_order
    ON order_item (order_id);

CREATE INDEX idx_order_item_listing
    ON order_item (listing_id);

CREATE INDEX idx_order_item_seller
    ON order_item (seller_id);