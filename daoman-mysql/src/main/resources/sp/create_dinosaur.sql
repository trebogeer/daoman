/*
-- CALL dinosaurs.upsert_dinosaur(@err, @id, "Dino", 1.0, 1.0, 1.0, "food", 1784,"north pole");
*/
-- uncomment delimiter to run through mysql command line client
--DELIMITER $

DROP PROCEDURE IF EXISTS dinosaurs.create_dinosaur$

CREATE PROCEDURE dinosaurs.create_dinosaur(
  OUT error_code INT,
  OUT id BIGINT,
  IN name VARCHAR(40),
  IN dheight DOUBLE,
  IN dlength DOUBLE,
  IN dweiht DOUBLE,
  IN food VARCHAR(40),
  IN yearDiscovery INT(4),
  IN location VARCHAR(40)
)
BEGIN

  SET error_code = 0;
  BEGIN
    DECLARE EXIT HANDLER FOR 1062 SET error_code = 1;
    INSERT INTO dinosaur (
        dinosaurName,
        height,
        length,
        weight,
        dinosaurFood,
        discoveredYear,
        discoveredLocation)
    VALUES
        (name,
        dheight,
        dlength,
        dweight,
        food,
        yearDiscovery,
        location);
  END;
  IF error_code = 0 THEN
    SET id = LAST_INSERT_ID();
  END IF;
END$

--DELIMITER ;