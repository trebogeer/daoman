
-- DELIMITER $$

create database if not exists dinosaurs;

use dinosaurs;

drop table if exists dinosaur;
CREATE TABLE dinosaur (
  dinosaurId bigint default NULL,
  dinosaurName varchar(40) default NULL,
  height double default NULL,
  length double default NULL,
  weight double default NULL,
  dinosaurFood varchar(40) default NULL,
  discoveredYear int(4) default NULL,
  discoveredLocation varchar(40) default NULL
);

INSERT INTO dinosaur VALUES
(1, 'Allosaurus', 5, 11, 1500, 'large dinosaurs', 1877, 'USA'),
(2, 'Troodon', 2, 1, 35, 'small dinosaurs', 1856, 'USA'),
(3, 'Deinonychus', 1, 3, 80, 'herbivorous dinosaurs', 1969, 'USA'),
(4, 'Kompsognat', 0.2, 0.3, 3, 'insects', 1959, 'Germany'),
(5, 'Tyrannosaurus Rex', 4, 13, 7000, 'large dinosaurs', 1905, 'USA');

     