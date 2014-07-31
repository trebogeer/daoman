

-- mysql --user=root mysql
CREATE USER 'daoman'@'localhost' IDENTIFIED BY 'daoman';
GRANT ALL PRIVILEGES ON *.* TO 'daoman'@'localhost' WITH GRANT OPTION;
CREATE USER 'daoman'@'%' IDENTIFIED BY 'daoman';
GRANT ALL PRIVILEGES ON *.* TO 'daoman'@'%' WITH GRANT OPTION;