CREATE TABLE absence (
    id INTEGER NOT NULL PRIMARY KEY,
    employee_id INTEGER NOT NULL,
    start_date DATE NOT NULL,
    absence_type_id INTEGER NOT NULL,
    end_date DATE NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE absence_type (
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);