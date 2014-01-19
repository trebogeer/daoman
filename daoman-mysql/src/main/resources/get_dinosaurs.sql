/*
-- CALL dinosaurs.get_dinosaurs(@err);
*/

DELIMITER $$

DROP PROCEDURE IF EXISTS dinosaurs.get_dinosaurs$$

CREATE PROCEDURE dinosaurs.get_dinosaurs(
  OUT error_code INT
)
BEGIN

  SET error_code = -1;

  SELECT 	
    dinosaurId,
    dinosaurName
  FROM 
    dinosaurs.dinosaur;

  SET error_code = 0;

END$$

DELIMITER ;