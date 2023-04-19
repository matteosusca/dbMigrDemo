--liquibase formatted sql
--changeset matte:1.0.0
CREATE TABLE employee (
    id INTEGER NOT NULL PRIMARY KEY,
    salary INTEGER NOT NULL
);

CREATE TABLE person (
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL
);