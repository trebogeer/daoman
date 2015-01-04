/*
-- CALL dinosaurs.upsert_dinosaur(@err, 1);
*/
-- uncomment delimiter to run through mysql command line client
--DELIMITER $

DROP PROCEDURE IF EXISTS dinosaurs.create_dinosaur$

CREATE PROCEDURE dinosaurs.create_dinosaur(
  OUT error_code INT,
  IN id BIGINT,
  IN name VARCHAR(40),
  IN dheight DOUBLE,
  IN dlength DOUBLE,
  IN dweiht DOUBLE,
  IN food VARCHAR(40),
  IN yearDiscovery INT(4),
  IN location VARCHAR(40)
)
BEGIN
  DECLARE cnt INT DEFAULT 0;
  SET error_code = 0;
  UPDATE dinosaur
  SET
     dinosaurName = name,
     height = dheight,
     length = dlength,
     weight = dweight,
     dinosaurFood = food,
     discoveredYear = yearDiscovery,
     discoveredLocation = location
  WHERE dinosaurId = id;
  SET cnt = ROW_COUNT();
  IF cnt = 0 THEN
    SET error_code = 1;
  END IF;
END$

--DELIMITER ;