INSERT INTO developers (name, age, gender)
VALUES
('James', 25, 'male'),
('Mary', 22, 'female'),
('Robert', 30, 'male'),
('Patricia', 25, 'female'),
('John', 24, 'male'),
('Jennifer', 27, 'female'),
('Michael', 24, 'male'),
('David', 40, 'male'),
('Elizabeth', 26, 'female'),
('William', 31, 'male');

INSERT INTO skills (department, level)
VALUES
('Java', 'Junior'),
('Java', 'Middle'),
('Java', 'Senior'),
('JS', 'Junior'),
('JS', 'Middle'),
('JS', 'Senior'),
('C++', 'Junior'),
('C++', 'Middle'),
('C++', 'Senior'),
('C#', 'Junior'),
('C#', 'Middle'),
('C#', 'Senior');


INSERT INTO companies (name, location)
VALUES
('Goit', 'Kyiv'),
('Electric', 'NY'),
('Teravision', 'Bogota'),
('Microsoft ', 'Washington'),
('IBM', 'Atlanta'),
('Oracle', 'California');


INSERT INTO customers (name, email)
VALUES
('Steven', 'steven@gmail.com'),
('George', 'george@gmail.com'),
('Edward', 'edward@gmail.com'),
('Samuel', 'samuel@gmail.com'),
('Tyler', 'tyler@gmail.com'),
('Raymond', 'raymond@gmail.com'),
('Peter', 'peter@gmail.com'),
('Henry', 'henry@gmail.com');

INSERT INTO projects (name, company_id, customer_id, creation_date)
VALUES
('Project 1', 1, 3, '2010-01-01'),
('Project 2', 3, 2, '2011-02-02'),
('Project 3', 2, 4, '2012-03-03'),
('Project 4', 4, 6, '2013-04-04'),
('Project 5', 6, 7, '2014-05-05'),
('Project 6', 3, 5, '2015-06-06'),
('Project 7', 2, 8, '2016-07-07'),
('Project 8', 5, 1, '2017-08-08');

INSERT INTO companies_developers (company_id, developer_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(1, 7),
(2, 8),
(3, 9),
(4, 10);

INSERT INTO developers_projects (developer_id, project_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 1),
(10, 2);

INSERT INTO developers_skills (developer_id, skill_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 12);

UPDATE developers SET salary = 700 WHERE id = 1;
UPDATE developers SET salary = 750 WHERE id = 2;
UPDATE developers SET salary = 800 WHERE id = 3;
UPDATE developers SET salary = 850 WHERE id = 4;
UPDATE developers SET salary = 900 WHERE id = 5;
UPDATE developers SET salary = 950 WHERE id = 6;
UPDATE developers SET salary = 1000 WHERE id = 7;
UPDATE developers SET salary = 1050 WHERE id = 8;
UPDATE developers SET salary = 1100 WHERE id = 9;
UPDATE developers SET salary = 1150 WHERE id = 10;

UPDATE projects SET cost = 3000 WHERE id = 1;
UPDATE projects SET cost = 4000 WHERE id = 2;
UPDATE projects SET cost = 5000 WHERE id = 3;
UPDATE projects SET cost = 6000 WHERE id = 4;
UPDATE projects SET cost = 7000 WHERE id = 5;
UPDATE projects SET cost = 8000 WHERE id = 6;
UPDATE projects SET cost = 9000 WHERE id = 7;
UPDATE projects SET cost = 10000 WHERE id = 8;