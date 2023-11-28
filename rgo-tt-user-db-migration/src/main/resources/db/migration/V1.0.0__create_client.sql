CREATE SEQUENCE client_sequence;

CREATE TABLE client (
    PRIMARY KEY(entity_id),
    entity_id          BIGINT       DEFAULT nextval('client_sequence'),
    email              VARCHAR(128) NOT NULL,
    password           VARCHAR(128) NOT NULL,
    created_date       TIMESTAMP    DEFAULT (now() AT TIME ZONE 'UTC'),
    last_modified_date TIMESTAMP    DEFAULT (now() AT TIME ZONE 'UTC')
);

GRANT SELECT, INSERT, UPDATE, DELETE ON client TO ${appRole};
GRANT SELECT ON client TO ${readerRole};

CREATE UNIQUE INDEX client_email_idx
                 ON client(email)
         TABLESPACE ${tbsIndexes};