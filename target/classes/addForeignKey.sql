--changeset matte:1.0.0-add-foreign-key
ALTER TABLE employee ADD CONSTRAINT fk_employee_person FOREIGN KEY (id) REFERENCES person (id);