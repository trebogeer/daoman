/*
-- CALL ecommerce.upsert_product(@err, 1);
*/
-- uncomment delimiter to run through mysql command line client
--DELIMITER $

DROP PROCEDURE IF EXISTS ecommerce.get_products$

CREATE PROCEDURE ecommerce.get_products(
  OUT error_code int,
  IN in_skip INT,
  IN in_limit INT
)
BEGIN
  SET error_code = -1;
  SELECT
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
     ProductUpdateDate,
     ProductStock,
     ProductLive,
     ProductUnlimited,
     ProductLocation
  FROM ecommerce.products LIMIT in_limit OFFSET in_skip;
  SET error_code = 0;
END$

--DELIMITER ;