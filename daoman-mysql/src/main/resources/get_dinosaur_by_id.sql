/*
-- CALL dinosaurs.get_dinosaurs(@err, 1);
*/

DELIMITER $$

DROP PROCEDURE IF EXISTS dinosaurs.get_dinosaur_by_id$$

CREATE PROCEDURE dinosaurs.get_dinosaur_by_id(
  OUT error_code INT,
  IN id BIGINT
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
  WHERE dinosaurId = id;

  SET error_code = 0;

END$$

DELIMITER ;