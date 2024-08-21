package utils;

import dao.EmployeeDAO;
import dao.StubDAO;
import model.Employee;
import model.Stub;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DatabasePopulationTest {

    private static final String[] NAMES = {"John Doe", "Jane Smith", "Bob Johnson", "Alice Williams", "Charlie Brown"};
    private static final String[] JOB_TITLES = {"Software Engineer", "Project Manager", "Data Analyst", "HR Specialist", "Marketing Coordinator"};
    private static final String[] DIVISIONS = {"IT", "HR", "Marketing", "Finance", "Operations"};

    public static void populateDatabase() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        StubDAO stubDAO = new StubDAO();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            try {
                // Create and save employee
                Employee employee = createRandomEmployee(random);
                boolean employeeCreated = employeeDAO.create(employee);

                if (employeeCreated) {
                    // Retrieve the created employee to get its ID
                    String ssn = employee.getSsn();
                    List<Employee> createdEmployees = employeeDAO.findBySSN(ssn);
                    if (!createdEmployees.isEmpty()) {
                        Employee createdEmployee = createdEmployees.getFirst();

                        // Create and save stub for the employee
                        Stub stub = createRandomStub(random, createdEmployee.getId());
                        stubDAO.create(stub);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error creating employee or stub: " + e.getMessage());
            }
        }
    }

    private static Employee createRandomEmployee(Random random) {
        double salary = roundToTwoDecimals(40000 + random.nextDouble() * 60000);
        return Employee.builder()
                .name(NAMES[random.nextInt(NAMES.length)])
                .ssn(generateRandomSSN(random))
                .jobTitle(JOB_TITLES[random.nextInt(JOB_TITLES.length)])
                .division(DIVISIONS[random.nextInt(DIVISIONS.length)])
                .salary(salary)
                .build();
    }

    private static Stub createRandomStub(Random random, int employeeId) {
        double salary = roundToTwoDecimals(2000 + random.nextDouble() * 3000);
        double deductions = roundToTwoDecimals(salary * 0.2);
        double netPay = roundToTwoDecimals(salary - deductions);
        double hours = roundToTwoDecimals(160 + random.nextDouble() * 20);

        return Stub.builder()
                .eid(employeeId)
                .salary(salary)
                .deductions(deductions)
                .grossPay(salary)
                .netPay(netPay)
                .hours(hours)
                .date(new Date())
                .build();
    }

    private static String generateRandomSSN(Random random) {
        return String.format("%03d-%02d-%04d",
                random.nextInt(1000),
                random.nextInt(100),
                random.nextInt(10000));
    }

    private static double roundToTwoDecimals(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static void main(String[] args) {
        populateDatabase();
        System.out.println("Database populated with 20 employees and their stubs.");
    }
}