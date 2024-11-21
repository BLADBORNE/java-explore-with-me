DROP TABLE IF EXISTS
    users CASCADE;

DROP TABLE IF EXISTS
    categories CASCADE;

DROP TABLE IF EXISTS
    locations CASCADE;

DROP TABLE IF EXISTS
    events CASCADE;

DROP TABLE IF EXISTS
    events_requests CASCADE;

DROP TABLE IF EXISTS
    compilations CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id    INTEGER GENERATED AlWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    name  VARCHAR(250)                                     NOT NULL CHECK (TRIM(name) <> ''),
    email VARCHAR(254)                                     NOT NULL CHECK (TRIM(email) <> '')
);

CREATE TABLE IF NOT EXISTS categories
(
    id   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    name VARCHAR(100)                                     NOT NULL CHECK (TRIM(name) <> '')
);

CREATE TABLE IF NOT EXISTS locations
(
    id  INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    lat NUMERIC(9, 6)                                    NOT NULL,
    lon NUMERIC(9, 6)                                    NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    id                 INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    title              VARCHAR(120)                                     NOT NULL CHECK (TRIM(annotation) <> ''),
    annotation         VARCHAR(2000)                                    NOT NULL CHECK (TRIM(annotation) <> ''),
    description        VARCHAR(7000)                                    NOT NULL,
    category_id        INTEGER                                          NOT NULL REFERENCES categories (id) ON DELETE CASCADE,
    initiator_id       INTEGER                                          NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    location_id        INTEGER                                          NOT NULL REFERENCES locations (id) ON DELETE CASCADE,
    created_on         TIMESTAMP WITHOUT TIME ZONE                      NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE                      NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    participant_limit  INTEGER                                          NOT NULL,
    confirmed_requests INTEGER                                          NOT NULL,
    request_moderation BOOLEAN                                          NOT NULL,
    state              VARCHAR(15)                                      NOT NULL,
    paid               BOOLEAN                                          NOT NULL
);

CREATE TABLE IF NOT EXISTS events_requests
(
    id           INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    requester_id INTEGER                                          NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    event_id     INTEGER                                          NOT NULL REFERENCES events (id) ON DELETE CASCADE,
    created      TIMESTAMP WITHOUT TIME ZONE                      NOT NULL,
    status       VARCHAR(15)                                      NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    pinned BOOLEAN                                          NOT NULL,
    title  VARCHAR(50)                                      NOT NULL
);

CREATE TABLE IF NOT EXISTS events_compilations
(
    compilation_id INTEGER NOT NULL REFERENCES compilations (id) ON DELETE CASCADE,
    event_id       INTEGER NOT NULL REFERENCES events (id) ON DELETE CASCADE,
    PRIMARY KEY (compilation_id, event_id)
);

CREATE TABLE IF NOT EXISTS comments
(
    id             INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    user_id        INTEGER                                          NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    event_id       INTEGER                                          NOT NULL REFERENCES events (id) ON DELETE CASCADE,
    text           VARCHAR(2000)                                    NOT NULL,
    published_date TIMESTAMP WITHOUT TIME ZONE                      NOT NULL
);