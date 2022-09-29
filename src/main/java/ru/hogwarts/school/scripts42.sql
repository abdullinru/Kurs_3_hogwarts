ALTER TABLE student ADD CONSTRAINT age_constraint CHECK (age > 16);

ALTER TABLE student
    ADD CONSTRAINT nickname_unique UNIQUE (name);
ALTER TABLE student
    ALTER COLUMN name set not null;

ALTER TABLE student
    ALTER age set DEFAULT 20;

ALTER TABLE faculty
    ADD CONSTRAINT nameAndColor_unique UNIQUE (name, color);

