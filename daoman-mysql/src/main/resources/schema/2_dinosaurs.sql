
-- DELIMITER $$

create database if not exists dinosaurs;

use dinosaurs;

drop table if exists dinosaur;
CREATE TABLE dinosaur (
  dinosaurId bigint NOT NULL AUTO_INCREMENT,
  dinosaurName varchar(40) default NULL,
  height double default NULL,
  length double default NULL,
  weight double default NULL,
  dinosaurFood varchar(40) default NULL,
  discoveredYear int(4) default NULL,
  discoveredLocation varchar(40) default NULL,
  PRIMARY KEY (dinosaurId)
);

INSERT INTO dinosaur (dinosaurName, height, length, weight, dinosaurFood, discoveredYear, discoveredLocation) VALUES
('Allosaurus', 5, 11, 1500, 'large dinosaurs', 1877, 'USA'),
('Troodon', 2, 1, 35, 'small dinosaurs', 1856, 'USA'),
('Deinonychus', 1, 3, 80, 'herbivorous dinosaurs', 1969, 'USA'),
('Kompsognat', 0.2, 0.3, 3, 'insects', 1959, 'Germany'),
('Tyrannosaurus Rex', 4, 13, 7000, 'large dinosaurs', 1905, 'USA');

     