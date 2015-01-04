/*
-- CALL dinosaurs.upsert_dinosaur(@err, 1);
*/
-- uncomment delimiter to run through mysql command line client
--DELIMITER $

DROP PROCEDURE IF EXISTS ecommerce.set_product_price$

CREATE PROCEDURE ecommerce.set_product_price(
  OUT error_code INT,
  IN PID int(12),
  IN price float
)
BEGIN
  DECLARE cnt INT DEFAULT 0;
  SET error_code = 0;
  UPDATE products p
  SET
     p.ProductPrice = price
  WHERE p.ProductId = PID;
  SET cnt = ROW_COUNT();
  IF cnt = 0 THEN
    SET error_code = 1;
  END IF;
END$

--DELIMITER ;