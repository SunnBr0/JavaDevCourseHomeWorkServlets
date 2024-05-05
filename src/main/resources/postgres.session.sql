
drop table if exists student;
drop table if exists groups;
CREATE TABLE IF NOT EXISTS student (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    groups_id INT
);
INSERT INTO student (name, age, groups_id) VALUES
('Alice', 20, 1),
('Bob', 22, 2),
('Charlie', 21, 3);

-- Создание таблицы groups
CREATE TABLE IF NOT EXISTS groups (
    id SERIAL PRIMARY KEY,
    faculty VARCHAR(100),
    number_of_students INT
);

-- Добавление записей в таблицу Student
INSERT INTO groups (faculty, number_of_students) VALUES
('Engineering', 150),
('Science', 200),
('Arts', 120);