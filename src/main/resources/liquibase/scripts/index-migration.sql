--liquibase formatted sql

--changeset abdullinru:1
create index studentNameIndex ON student (name);
--changeset abdullinru:2
create index nameAndColorFacultyIndex ON faculty (name, color);



