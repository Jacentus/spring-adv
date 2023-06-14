CREATE TABLE IF NOT EXISTS  payments (id INT PRIMARY KEY auto_increment, amount real NOT NULL, currency_code VARCHAR(5) NOT NULL, status VARCHAR(20) NOT NULL);
