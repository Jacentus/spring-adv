DROP TABLE IF EXISTS payments;
CREATE TABLE payments (id INT PRIMARY KEY auto_increment, amount VARCHAR(100) NOT NULL, status VARCHAR(100) NOT NULL);
