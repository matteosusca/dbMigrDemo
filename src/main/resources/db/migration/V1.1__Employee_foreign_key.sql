ALTER TABLE employee ADD CONSTRAINT fk_employee_person
    FOREIGN KEY (person_id)
    REFERENCES person (id);