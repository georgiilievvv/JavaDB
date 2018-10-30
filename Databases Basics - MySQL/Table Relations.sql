01. One-To-One Relationship
---------------------------
CREATE TABLE passports (
  passport_id INT(20) ,
  passport_number varchar(30),
  CONSTRAINT pk_persons_person_id
  PRIMARY KEY (passport_id)
);

CREATE TABLE persons(
  person_id INT(20),
  first_name varchar(30),
  salary decimal(10,2),
  passport_id int unique ,
  CONSTRAINT pk_persons_person_id
  PRIMARY KEY (person_id),
  CONSTRAINT fk_passport_id
  FOREIGN KEY (passport_id)
  REFERENCES passports(passport_id)
);

INSERT passports (passport_id,passport_number)
VALUES (101, 'N34FG21B'),
  (102, 'K65LO4R7'),
  (103, 'ZE657QP2');


INSERT persons(person_id, first_name, salary, passport_id)
VALUES (1, 'Roberto', 43300.00, 102),
  (2, 'Tom', 56100.00, 103),
  (3, 'Yana', 60200.00, 101)

02.	One-To-Many Relationship
---------------------------
CREATE TABLE manufacturers (
  manufacturer_id INT(20) unique ,
  name varchar(30),
  established_on date,
  CONSTRAINT pk_manufacturers__id
  PRIMARY KEY (manufacturer_id)
);

CREATE TABLE models(
  model_id INT(20) unique,
  name varchar(30),
  manufacturer_id int ,
  CONSTRAINT pk_persons_person_id
  PRIMARY KEY (model_id),
  CONSTRAINT fk_manufacturer_id
  FOREIGN KEY (manufacturer_id)
  REFERENCES manufacturers(manufacturer_id)
);

INSERT manufacturers (manufacturer_id, name, established_on)
VALUES (1, 'BMW', '1916-03-01'),
  (2, 'Tesla', '2003-01-01'),
  (3, 'Lada', '1966-05-01');


INSERT models(model_id, name, manufacturer_id)
VALUES (101, 'X1', 1),
  (102, 'i6', 1),
  (103, 'Model S', 2),
  (104, 'Model X', 2),
  (105, 'Model 3', 2),
  (106, 'Nova', 3);

03. Many-To-Many Relationship
---------------------------
CREATE TABLE students (
  student_id INT(20) unique ,
  name varchar(30),
  CONSTRAINT pk_student_id
  PRIMARY KEY (student_id)
);

CREATE TABLE exams(
  exam_id INT(20) unique,
  name varchar(30),
  CONSTRAINT pk_exam_id
  PRIMARY KEY (exam_id)
);

CREATE TABLE students_exams(
  student_id INT(20),
  exam_id INT(20),
  CONSTRAINT pk_student_id_exam_id
  PRIMARY KEY (student_id,exam_id),
  CONSTRAINT fk_student_id
  FOREIGN KEY (student_id)
  REFERENCES students(student_id),
  CONSTRAINT fk_exam_id
  FOREIGN KEY (exam_id)
  REFERENCES exams(exam_id)
);

INSERT students (student_id, name)
VALUES (1, 'Mila'),
  (2, 'Toni'),
  (3, 'Ron');


INSERT exams(exam_id, name)
VALUES (101, 'Spring MVC'),
  (102, 'Neo4j'),
  (103, 'Oracle 11g');

INSERT students_exams (student_id, exam_id)
VALUES (1, 101),
  (1, 102),
  (2, 101),
  (3, 103),
  (2, 102),
  (2, 103);

04. Self-Referencing
---------------------------
CREATE TABLE IF NOT EXISTS teachers (
  teacher_id INT PRIMARY KEY AUTO_INCREMENT,
  name       VARCHAR(15),
  manager_id INT
);

INSERT INTO teachers (teacher_id, name, manager_id)
VALUES (101, 'John', NULL),
  (102, 'Maya', 106),
  (103, 'Silvia', 106),
  (104, 'Ted', 105),
  (105, 'Mark', 101),
  (106, 'Greta', 101);

ALTER TABLE teachers
ADD CONSTRAINT fk_manager_id FOREIGN KEY (manager_id)
REFERENCES teachers (teacher_id);

05. Online Store Database
---------------------------
CREATE TABLE cities
(
city_id INT PRIMARY KEY,
name VARCHAR(50) NOT NULL
);

CREATE TABLE customers
(
  custome_id INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  birthday DATE,
  city_id INT NOT NULL,
  CONSTRAINT FK_Customers_Cities FOREIGN KEY (city_id) REFERENCES cities(city_id)
);

CREATE TABLE orders
(
  order_id INT PRIMARY KEY,
  customer_id INT NOT NULL,
  CONSTRAINT FK_Orders_Customers FOREIGN KEY (customer_id) REFERENCES customers(custome_id)
);

CREATE TABLE item_types
(
  item_type_id INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE items
(
  item_id INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  item_type_id INT NOT NULL,
  CONSTRAINT FK_Items_ItemTypes FOREIGN KEY(item_id) REFERENCES item_types(item_type_id)
);

CREATE TABLE order_items
(
  order_id INT NOT NULL,
  item_id INT NOT NULL,
  CONSTRAINT PK_OrderItems PRIMARY KEY(order_id,item_id),
  CONSTRAINT FK_OrderItems_Orders FOREIGN KEY (order_id) REFERENCES orders(order_id),
  CONSTRAINT FK_OrderItems_Items FOREIGN KEY (item_id) REFERENCES items(item_id)
)

06.	University Database
---------------------------
CREATE TABLE subjects (
  subject_id   INT(11) PRIMARY KEY,
  subject_name VARCHAR(50) NOT NULL
);

CREATE TABLE majors
(
  major_id INT PRIMARY KEY,
  name     VARCHAR(50)
);

CREATE TABLE students (
  student_id     INT PRIMARY KEY,
  student_number VARCHAR(12) NOT NULL,
  student_name   VARCHAR(50),
  major_id       INT         NOT NULL,
  CONSTRAINT fk_majors_major_id
  FOREIGN KEY (major_id)
  REFERENCES majors (major_id)
);


CREATE TABLE agenda (
  student_id INT(11),
  subject_id INT(11),
  CONSTRAINT fk_students_student_id
  FOREIGN KEY (student_id)
  REFERENCES students (student_id),
  CONSTRAINT fk_students_subject_id
  FOREIGN KEY (subject_id)
  REFERENCES subjects (subject_id)
  
);

CREATE TABLE payments
(
  payment_id     INT PRIMARY KEY,
  payment_date   DATE,
  payment_amount DECIMAL(8, 2),
  student_id     INT(11),
  CONSTRAINT fk_payments_student_id
  FOREIGN KEY (student_id)
  REFERENCES students (student_id)
);

09.	Peaks in Rila
---------------------------
SELECT
  m.mountain_range,
  p.peak_name,
  p.elevation AS peak_elevation
FROM countries c
  JOIN mountains_countries mc
    ON c.country_code = mc.country_code
  JOIN mountains m
    ON mc.mountain_id = m.id
  JOIN peaks p
    ON m.id = p.mountain_id
  WHERE m.mountain_range = 'Rila'
ORDER BY peak_elevation DESC;
