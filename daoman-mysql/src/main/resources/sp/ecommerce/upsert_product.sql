/*
-- CALL ecommerce.upsert_product(@err, 1);
*/
-- uncomment delimiter to run through mysql command line client
--DELIMITER $

DROP PROCEDURE IF EXISTS ecommerce.upsert_product$

CREATE PROCEDURE ecommerce.upsert_product(
  OUT error_code int,
  INOUT ProductID int(12),
  IN ProductSKU varchar(50),
  IN ProductName varchar(100),
  IN ProductPrice float,
  IN ProductWeight float,
  IN ProductCartDesc varchar(250),
  IN ProductShortDesc varchar(1000),
  IN ProductLongDesc text,
  IN ProductThumb varchar(100),
  IN ProductImage varchar(100),
  IN ProductCategoryID int(11),
  IN ProductStock float,
  IN ProductLive tinyint(1),
  IN ProductUnlimited tinyint(1),
  IN ProductLocation varchar(250)
)
BEGIN
  DECLARE cnt INT DEFAULT 0;
  SET error_code = -1;
  INSERT INTO ecommerce.products
  VALUES (
     ProductID,
     ProductSKU,
     ProductName,
     ProductPrice,
     ProductWeight,
     ProductCartDesc,
     ProductShortDesc,
     ProductLongDesc,
     ProductThumb,
     ProductImage,
     ProductCategoryID,
     NOW(),
     ProductStock,
     ProductLive,
     ProductUnlimited,
     ProductLocation
  )
  ON DUPLICATE KEY UPDATE
     ProductID = VALUES(ProductID),
     ProductSKU = VALUES(ProductSKU),
     ProductName = VALUES(ProductName),
     ProductPrice = VALUES(ProductPrice),
     ProductWeight = VALUES(ProductWeight),
     ProductCartDesc = VALUES(ProductCartDesc),
     ProductShortDesc = VALUES(ProductShortDesc),
     ProductLongDesc = VALUES(ProductLongDesc),
     ProductThumb = VALUES(ProductThumb),
     ProductImage = VALUES(ProductImage),
     ProductCategoryID = VALUES(ProductCategoryID),
     ProductUpdateDate = VALUES(ProductUpdateDate),
     ProductStock = VALUES(ProductStock),
     ProductLive = VALUES(ProductLive),
     ProductUnlimited = VALUES(ProductUnlimited),
     ProductLocation = VALUES(ProductLocation);
   SET cnt = ROW_COUNT();
   IF cnt = 1 THEN
     SET ProductID = LAST_INSERT_ID();
   END IF;
   SET error_code = 0;
END$

--DELIMITER ;