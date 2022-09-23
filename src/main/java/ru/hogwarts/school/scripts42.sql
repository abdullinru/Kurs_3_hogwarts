ALTER TABLE student ADD CONSTRAINT age_constraint CHECK (age > 16);

ALTER TABLE student
    ADD CONSTRAINT nickname_unique UNIQUE (name);
ALTER TABLE student
    ALTER COLUMN name set not null;

ALTER TABLE student
    ADD CONSTRAINT default_age DEFAULT 20 FOR age;

ALTER TABLE faculty
    ADD CONSTRAINT nameAndColor_unique UNIQUE (name, color);

