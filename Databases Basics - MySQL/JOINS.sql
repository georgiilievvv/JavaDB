1. Employee Address

SELECT e.employee_id, e.job_title, e.address_id, a.address_text
FROM employees e
JOIN addresses a
ON a.address_id = e.address_id
ORDER BY e.address_id
LIMIT 5;

2. Addresses with Towns
-------------------------------
SELECT e.first_name, e.last_name, t.name AS towns, a.address_text
FROM employees e
JOIN addresses a
ON a.address_id = e.address_id
LEFT JOIN towns t
ON t.town_id = a.town_id
ORDER BY first_name,last_name
LIMIT 5;

3. Sales Employee
-------------------------------
SELECT e.employee_id, e.first_name, e.last_name, d.name AS department_name
FROM employees e
LEFT JOIN departments d
ON d.department_id = e.department_id
WHERE d.name = 'Sales'
ORDER BY e.employee_id DESC;

4. Employee Departments
-------------------------------
SELECT e.employee_id, e.first_name, e.salary, d.name AS department_name
FROM employees e
LEFT JOIN departments d
ON d.department_id = e.department_id
WHERE e.salary > 15000
ORDER BY e.department_id DESC
LIMIT 5;

5. Employees Without Project
-------------------------------
SELECT e.employee_id, e.first_name
FROM employees e
LEFT JOIN employees_projects p
ON p.employee_id = e.employee_id
  WHERE p.project_id IS NULL
ORDER BY e.employee_id DESC
LIMIT 3;

6. Employees Hired After
-------------------------------
SELECT e.first_name, e.last_name, e.hire_date, d.name AS dept_name
FROM employees e
LEFT JOIN departments d
ON d.department_id = e.department_id
  WHERE e.hire_date >= '1999-01-02'
  AND d.name IN ('Sales', 'Finance')
ORDER BY e.hire_date ;

7. Employees with Project
--------------------------------

SELECT e.employee_id, e.first_name, pr.name project_name
FROM employees e
LEFT JOIN employees_projects p
ON p.employee_id = e.employee_id
  LEFT JOIN projects pr
  ON pr.project_id = p.project_id
  WHERE pr.start_date >= '2002-08-14'
  AND pr.end_date IS NULL
ORDER BY e.first_name, pr.name 
LIMIT 5;

8. Employee 24
--------------------------------
SELECT e.employee_id, e.first_name,
  CASE
    WHEN pr.start_date >= '2005-01-01' THEN NULL
    ELSE pr.name
  END AS project_name
FROM employees e
INNER JOIN employees_projects p
ON p.employee_id = e.employee_id
  INNER JOIN projects pr
  ON pr.project_id = p.project_id
  WHERE e.employee_id = 24
ORDER BY project_name
LIMIT 5;

9. Employee Manager
--------------------------------
SELECT e.employee_id, e.first_name, e.manager_id, emp.first_name AS manager_name
FROM employees e
JOIN employees emp
ON e.manager_id = emp.employee_id
  WHERE e.manager_id IN (3,7)
  ORDER BY e.first_name;

10. Employee Summary
--------------------------------
SELECT e.employee_id, CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
  CONCAT(emp.first_name, ' ', emp.last_name) AS manager_name,
  d.name AS department_name
FROM employees e
JOIN employees emp
ON e.manager_id = emp.employee_id
JOIN departments d
ON e.department_id = d.department_id
ORDER BY e.employee_id
LIMIT 5;

11. Min Average Salary
---------------------------------
SELECT AVG(salary) AS min_average_salary
FROM employees
GROUP BY department_id
ORDER BY min_average_salary
LIMIT 1;

12. Highest Peaks in Bulgaria
---------------------------------
SELECT c.country_code, m.mountain_range, p.peak_name, p.elevation
FROM countries c
JOIN mountains_countries mc
ON c.country_code = mc.country_code
JOIN mountains m
ON mc.mountain_id = m.id
JOIN peaks p
ON mc.mountain_id = p.mountain_id
WHERE c.country_name = 'Bulgaria'
AND p.elevation > 2835
ORDER BY p.elevation DESC ;

13. Count Mountain Ranges
---------------------------------
SELECT c.country_code, COUNT(m.mountain_range) AS mountain_range
FROM countries c
  JOIN mountains_countries mc
    ON c.country_code = mc.country_code
  JOIN mountains m
    ON mc.mountain_id = m.id
GROUP BY mc.country_code
HAVING mc.country_code IN
(
	SELECT DISTINCT c.country_code FROM countries AS c
	WHERE c.country_name IN ('United States', 'Russia', 'Bulgaria')
)
ORDER BY mountain_range DESC ;

14. Countries with Rivers
---------------------------------
SELECT c.country_name, r.river_name
FROM countries c
  LEFT JOIN countries_rivers cr
    ON c.country_code = cr.country_code
  LEFT JOIN rivers r
    ON cr.river_id = r.id
WHERE c.continent_code = 'AF'
ORDER BY c.country_name
LIMIT 5;

16. Countries without any Mountains
----------------------------------
SELECT COUNT(*) AS county_count
FROM countries c
WHERE NOT c.country_code IN(
  SELECT mc.country_code FROM mountains_countries AS mc
  );
  
17. Highest Peak and Longest River by Country
----------------------------------
SELECT
  c.country_name,
  MAX(p.elevation) AS highest_peak_elevation,
  MAX(r.length)    AS longest_river_length
FROM countries c
  LEFT JOIN mountains_countries mc
    ON c.country_code = mc.country_code
  LEFT JOIN peaks p
    ON mc.mountain_id = p.mountain_id
  LEFT JOIN countries_rivers cr
    ON c.country_code = cr.country_code
  LEFT JOIN rivers r
    ON cr.river_id = r.id
GROUP BY c.country_name
ORDER BY elevation DESC, length DESC, c.country_name
LIMIT 5;
