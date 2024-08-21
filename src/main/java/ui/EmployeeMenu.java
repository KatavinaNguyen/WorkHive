package ui;

import lombok.RequiredArgsConstructor;
import model.Employee;
import service.EmployeeManagementService;
import utils.InputHandler;

import java.sql.SQLException;
import java.util.Arrays;

@RequiredArgsConstructor
public class EmployeeMenu implements Menu {
    private final InputHandler inputHandler;
    private final EmployeeManagementService managementService;

    @Override
    public void display() {
        while (true) {
            for (String s : Arrays.asList("1. Create Employee",
                    "2. Search Employee",
                    "3. Update Employee",
                    "4. Delete Employee",
                    "5. Exit")) {
                System.out.println(s);
            }
            System.out.print("Select an option: ");

            int choice = inputHandler.getOption();

            switch (choice) {
                case 1:
                    createMenu();
                    break;
                case 2:
                    searchMenu();
                    break;
                case 3:
                    updateMenu();
                    break;
                case 4:
                    deleteMenu();
                    break;
                case 5:
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void createMenu() {
        System.out.println("Create Employee (Enter '" + inputHandler.CANCEL_COMMAND + "' at any prompt to cancel)");
        try {
            Employee employee = inputHandler.getEmployeeDetailsFromUser();
            if (employee != null) {
                boolean created = managementService.createEmployee(employee);
                if (created) {
                    System.out.println("Employee created successfully!");
                } else {
                    System.out.println("Failed to create employee. Please try again.");
                }
            } else {
                System.out.println("Employee creation cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating employee: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void searchMenu() {
        System.out.println("Search Employee (Enter '" + inputHandler.CANCEL_COMMAND + "' at any prompt to cancel)");

        while (true) {
            System.out.println("1. Get employee by ID");
            System.out.println("2. Get all employees");
            System.out.println("3. Get employees by Name");
            System.out.println("4. Get employee by SSN");
            System.out.println("5. Get employees by Job Title");
            System.out.println("6. Get employees by Division");
            System.out.println("7. Return to main menu");
            System.out.print("Select an option: ");

            int choice = inputHandler.getOption();

            try {
                switch (choice) {
                    case 1:
                        inputHandler.searchById();
                        break;
                    case 2:
                        inputHandler.searchAllEmployees();
                        break;
                    case 3:
                        inputHandler.searchByName();
                        break;
                    case 4:
                        inputHandler.searchBySSN();
                        break;
                    case 5:
                        inputHandler.searchByJobTitle();
                        break;
                    case 6:
                        inputHandler.searchByDivision();
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("Error searching for employee(s): " + e.getMessage());
            }
        }
    }

    public void updateMenu() {
        System.out.println("Update Employee (Enter '" + inputHandler.CANCEL_COMMAND + "' at any prompt to cancel)");

        Integer id = inputHandler.getEmployeeIdFromUser();
        if (id == null) return;

        try {
            Employee employee = managementService.findEmployee(id);
            if (employee == null) {
                System.out.println("No employee found with ID: " + id);
                return;
            }

            while (true) {
                System.out.println("=== Editing " + employee.getName() + " details ===");
                System.out.println("1. Update Name");
                System.out.println("2. Update SSN");
                System.out.println("3. Update Job Title");
                System.out.println("4. Update Division");
                System.out.println("5. Update Salary");
                System.out.println("6. Save and return to main menu");
                System.out.println("7. Return to main menu");
                System.out.print("Select an option: ");

                int choice = inputHandler.getOption();

                switch (choice) {
                    case 1:
                        inputHandler.updateName(employee);
                        break;
                    case 2:
                        inputHandler.updateSSN(employee);
                        break;
                    case 3:
                        inputHandler.updateJobTitle(employee);
                        break;
                    case 4:
                        inputHandler.updateDivision(employee);
                        break;
                    case 5:
                        inputHandler.updateSalary(employee);
                        break;
                    case 6:
                        inputHandler.saveUpdatedEmployee(id, employee);
                        return;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }

    public void deleteMenu() {
        System.out.println("Remove Employee (Enter '" + inputHandler.CANCEL_COMMAND + "' at any prompt to cancel)");
        try {
            Integer eid = inputHandler.getEmployeeIdFromUser();
            if (eid != null) {
                boolean created = managementService.deleteEmployee(eid);
                if (created) {
                    System.out.println("Employee " + eid + " removed successfully!");
                } else {
                    System.out.println("Employee does not exist. Please try again.");
                }
            } else {
                System.out.println("Employee removal cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing employee: " + e.getMessage());
        }
    }
}

