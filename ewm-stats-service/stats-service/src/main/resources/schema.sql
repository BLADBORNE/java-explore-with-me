DROP TABLE IF EXISTS
    stats CASCADE;

CREATE TABLE IF NOT EXISTS stats
(
    id           INTEGER GENERATED AlWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    app          VARCHAR(50)                                      NOT NULL,
    uri          VARCHAR(50)                                      NOT NULL,
    ip           VARCHAR(50)                                      NOT NULL,
    created_date TIMESTAMP WITHOUT TIME ZONE                      NOT NULL
);