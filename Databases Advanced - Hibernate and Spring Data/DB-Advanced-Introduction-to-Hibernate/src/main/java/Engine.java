import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;
import jdk.jfr.Unsigned;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class Engine implements Runnable {

    private EntityManager em;

    Engine(EntityManager em) {
        setEntityManager(em);
    }

    @Override
    public void run() {
        try {
            containsEmployee();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove Objects
     *
     *  Use the soft_uni database. Persist all towns from the database.
     *  Detach those whose name length is more than 5 symbols.
     *  Then transform the names of all attached towns to lowercase
     *  and save them to the database.
     */

    private void removeObjects(){
        List<Town> towns = em
                .createQuery("SELECT t FROM Town AS t")
                .getResultList();

        for (Town town : towns) {
            if (town.getName().length() > 5) {
                em.detach(town);
            }
        }

        em.getTransaction().begin();

        towns.stream().filter(em::contains).forEach(town -> {
            town.setName(town.getName().toLowerCase());
            em.persist(town);
        });

        em.getTransaction().commit();
        em.close();
    }

    /**
     * Contains Employee
     *
     * Use the soft_uni database. Write a program that checks
     * if a given employee name is contained in the database.
     */

    private void containsEmployee() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String employeeName = reader.readLine();

        try {
            Employee employee = (Employee) em.createQuery("SELECT e FROM Employee AS e WHERE concat(e.firstName, ' ', e.lastName) = :name")
                    .setParameter("name", employeeName)
                    .getSingleResult();

            System.out.println("Yes");
        } catch (NoResultException e) {
            System.out.println("No");

        }

        this.em.close();
    }

    /**
     * Employees with Salary Over 50 000
     *
     * Write a program that gets the first name of
     * all employees who have salary over 50 000.
     */

    private void salaryOverFiftyK(){

        List<Employee> towns = em
                .createQuery("FROM Employee WHERE salary > 50000")
                .getResultList();

        towns.stream().forEach(x -> System.out.println(x.getFirstName()));
    }

    /**
     * Employees from Department
     *
     * Extract all employees from the Research and Development
     * department. Order them by salary (in ascending order),
     * then by id (in asc order). Print only their first name,
     * last name, department name and salary.
     */

    private void employeesFromDepartment(){

        List<Employee> towns = em
                .createQuery("FROM Employee " +
                "WHERE department.id = 6 ORDER BY salary, id")
                .getResultList();

        towns.stream().forEach(x -> System.out.println(
                x.getFirstName() + " " + x.getLastName() +
                " from Research and Development - $" + x.getSalary()
        ));
    }

    /**
     * Adding a New Address and Updating Employee
     *
     * Create a new address with text "Vitoshka 15".
     * Set that address to an employee with last name from user input.
     */
    private void addingNewAddressAndUpdating() throws IOException {
        this.em.getTransaction().begin();
        Address address = new Address();
        address.setText("Vitoshka 15");

        Town town = new Town();
        town.setName("Sofia");
        address.setTown(town);

        this.em.persist(town);
        this.em.persist(address);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String lastName = reader.readLine();

        Employee employee = this.em.createQuery
                ("SELECT e FROM Employee AS e WHERE e.lastName = :lastName", Employee.class)
                .setParameter("lastName", lastName)
                .getSingleResult();

        this.em.detach(employee.getAddress());
        employee.setAddress(address);
        this.em.merge(employee);

        this.em.getTransaction().commit();
    }

    /**
     * Addresses with Employee Count
     *
     * Find all addresses, ordered by the number of employees who live there (descending),
     * then by town id (ascending).
     * Take only the first 10 addresses and print their address text, town name and employee count.
     */
    private void addressesWithEmployeeCount() {
        this.em.getTransaction().begin();

        List<Address> addressList = this.em.createQuery("SELECT a FROM Address AS a ORDER BY a.employees.size DESC, a.town.id", Address.class)
                .setMaxResults(10)
                .getResultList();

        addressList.forEach(x -> System.out.println(String.format("%s %s - %d employees",
                x.getText(), x.getTown().getName(), x.getEmployees().size())));

        this.em.getTransaction().commit();
    }

    /**
     * Get Employee with Project
     *
     * Get an employee by id. Print only his/her first name, last name, job title and projects (only their names).
     * The projects should be ordered by name (ascending). The output should be printed in the format given in example
     */
    private void getEmployeeWithProject() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int currentID = Integer.parseInt(reader.readLine());
        this.em.getTransaction().begin();

        List<Employee> employeeList = this.em.createQuery("SELECT e FROM Employee AS e WHERE e.id = :id", Employee.class)
                .setParameter("id", currentID)
                .getResultList();

        if (employeeList.isEmpty()){
            System.out.println("Please insert valid ID");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Employee employee : employeeList) {
            sb.append(String.format("%s %s - %s%n",
                    employee.getFirstName(), employee.getLastName(), employee.getJobTitle()));

            employee.getProjects().stream().sorted((x1, x2) -> {
                int comp =  x1.getName().compareTo(x2.getName());
                return comp;
            }).forEach(x -> sb.append("      ").append(x.getName()).append(System.lineSeparator()));
        }

        System.out.println(sb.toString());
        this.em.getTransaction().commit();
    }

    /**
     * Find Latest 10 Projects
     *
     * Write a program that prints last 10 started projects. Print their name, description,
     * start and end date and sort them by name lexicographically.
     * See example for output format.
     */
    private void findLastTenProjects() {
        StringBuilder sb = new StringBuilder();
        this.em.getTransaction().begin();

        List<Project> projectList = this.em.createQuery("SELECT p FROM Project AS p ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList();

        projectList.stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(x -> sb.append(String.format("Project name: %s%n",  x.getName()))
                        .append(String.format("     Project Description: %s", x.getDescription())).append(System.lineSeparator())
                        .append(String.format("     Project Start Date: %s", x.getStartDate())).append(System.lineSeparator())
                        .append(String.format("     Project End Date: %s", x.getEndDate())).append(System.lineSeparator()));

        System.out.println(sb.toString());
        this.em.getTransaction().commit();
    }

    /**
     * Increase Salaries
     *
     * Write a program that increases salaries of all employees that are in the Engineering,
     * Tool Design, Marketing or Information Services department by 12%.
     * Then print first name, last name and salary for those employees whose salary was increased.
     */
    private void increaseSalaries(){
        this.em.getTransaction().begin();

        List<Employee> employeeList = this.em.createQuery("SELECT e FROM Employee AS e WHERE e.department.name IN " +
                "('Tool Design','Marketing', 'Information Services', 'Engineering')" , Employee.class)
                .getResultList();

        for (Employee employee : employeeList) {
            employee.setSalary(employee.getSalary().multiply(BigDecimal.valueOf(1.12)));
        }

        employeeList.stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .forEach(e -> System.out.printf("%s %s ($%.2f)%n",
                        e.getFirstName(), e.getLastName(), e.getSalary()));

        this.em.getTransaction().commit();
    }


    /**
     * Remove Towns
     *
     * Write a program that deletes town which name is given as an input. Also delete all addresses that are in those towns.
     * Print on the console the number addresses that were deleted as given in the example:*/
    private void deleteTowns() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String townName = reader.readLine();

        List<Address> addresses = this.em.createQuery
                ("SELECT a FROM Address a WHERE a.town.name = :townName", Address.class)
                .setParameter("townName", townName)
                .getResultList();

        this.em.getTransaction().begin();

        for (Address address : addresses) {
            for (Employee employee : address.getEmployees()) {
                employee.setAddress(null);
            }
            this.em.remove(address);
        }

        Town town = this.em.createQuery(
                "SELECT t FROM Town t WHERE t.name = :townName", Town.class)
                .setParameter("townName", townName)
                .getSingleResult();

        this.em.remove(town);

        if (addresses.size() == 1) {
            System.out.printf("1 address in %s deleted%n", townName);
        } else {
            System.out.printf("%d addresses in %s deleted%n", addresses.size(), townName);
        }

        this.em.getTransaction().commit();
    }


    /**
     * Find Employees by First Name
     *
     * Write a program that finds all employees whose first name starts with pattern given as an input from the console.
     * Print their first, last name, their job title and salary in the format given in the examples below.
     */

    private void findEmployeesByFirstName() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String pattern = reader.readLine();
        this.em.getTransaction().begin();

        List<Employee> employeeList = this.em.createQuery("SELECT e FROM Employee AS e", Employee.class)
                .getResultList();

        employeeList.stream().filter(e ->
                e.getFirstName().toLowerCase().startsWith(pattern.toLowerCase())
        ).forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));

        this.em.getTransaction().commit();
    }

    /**
     * 12.	Employees Maximum Salaries
     * Write a program to find the max salary for each department.
     * Filter those which have max salaries not in the range 30000 and 70000.*/
    private void employeeMaximumSalaries(){
        this.em.getTransaction().begin();

        List<Object[]> result = this.em.createQuery("" +
                "SELECT d.name, MAX(e.salary) FROM Department AS d " +
                "INNER JOIN Employee AS e " +
                "ON e.department.id = d.id " +
                "GROUP BY d.name " +
                "HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000 " +
                "ORDER BY d.id", Object[].class)
                .getResultList();

        for (Object[] tokens : result) {
            System.out.printf("%s - %.2f%n", tokens[0], (BigDecimal) tokens[1]);
        }

        this.em.getTransaction().commit();
    }

    private void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
