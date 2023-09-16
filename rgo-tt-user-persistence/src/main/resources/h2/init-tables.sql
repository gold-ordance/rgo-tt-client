CREATE TABLE client (
    PRIMARY KEY(entity_id),
    entity_id          IDENTITY,
    email              VARCHAR(128) NOT NULL,
    password           VARCHAR(128) NOT NULL,
    created_date       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX email_uq_idx
    ON client(email);