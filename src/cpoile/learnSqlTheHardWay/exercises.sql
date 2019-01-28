CREATE DATABASE testdb;
USE testdb;

CREATE TABLE person
(
  id         INTEGER PRIMARY KEY,
  first_name TEXT,
  last_name  TEXT,
  age        INTEGER
);

CREATE TABLE pet
(
  id    INTEGER PRIMARY KEY,
  name  TEXT,
  breed TEXT,
  age   INTEGER,
  dead  BOOLEAN
);

CREATE TABLE person_pet
(
  person_id INTEGER,
  pet_id    INTEGER
);

CREATE TABLE car
(
  id    INTEGER PRIMARY KEY,
  make  TEXT,
  model TEXT,
  km    INTEGER
);

CREATE TABLE person_car
(
  person_id INTEGER,
  car_id    INTEGER
);

INSERT INTO person (id, first_name, last_name, age)
VALUES (0, "Jon", "Wood", 39);
INSERT INTO person (id, first_name, last_name, age)
VALUES (1, "Chris", "Poile", 39);
INSERT INTO person (id, first_name, last_name, age)
VALUES (2, "Tasha", "Wood", 44);
INSERT INTO pet (id, name, breed, age, dead)
VALUES (0, "Basil", "Siamese", 12, true);
INSERT INTO pet (id, name, breed, age, dead)
VALUES (1, "Phoebe", "Tabby", 13, false);
INSERT INTO pet (id, name, breed, age, dead)
VALUES (2, "Scooter", "Gerbil", 2, true);
INSERT INTO person_pet (person_id, pet_id)
VALUES (0, 0);
INSERT INTO person_pet (person_id, pet_id)
VALUES (0, 1);
INSERT INTO person_pet (person_id, pet_id)
VALUES (2, 0);
INSERT INTO person_pet (person_id, pet_id)
VALUES (2, 1);
INSERT INTO person_pet (person_id, pet_id)
VALUES (1, 2);

SELECT *
from pet;
SELECT *
from person;
SELECT *
from person_pet;

SELECT name, breed
FROM pet;
SELECT name, breed
FROM pet
WHERE dead = 1;
SELECT *
FROM pet
WHERE name != "Phoebe";
SELECT *
FROM pet
WHERE age >= 12
  AND name != "Phoebe";

DESCRIBE pet;

SELECT *
FROM pet,
     person_pet,
     person
WHERE person.id = person_pet.person_id
  AND pet.id = person_pet.pet_id
  AND person.first_name = "Jon";

SELECT pet.id, pet.name, pet.breed, pet.age, pet.dead
FROM pet
WHERE pet.id IN (
  SELECT pet_id
  FROM person_pet,
       person
  WHERE person.id = person_pet.person_id
    AND person.first_name = "Jon"
);

Select * FROM person INNER JOIN
SELECT * FROM pet INNER JOIN person_pet ON pet.id = person_pet.pet_id;