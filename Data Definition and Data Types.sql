01. Create Tables
------------------------
CREATE TABLE minions (
  Id INT PRIMARY KEY ,
  name VARCHAR(50),
  age INT 
);

CREATE TABLE towns (
  Id INT PRIMARY KEY ,
  name VARCHAR(50)
);

02. Alter Minions Table
------------------------
ALTER TABLE minions ADD column town_id int;
ALTER TABLE minions
ADD CONSTRAINT fk_minions_towns FOREIGN KEY(town_id)
REFERENCES towns(id);

03. Insert Records in Both Tables
------------------------
INSERT INTO towns(id, name)
VALUES (1, 'Sofia'),
(2, 'Plovdiv'),
(3, 'Varna');

INSERT INTO minions(id, name, age, town_id)
VALUES (1, 'Kevin', 22, 1),
(2, 'Bob', 15, 3),
(3, 'Steward', NULL, 2);

04. Truncate Table Minions
------------------------
DELETE minions FROM minions;

05. Drop All Tables
------------------------
DROP TABLE minions;
DROP TABLE towns;

06. Create Table People
------------------------
CREATE TABLE people (
  id int AUTO_INCREMENT,
  name VARCHAR(200) not null,
  picture mediumblob
  ,height float(6,2)
  ,weight float(6,2)
  ,gender enum ('m','f') not null
  ,birthdate datetime not null
  ,biography text,
  constraint pk_people PRIMARY key (id)
);

INSERT INTO people(id,name,gender,birthdate,biography)
VALUES(1,'Pesho','m',now(),'areg  sthsrthsrth sfg hft'),
(2,'Misho','m',now(),'areg  sthsrthsrth sfg hft'),
(3,'Simo','m',now(),'areg  sthsrthsrth sfg hft'),
(4,'Maria','f',now(),'areg  sthsrthsrth sfg hft'),
(5,'Miro','m',now(),'areg  sthsrthsrth sfg hft')

07. Create Table Users
------------------------
CREATE TABLE users(
	id BIGINT UNIQUE AUTO_INCREMENT,
	username VARCHAR(30) UNIQUE  NOT NULL,
	password  VARCHAR(26) NOT NULL,
	profile_picture  BLOB,
	last_login_time DATE,
	is_deleted BOOL
);

ALTER TABLE users
ADD CONSTRAINT pk_users PRIMARY KEY(id);

INSERT INTO users(username, password, last_login_time, is_deleted)
VALUES ('Gogo', 'spojpe',  '2017-05-15', TRUE),
('Bobo','epgojro', '2017-08-05', FALSE),
('Ani',  'rpker', '2017-04-25', TRUE),
('Sasho',  'rgpjrpe', '2017-05-06', TRUE),
('Gery', 'pkptkh','2017-01-11', FALSE);

08. Change Primary Key
------------------------
ALTER TABLE users
DROP PRIMARY KEY,
ADD CONSTRAINT pk_users PRIMARY KEY (`id`, `username`);

09. Set Default Value of a Field
------------------------
ALTER TABLE users
MODIFY  COLUMN last_login_time 
TIMESTAMP 
NOT NULL DEFAULT CURRENT_TIMESTAMP;

10. Set Unique Field
------------------------
ALTER TABLE users MODIFY id BIGINT NOT NULL;
ALTER TABLE users DROP PRIMARY KEY;
ALTER TABLE users ADD CONSTRAINT pk_users PRIMARY KEY(id);
ALTER TABLE users ADD CONSTRAINT uq_username UNIQUE (username);

11. Movies Database
------------------------
CREATE TABLE directors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    director_name VARCHAR(30) not null,
    notes BLOB
);
INSERT INTO directors(id,director_name)
VALUES(1,'yjfjfyjf'),(2,'yjgjg'),(3,'dasdasjkd'),(4,'gm'),(5,'guj');

CREATE TABLE genres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    genre_name VARCHAR(30) not null not null,
    notes BLOB
);
INSERT INTO genres(id,genre_name)
VALUES(2,'myumymu'),(1,'yummyum'),(3,'umyum'),(4,'fgnnu'),(5,'ymuumyu');


CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(30) not null,
    notes BLOB
);
insert into categories(id,category_name)
values (1,'wi-fi')
,(2,'wi-fi')
,(3,'wi-fi')
,(4,'wi-fi')
,(5,'wi-fi');

CREATE TABLE movies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(30) not null,
    director_id INT,
    copyright_year DATETIME not null,
    length INT not null,
    genre_id INT not null,
    category_id INT not null,
    rating INT,
    notes BLOB
);
 insert into movies(id,title,copyright_year,`length`,genre_id,category_id)
 values(11,'dasdasda','2018-12-2',2,1,2),
(10,'dasdasda','2018-12-12',3,1,2),
(13,'dasdasda','2018-12-12',2,1,2),
(14,'dasdasda','2018-12-12',3,1,2),
(15,'dasdasda','2018-12-12',3,1,2);

12. Car Rental Database
------------------------
13. Hotel Database
------------------------
CREATE TABLE employees(
   id INT,
  first_name varchar(50) not null,
  last_name varchar(50),
  title varchar(50),
  notes blob
  ,PRIMARY KEY (id)
);
INSERT INTO employees(id,first_name)
VALUES(1,'gfnsfn'),(2,'gfn'),(3,'dgngfnd');

