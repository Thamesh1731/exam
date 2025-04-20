CREATE DATABASE exam;

USE exam;

CREATE TABLE student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    phone VARCHAR(15),
    reg_number VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    score INT,
    exam_date DATE
);

CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question TEXT,
    option1 VARCHAR(255),
    option2 VARCHAR(255),
    option3 VARCHAR(255),
    option4 VARCHAR(255),
    correct_option INT  -- Store the correct answer as an integer (1 to 4)
);

