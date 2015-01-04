/*
-- CALL dinosaurs.get_test_some_out(@err, 1);
*/

--DELIMITER $

DROP PROCEDURE IF EXISTS dinosaurs.get_test_some_out$

CREATE PROCEDURE dinosaurs.get_test_some_out(
  OUT error_code INT,
  OUT test1 VARCHAR(40),
  OUT test2 VARCHAR(40),
  OUT test3 VARCHAR(40),
  OUT test4 VARCHAR(40),
  OUT test5 INT,
  IN id BIGINT
)
BEGIN

  SET error_code = -1;
  SET test1 = "test1";
  SET test2 = "test2";
  SET test3 = "test3";
  SET test4 = "test4";
  SET test5 = 5;
END$

--DELIMITER ;
