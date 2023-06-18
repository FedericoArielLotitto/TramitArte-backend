CREATE USER 'username'@'%' IDENTIFIED BY 'Password123/';
GRANT ALL PRIVILEGES ON mydatabase.* TO 'username'@'%';
FLUSH PRIVILEGES;