/*
-- CALL dinosaurs.upsert_dinosaur(@err, 1);
*/
-- uncomment delimiter to run through mysql command line client
--DELIMITER $

DROP PROCEDURE IF EXISTS dinosaurs.set_dinosaur_year_discovered$

CREATE PROCEDURE dinosaurs.set_dinosaur_year_discovered(
  OUT error_code INT,
  IN id BIGINT,
  IN yearDiscovery INT(4)
)
BEGIN
  DECLARE cnt INT DEFAULT 0;
  SET error_code = 0;
  UPDATE dinosaur
  SET
     discoveredYear = yearDiscovery
  WHERE dinosaurId = id;
  SET cnt = ROW_COUNT();
  IF cnt = 0 THEN
    SET error_code = 1;
  END IF;
END$

--DELIMITER ;