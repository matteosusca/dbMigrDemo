--liquibase formatted sql
--changeset matte:1.0.0-insert
INSERT INTO person (id, name, surname, date_of_birth) VALUES (1, 'John', 'Smith', '1980-01-01');
INSERT INTO person (id, name, surname, date_of_birth) VALUES (2, 'Mary', 'Smith', '1985-01-01');
INSERT INTO person (id, name, surname, date_of_birth) VALUES (3, 'Peter', 'Smith', '1990-01-01');

INSERT INTO employee (id, salary, department) VALUES (1, 1000, 'IT');
INSERT INTO employee (id, salary, department) VALUES (2, 2000, 'HR');
INSERT INTO employee (id, salary, department) VALUES (3, 3000, 'Sales');