CREATE TABLE app_user (
                          id BIGSERIAL PRIMARY KEY,

                          username VARCHAR(100) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          cpf VARCHAR(11) NOT NULL,

                          reputation NUMERIC(3,2),

                          store BOOLEAN NOT NULL DEFAULT false
);

CREATE UNIQUE INDEX uq_app_user_username ON app_user (username);
CREATE UNIQUE INDEX uq_app_user_email ON app_user (email);
CREATE UNIQUE INDEX uq_app_user_cpf ON app_user (cpf);

CREATE TABLE address (
                         id BIGSERIAL PRIMARY KEY,

                         user_id BIGINT NOT NULL REFERENCES app_user(id),

                         cep VARCHAR(8) NOT NULL,
                         street VARCHAR(150) NOT NULL,
                         number VARCHAR(20) NOT NULL,
                         complement VARCHAR(100),
                         district VARCHAR(100) NOT NULL,
                         city VARCHAR(100) NOT NULL,
                         state VARCHAR(2) NOT NULL,

                         main_address BOOLEAN NOT NULL DEFAULT false
);