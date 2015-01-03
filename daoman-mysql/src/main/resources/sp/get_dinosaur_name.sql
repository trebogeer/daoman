/*
-- CALL dinosaurs.get_dinosaurs(@err, 1);
*/

--DELIMITER $

DROP PROCEDURE IF EXISTS dinosaurs.get_dinosaur_name$

CREATE PROCEDURE dinosaurs.get_dinosaur_name(
  OUT error_code INT,
  OUT dname VARCHAR(40),
  IN id BIGINT
)
BEGIN

  SET error_code = -1;

  SELECT
    @dname := dinosaurName
  FROM
    dinosaurs.dinosaur
  WHERE dinosaurId = id;

  SET error_code = 0;

END$

--DELIMITER ;