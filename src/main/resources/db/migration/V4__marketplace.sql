CREATE TABLE listing (
                         id BIGSERIAL PRIMARY KEY,

                         seller_id BIGINT NOT NULL,
                         card_print_id BIGINT NOT NULL,

                         condition VARCHAR(30),
                         language VARCHAR(30),

                         signed BOOLEAN DEFAULT false,
                         altered BOOLEAN DEFAULT false,

                         quantity INT NOT NULL,
                         price NUMERIC(10,2) NOT NULL,

                         CONSTRAINT fk_listing_seller
                             FOREIGN KEY (seller_id) REFERENCES app_user(id),

                         CONSTRAINT fk_listing_card_print
                             FOREIGN KEY (card_print_id) REFERENCES card_print(id)
);

CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,

                        buyer_id BIGINT NOT NULL,
                        seller_id BIGINT NOT NULL,

                        status VARCHAR(30),
                        total_price NUMERIC(10,2),

                        CONSTRAINT fk_orders_buyer
                            FOREIGN KEY (buyer_id) REFERENCES app_user(id),

                        CONSTRAINT fk_orders_seller
                            FOREIGN KEY (seller_id) REFERENCES app_user(id)
);

CREATE TABLE order_item (
                            id BIGSERIAL PRIMARY KEY,

                            order_id BIGINT NOT NULL,
                            listing_id BIGINT NOT NULL,

                            quantity INT NOT NULL,
                            price_at_purchase NUMERIC(10,2) NOT NULL,

                            CONSTRAINT fk_order_item_order
                                FOREIGN KEY (order_id) REFERENCES orders(id),

                            CONSTRAINT fk_order_item_listing
                                FOREIGN KEY (listing_id) REFERENCES listing(id)
);
