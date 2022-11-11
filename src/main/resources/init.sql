CREATE SCHEMA IF NOT EXISTS PUBLIC;
CREATE DATABASE go_it_dev_hw3 WITH OWNER root;
COMMENT ON SCHEMA PUBLIC IS 'standard public schema';
ALTER SCHEMA PUBLIC OWNER TO root;

-- developers (данные о разработчиках(имя, возраст, пол и прочее))
CREATE TYPE gender_enum AS ENUM ('male', 'female');
CREATE TABLE developers
(
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	age INT NOT NULL,
	gender gender_enum,
	salary INT
);

-- skills (отрасль – Java, C++, C#, JS; уровень навыков - Junior, Middle, Senior)
CREATE TYPE language_enum AS ENUM ('Java', 'C++', 'C#', 'JS');
CREATE TYPE level_enum AS ENUM ('Junior', 'Middle', 'Senior');
CREATE TABLE skills (
	id SERIAL PRIMARY KEY,
	department language_enum,
	level level_enum
);

-- companies (IT компании, в которых работают разработчики
CREATE TABLE companies (
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	location VARCHAR(100) NOT NULL
);

-- customers (клиенты, которые являются заказчиками проектов в IT компаниях)
CREATE TABLE customers (
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL
);

-- projects (проекты, на которых работают разработчики)
CREATE TABLE projects (
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	company_id INT REFERENCES companies(id),
	customer_id INT REFERENCES customers(id),
	cost INT

);

CREATE TABLE companies_developers (
	company_id INT REFERENCES companies(id),
	developer_id INT REFERENCES developers(id),
	CONSTRAINT companies_developers_pk PRIMARY KEY (company_id, developer_id)
);

CREATE TABLE developers_projects (
	developer_id INT REFERENCES developers(id),
	project_id INT REFERENCES projects(id),
	CONSTRAINT developers_projects_pk PRIMARY KEY (developer_id, project_id)
);
CREATE TABLE developers_skills (
	developer_id INT REFERENCES developers(id),
	skill_id INT REFERENCES skills(id),
	CONSTRAINT developers_skills_pk PRIMARY KEY (developer_id, skill_id)
);

ALTER TABLE projects
ADD COLUMN IF NOT EXISTS creation_date DATE DEFAULT now();