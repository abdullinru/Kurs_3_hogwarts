CREATE TABLE human (
                         Id integer primary key,
                         name varchar (20),
                         Age INTEGER,
                         isPrava BOOLEAN,
                         car_id integer REFERENCES car (id)
);
CREATE TABLE car (
                       Id integer primary key,
                       marka varchar (10),
                       model char (10),
                       stoimost INTEGER
);
