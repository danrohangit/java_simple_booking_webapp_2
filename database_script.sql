-- Create a new database
CREATE DATABASE restaurantBooking;

-- Use the new database
USE restaurantBooking;

-- Create a new table
CREATE TABLE booking (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    location VARCHAR(300) NOT NULL,
    theDate DATETIME NOT NULL,
    PRIMARY KEY (id)
);

-- create the user
CREATE USER 'aaaa'@'localhost' IDENTIFIED BY 'qazwsx123';

-- grant only insert access to user
GRANT INSERT ON restaurantBooking.booking TO 'aaaa'@'localhost';

-- flush to ensure changes take effect
FLUSH PRIVILEGES;

-- Insert some sample data into the table
/*
INSERT INTO users (name, email, password) VALUES
('John Doe', 'john@example.com', 'password123'),
('Jane Doe', 'jane@example.com', 'qwerty456');
*/