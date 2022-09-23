CREATE TABLE human (
                         Id SERIAL,
                         name varchar (20),
                         Age INTEGER,
                         isPrava BOOLEAN,
                         car_id integer REFERENCES car (id)
);

CREATE TABLE car (
                       Id SERIAL,
                       marka varchar (10),
                       model char (10),
                       stoimost INTEGER
);
