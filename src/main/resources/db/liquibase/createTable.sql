--liquibase formatted sql
--changeset Matteo:create_tables
CREATE TABLE person (
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL
);

CREATE TABLE employee (
    id INTEGER NOT NULL PRIMARY KEY,
    salary INTEGER NOT NULL,
    department VARCHAR(255) NOT NULL,
    person_id INTEGER
);