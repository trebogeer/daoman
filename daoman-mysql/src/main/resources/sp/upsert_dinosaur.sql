/*
-- CALL dinosaurs.upsert_dinosaur(@err, 1);
*/
-- uncomment delimiter to run through mysql command line client
--DELIMITER $

DROP PROCEDURE IF EXISTS dinosaurs.create_dinosaur$

CREATE PROCEDURE dinosaurs.create_dinosaur(
  OUT error_code INT,
  INOUT id BIGINT,
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
  SET error_code = -1;
  INSERT INTO dinosaur (
        dinosaurId,
        dinosaurName,
        height,
        length,
        weight,
        dinosaurFood,
        discoveredYear,
        discoveredLocation)
  VALUES (
        id,
        name,
        dheight,
        dlength,
        dweight,
        food,
        yearDiscovery,
        location)
   ON DUPLICATE KEY UPDATE
        dinosaurName = name,
        height = dheight,
        length = dlength,
        weight = dweight,
        dinosaurFood = food,
        discoveredYear = yearDiscovery,
        discoveredLocation = location;

   SET cnt = ROW_COUNT();
   IF cnt = 1 THEN
     SET id = LAST_INSERT_ID();
   END IF;
   SET error_code = 0;
END$

--DELIMITER ;