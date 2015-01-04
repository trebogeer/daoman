/*
-- CALL ecommerce.upsert_product(@err, 1);
*/
-- uncomment delimiter to run through mysql command line client
--DELIMITER $

DROP PROCEDURE IF EXISTS ecommerce.get_product_by_id$

CREATE PROCEDURE ecommerce.get_product_by_id(
  OUT error_code int,
  IN ProductID int(12)
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
  FROM ecommerce.products ep
  WHERE ep.ProductID = ProductID;
   SET error_code = 0;
END$

--DELIMITER ;