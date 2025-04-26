--liquibase formatted sql

--changeset krasnovv:1
CREATE TABLE socks
(
    id          BIGSERIAL PRIMARY KEY,
    color       VARCHAR(50) NOT NULL,
    cotton_part INTEGER     NOT NULL,
    quantity    INTEGER     NOT NULL
);