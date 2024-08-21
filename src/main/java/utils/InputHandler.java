package utils;

import lombok.RequiredArgsConstructor;
import model.Employee;
import service.EmployeeManagementService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class InputHandler {
    private final Scanner scanner;
    private final EmployeeManagementService managementService;
    public final String CANCEL_COMMAND = "q";

    public int getOption() {
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    public void searchById() throws SQLException {
        Integer id = getEmployeeIdFromUser();
        if (id != null) {
            Employee employee = managementService.findEmployee(id);
            if (employee != null) {
                System.out.println("Employee found: " + employee);
            } else {
                System.out.println("No employee found with ID: " + id);
            }
        }
    }

    public void searchAllEmployees() throws SQLException {
        List<Employee> employees = managementService.findAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            employees.forEach(System.out::println);
        }
    }

    public void searchByName() throws SQLException {
        String name = getValidInput("Enter employee name: ", this::isValidName);
        if (name != null) {
            List<Employee> employees = managementService.findAllEmployeesByName(name);
            if (employees.isEmpty()) {
                System.out.println("No employees found with name: " + name);
            } else {
                employees.forEach(System.out::println);
            }
        }
    }

    public void searchBySSN() throws SQLException {
        String ssn = getValidSSN();
        if (ssn != null) {
            List<Employee> employees = managementService.findEmployeeBySSN(ssn);
            if (employees.isEmpty()) {
                System.out.println("No employees found with ssn: " + ssn);
            } else {
                if (employees.size() > 1) {
                    System.out.println("WARNING: Duplicates found.");
                }
                employees.forEach(System.out::println);
            }
        }
    }

    public void searchByJobTitle() throws SQLException {
        String jobTitle = getValidInput("Enter job title: ", this::isValidJobTitle);
        if (jobTitle != null) {
            List<Employee> employees = managementService.findAllEmployeesByJobTitle(jobTitle);
            if (employees.isEmpty()) {
                System.out.println("No employees found with job title: " + jobTitle);
            } else {
                employees.forEach(System.out::println);
            }
        }
    }

    public void searchByDivision() throws SQLException {
        String division = getValidInput("Enter division: ", this::isValidDivision);
        if (division != null) {
            List<Employee> employees = managementService.findAllEmployeesByDivision(division);
            if (employees.isEmpty()) {
                System.out.println("No employees found in division: " + division);
            } else {
                employees.forEach(System.out::println);
            }
        }
    }

    public void updateName(Employee employee) {
        String name = getValidInputWithDefault("Enter new name (current: " + employee.getName() + "): ",
                employee.getName(),
                this::isValidName);
        if (name != null) {
            employee.setName(name);
        }
    }

    public void updateSSN(Employee employee) {
        String ssn = getValidSSNWithDefault("Enter new SSN (current: " + employee.getSsn() + "): ",
                employee.getSsn());
        if (ssn != null) {
            employee.setSsn(ssn);
        }
    }

    public void updateJobTitle(Employee employee) {
        String jobTitle = getValidInputWithDefault("Enter new job title (current: " + employee.getJobTitle() + "): ",
                employee.getJobTitle(),
                this::isValidJobTitle);
        if (jobTitle != null) {
            employee.setJobTitle(jobTitle);
        }
    }

    public void updateDivision(Employee employee) {
        String division = getValidInputWithDefault("Enter new division (current: " + employee.getDivision() + "): ",
                employee.getDivision(),
                this::isValidDivision);
        if (division != null) {
            employee.setDivision(division);
        }
    }

    public void updateSalary(Employee employee) {
        Double salary = getValidSalaryWithDefault("Enter new salary (current: " + employee.getSalary() + "): ",
                employee.getSalary());
        if (salary != null) {
            employee.setSalary(salary);
        }
    }

    public void saveUpdatedEmployee(Integer eid, Employee employee) throws SQLException {
        if (!managementService.updateEmployee(eid, employee)) {
            System.out.println("An unknown error has occurred.");
        }
        System.out.println("Employee updated successfully.");
    }


    public String getValidInputWithDefault(String prompt, String defaultValue, InputValidator validator) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return defaultValue;
            }
            if (input.equalsIgnoreCase(CANCEL_COMMAND)) {
                return null;
            }
            if (validator.isValid(input)) {
                return input;
            }
            System.out.println("Invalid input. Please try again or enter '" + CANCEL_COMMAND + "' to cancel.");
        }
    }

    public String getValidSSNWithDefault(String prompt, String defaultValue) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return defaultValue;
            }
            if (input.equalsIgnoreCase(CANCEL_COMMAND)) {
                return null;
            }
            String ssn = input.replaceAll("[^0-9]", "");
            if (ssn.length() == 9) {
                return formatSSN(ssn);
            }
            System.out.println("Invalid SSN. Please enter 9 digits or enter '" + CANCEL_COMMAND + "' to cancel.");
        }
    }

    public Double getValidSalaryWithDefault(String prompt, Double defaultValue) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return defaultValue;
            }
            if (input.equalsIgnoreCase(CANCEL_COMMAND)) {
                return null;
            }
            try {
                double salary = Double.parseDouble(input);
                if (salary >= 0) {
                    return salary;
                }
                System.out.println("Salary must be non-negative. Please try again or enter '" + CANCEL_COMMAND + "' to cancel.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary format. Please enter a valid number or enter '" + CANCEL_COMMAND + "' to cancel.");
            }
        }
    }

    public Integer getEmployeeIdFromUser() {
        while (true) {
            System.out.print("Enter the employee id of target: ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase(CANCEL_COMMAND)) return null;

            try {
                int id = Integer.parseInt(input);
                if (id >= 1) {
                    return id;
                }
                System.out.println("Id must be non-negative. Please try again or enter '" + CANCEL_COMMAND + "' to cancel.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid eid format. Please enter a valid id or enter '" + CANCEL_COMMAND + "' to cancel.");
            }
        }
    }

    public Employee getEmployeeDetailsFromUser() {
        String name = getValidInput("Enter the name of the employee: ", this::isValidName);
        if (name == null) return null;

        String ssn = getValidSSN();
        if (ssn == null) return null;

        String jobTitle = getValidInput("Enter the job title of the employee: ", this::isValidJobTitle);
        if (jobTitle == null) return null;

        String division = getValidInput("Enter the division of the employee: ", this::isValidDivision);
        if (division == null) return null;

        Double salary = getValidSalary();
        if (salary == null) return null;

        Employee.EmployeeBuilder builder = Employee.builder();
        builder.name(name);
        builder.ssn(ssn);
        builder.jobTitle(jobTitle);
        builder.division(division);
        builder.salary(salary);
        return builder.build();
    }

    public String getValidInput(String prompt, InputValidator validator) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase(CANCEL_COMMAND)) {
                return null;
            }
            if (validator.isValid(input)) {
                return input;
            }
            System.out.println("Invalid input. Please try again or enter '" + CANCEL_COMMAND + "' to cancel.");
        }
    }

    public String getValidSSN() {
        while (true) {
            System.out.print("Enter the social security number of the employee (9 digits, with or without dashes): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase(CANCEL_COMMAND)) {
                return null;
            }
            String ssn = input.replaceAll("[^0-9]", "");
            if (ssn.length() == 9) {
                return formatSSN(ssn);
            }
            System.out.println("Invalid SSN. Please enter 9 digits or enter '" + CANCEL_COMMAND + "' to cancel.");
        }
    }

    public Double getValidSalary() {
        while (true) {
            System.out.print("Enter the salary of the employee: ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase(CANCEL_COMMAND)) {
                return null;
            }
            try {
                double salary = Double.parseDouble(input);
                if (salary >= 0) {
                    return salary;
                }
                System.out.println("Salary must be non-negative. Please try again or enter '" + CANCEL_COMMAND + "' to cancel.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary format. Please enter a valid number or enter '" + CANCEL_COMMAND + "' to cancel.");
            }
        }
    }

    public String formatSSN(String ssn) {
        return ssn.substring(0, 3) + "-" + ssn.substring(3, 5) + "-" + ssn.substring(5);
    }

    public boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public boolean isValidJobTitle(String jobTitle) {
        return jobTitle != null && !jobTitle.trim().isEmpty();
    }

    public boolean isValidDivision(String division) {
        return division != null && !division.trim().isEmpty();
    }

    public int getValidYear() {
        String input = getValidInput("Enter year: ", this::isValidYear);
        if (input == null) return -1;
        return Integer.parseInt(input);

    }

    public int getValidMonth() {
        String input = getValidInput("Enter month (1-12): ", this::isValidMonth);
        if (input == null) return -1;
        return Integer.parseInt(input);
    }

    private boolean isValidYear(String input) {
        try {
            int year = Integer.parseInt(input);
            return year > 0; // Accepts any positive year
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidMonth(String input) {
        try {
            int month = Integer.parseInt(input);
            return month >= 1 && month <= 12;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
