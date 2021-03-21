DROP TABLE IF EXISTS park;
CREATE TABLE park
(
    id          BINARY(16)   NOT NULL,
    park_code   VARCHAR(20)  NOT NULL,
    states      VARCHAR(20)  NOT NULL,
    name        VARCHAR(100) NOT NULL,
    designation VARCHAR(100) NOT NULL,
    description TEXT         NOT NULL,
    PRIMARY KEY (id)
);

CREATE
UNIQUE INDEX idx_park_id ON park (id);
CREATE
UNIQUE INDEX idx_park_park ON park (park_code);

