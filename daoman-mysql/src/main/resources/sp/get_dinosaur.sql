/*
-- CALL dinosaurs.get_dinosaurs(@err, 1);
*/
-- uncomment delimiter to run through mysql command line client
--DELIMITER $

DROP PROCEDURE IF EXISTS dinosaurs.get_dinosaur$

CREATE PROCEDURE dinosaurs.get_dinosaur(
  OUT error_code INT,
  IN dheight DOUBLE,
  IN dweiht DOUBLE
)
BEGIN

  SET error_code = -1;

  SELECT
    dinosaurId,
    dinosaurName,
    height,
    length,
    weight,
    dinosaurFood,
    discoveredYear,
    discoveredLocation
  FROM
    dinosaurs.dinosaur
  WHERE height = dheight AND weight = dweiht;

  SET error_code = 0;

END$

--DELIMITER ;