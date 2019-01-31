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

DROP TABLE person_car;
DROP TABLE car;

-- load data:
DELETE
  FROM person;
DELETE
  FROM pet;
DELETE
  FROM person_pet;
INSERT INTO person (id, first_name, last_name, age)
  VALUES (0, "Jon", "Wood", 39);
INSERT INTO person (id, first_name, last_name, age)
  VALUES (1, "Chris", "Poile", 39);
INSERT INTO person (id, first_name, last_name, age)
  VALUES (2, "Tasha", "Wood", 44);
INSERT INTO person (id, first_name, last_name, age)
  VALUES (3, "Mai", "Poile", 9);
INSERT INTO pet (id, name, breed, age, dead)
  VALUES (0, "Basil", "Siamese", 12, true);
INSERT INTO pet (id, name, breed, age, dead)
  VALUES (1, "Phoebe", "Tabby", 13, false);
INSERT INTO pet (id, name, breed, age, dead)
  VALUES (2, "Scooter", "Gerbil", 2, true);
INSERT INTO pet (id, name, breed, age, dead)
  VALUES (3, "Asta", "Dog", 4, false);
INSERT INTO pet (id, name, breed, age, dead)
  VALUES (4, "Apple", "Stuffed cat", 1, false);
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
INSERT INTO person_pet (person_id, pet_id)
  VALUES (1, 3);
INSERT INTO person_pet (person_id, pet_id)
  VALUES (3, 4);

SHOW TABLES;

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


SELECT *
  FROM pet;
DELETE
  FROM pet
  WHERE dead = 1;


INSERT INTO pet (id, name, breed, age, dead)
  VALUES (0, "Basil", "Siamese", 12, true);
INSERT INTO pet (id, name, breed, age, dead)
  VALUES (2, "Scooter", "Gerbil", 2, true);

-- delete dead pets owned by me
SELECT *
  FROM pet;
DELETE
  FROM pet
  WHERE dead = true
    AND id IN (
    SELECT pet_id
      FROM person_pet,
           person
      WHERE person.id = person_id
        AND person.first_name = 'Chris'
  );

DELETE
  FROM pet
  WHERE id IN (
    SELECT person_pet.pet_id
      FROM person_pet,
           person
      WHERE person.id = person_pet.person_id
        AND person.first_name = "Chris"
        AND pet.dead = true
  );


SELECT *
  FROM pet;
SELECT *
  FROM person_pet; -- want to get rid of pet_id 2 (scooter)
DELETE
  FROM person_pet
  WHERE pet_id NOT IN (
    SELECT id
      FROM pet
  );
SELECT *
  FROM person_pet; -- want to get rid of pet_id 3 (scooter)


INSERT INTO pet (id, name, breed, age, dead)
  VALUES (2, "Scooter", "Gerbil", 2, true);
INSERT INTO person_pet (person_id, pet_id)
  VALUES (1, 2);
SELECT *
  FROM pet;
SELECT *
  FROM person_pet;


-- delete people who have dead pets
SELECT *
  FROM person;
SELECT *
  FROM pet;
DELETE
  FROM person
  WHERE id IN (
    SELECT person_id
      FROM person_pet,
           pet
      WHERE pet_id = pet.id
        AND dead = true
  );

-- remove dead pets from the person_pet relationship
SELECT *
  FROM person_pet;
SELECT *
  FROM pet;
DELETE
  FROM person_pet
  WHERE pet_id IN (
    SELECT id
      FROM pet
      WHERE dead = true
  );

-- change the breed of Scooter to short haired furball
SELECT *
  FROM pet;
UPDATE pet
SET breed = 'short haired furball'
  WHERE name = 'scooter';
SELECT *
  FROM pet;

-- change the name of any dead animals to deceased
SELECT *
  FROM pet;
UPDATE pet
SET name = 'DECEASED'
  WHERE dead = true;

-- change my dead pet's names to chris's dead pets
UPDATE pet
SET name = 'Chris''s dead pet'
  WHERE dead = true
    AND id IN (
    SELECT pet_id
      FROM person_pet,
           person
      WHERE person_id = person.id
        AND first_name = 'Chris'
  );
SELECT *
  FROM pet;

-- do an atomic change to whole row
REPLACE INTO pet (id, name, breed, age, dead)
  VALUES (2, 'Scooter', 'Gerbil', 2, 1);

-- add a column for height to the person table, then rename person to people, then back.
DESCRIBE person;
ALTER TABLE person
  ADD COLUMN height INT;
ALTER TABLE person
  ADD COLUMN dob DATETIME;
ALTER TABLE person RENAME TO people;
ALTER TABLE people RENAME TO person;

SELECT *
  FROM pet;
SELECT @id := MAX(id) + 1
  FROM pet;
SELECT COUNT(*)
  FROM pet;

START TRANSACTION;
-- do stuff
COMMIT;
-- or:
ROLLBACK;


CREATE TABLE Employees
(
  EmployeeID   INT PRIMARY KEY,
  DepartmentID INT,
  BossID       INT,
  Name         TEXT,
  Salary       INT
);

CREATE TABLE Departments
(
  DepartmentID INT PRIMARY KEY,
  Name         Text
);