CREATE TABLE customers (
   account_number int not null,
   first_name varchar(50) not null, 
	last_name varchar(50),
	phone_number varchar(10),
	emergency_name varchar(50),
	emergency_number varchar(10),
	notes blob,
	primary key(account_number)
);
INSERT INTO customers(account_number,first_name)VALUES(1,'sdg'),(2,'gdsg'),(3,'dsf');
CREATE TABLE room_status (
   room_status int not null,
   notes blob,
   primary key(room_status)
);
INSERT INTO room_status(room_status)VALUES(1),(2),(3);

CREATE TABLE room_types(
   room_type INT not null,
   notes blob not null,
   primary key(room_type)
);
INSERT INTO room_types(room_type,notes)VALUES(1,'sdggs'),(2,'sdggsd'),(3,'sdgsg');

CREATE TABLE bed_types(
  bed_type INT not null,
  notes blob not null,
  primary key(bed_type)
);
INSERT INTO bed_types(bed_type,notes)VALUES(1,'sdgdg'),(2,'gdsg'),(3,'gsdgsd');

CREATE TABLE rooms(
   room_number int not null, 
   room_type varchar(50),
   bed_type varchar(50),
   rate varchar(50), 
   room_status varchar(50), 
   notes blob not null, 
   primary key(room_number)
);
INSERT INTO rooms(room_number,notes)VALUES(1,'dss'),(2,'dgsdg'),(3,'sdgsd');

CREATE TABLE payments(
   id INT not null,
   employee_id int,
   payment_date datetime, 
   account_number varchar(10), 
   first_date_occupied datetime, 
   last_date_occupied datetime, 
   total_days INT, 
   amount_charged double(10,2), 
   tax_rate double(10,2), 
   tax_amount double(10,2), 
   payment_total double(10,2), 
   notes blob not null, 
   primary key(id)
);
INSERT INTO payments(id,notes)VALUES(1,'dsfdf'),(2,'dffg'),(3,'fdhdfhfdh');

CREATE TABLE occupancies(
  id int, 
  employee_id int ,
  date_occupied datetime,
  account_number varchar(10),
  room_number int,
  rate_applied varchar(10), 
  phone_charge varchar(10), 
  notes blob not null, 
  primary key(id)
);
INSERT INTO occupancies(id,notes)VALUES(1,'sdgsdg'),(2,'sfdsd'),(3,'sdf');

14. Create SoftUni Database
------------------------
CREATE TABLE towns(
   id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
   name varchar(30)
);
CREATE TABLE addresses(
   id  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
   address_text  varchar(50),
   town_id INT,
   CONSTRAINT fk_addresses_towns FOREIGN KEY (town_id)
   REFERENCES towns(id) 
);
CREATE TABLE `departments`(
   id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
   name varchar(10)
);
CREATE TABLE employees(
   id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
   first_name varchar(10),
   middle_name varchar(10),
   last_name varchar(10),
   job_title varchar(10),
   department_id INT,
   hire_date datetime,
   salary double (10,2),
   address_id INT,
   CONSTRAINT FK_address_id FOREIGN KEY (address_id)
   REFERENCES addresses(id),
   CONSTRAINT FK_department_id FOREIGN KEY (department_id)
   REFERENCES  departments (id)  
);


16. Basic Insert
------------------------
INSERT INTO towns(name)
VALUES
('Sofia'),('Plovdiv'),('Varna'),('Burgas');

INSERT INTO departments(name)
VALUES
('Engineering'),('Sales'),('Marketing'),('Software Development'),('Quality Assurance');

INSERT INTO employees(first_name,middle_name,last_name,job_title,department_id,hire_date,salary)
VALUES('Ivan','Ivanov','Ivanov','.NET Developer',4,'2013-02-01','3500.00'),
('Petar','Petrov','Petrov','Senior Engineer',1,'2004-03-02','4000.00'),
('Maria','Petrova','Ivanova','Intern',5,'2016-08-28','525.25'),
('Georgi','Terziev','Ivanov','CEO',2,'2007-12-09','3000.00'),
('Peter','Pan','Pan','Intern',3,'2016-08-28','599.88');

17. Basic Select All Fields
------------------------
SELECT * FROM towns;
SELECT * FROM departments;
SELECT * FROM employees;

18. Basic Select All Fields and Order Them
------------------------
SELECT * FROM towns AS t ORDER BY t.name ;
SELECT * FROM departments AS d ORDER BY d.name ;
SELECT * FROM employees 
ORDER BY salary desc;

19. Basic Select Some Fields
------------------------
SELECT name FROM towns AS t ORDER BY name ;
SELECT name FROM departments AS d ORDER BY name ;
SELECT first_name,last_name,job_title,salary FROM employees ORDER BY salary DESC;

20. Increase Employees Salary
------------------------
UPDATE employees
SET salary = salary * 1.1;
SELECT salary FROM employees;

21. Decrease Tax Rate
------------------------
SELECT tax_rate * 0.97 FROM payments;

22. Delete All Records
------------------------
DELETE FROM occupancies;

