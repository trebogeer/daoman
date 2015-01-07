/*
-- CALL ecommerce.upsert_product(@err, 1);
*/
-- uncomment delimiter to run through mysql command line client
--DELIMITER $

DROP PROCEDURE IF EXISTS ecommerce.get_optiongroups$

CREATE PROCEDURE ecommerce.get_optiongroups(
  OUT error_code int
)
BEGIN
  SET error_code = -1;
  SELECT
     OptionGroupID,
     OptionGroupName
  FROM ecommerce.optiongroups;-- LIMIT in_limit OFFSET in_skip;
  SET error_code = 0;
END$

--DELIMITER ;