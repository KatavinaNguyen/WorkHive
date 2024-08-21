package ui;

import lombok.RequiredArgsConstructor;
import utils.InputHandler;

import java.util.Arrays;

@RequiredArgsConstructor
public class StakeholderMenu implements Menu {
    private final InputHandler inputHandler;
    private final EmployeeMenu employeeMenu;
    private final ReportMenu reportMenu;

    @Override
    public void display() {
        while (true) {
            for (String s : Arrays.asList("1. Search Employee",
                    "2. Generate Reports",
                    "3. Exit")) {
                System.out.println(s);
            }
            System.out.print("Select an option: ");


            int choice = inputHandler.getOption();

            switch (choice) {
                case 1:
                    employeeMenu.searchMenu();
                    break;
                case 2:
                    reportMenu.display();
                    break;
                case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
