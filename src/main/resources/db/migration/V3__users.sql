CREATE TABLE app_user (
                          id BIGSERIAL PRIMARY KEY,

                          username VARCHAR(100) NOT NULL UNIQUE,
                          email VARCHAR(255) NOT NULL UNIQUE,

                          reputation NUMERIC(3,2),

                          buyer BOOLEAN NOT NULL DEFAULT true,
                          seller BOOLEAN NOT NULL DEFAULT true
);