--liquibase formatted sql
--changeset matte:2.1
CREATE TABLE absence_type (
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

--changeset matte:2.2
INSERT INTO absence_type (id, name) VALUES (1, 'Sick leave');
INSERT INTO absence_type (id, name) VALUES (2, 'Vacation');
INSERT INTO absence_type (id, name) VALUES (3, 'Parental leave');
INSERT INTO absence_type (id, name) VALUES (4, 'Other');