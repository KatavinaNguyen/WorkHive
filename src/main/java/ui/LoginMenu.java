package ui;

import lombok.RequiredArgsConstructor;
import utils.InputHandler;

import java.util.Arrays;

@RequiredArgsConstructor
public class LoginMenu implements Menu {
    private final InputHandler inputHandler;
    private final Menu adminMenu;
    private final Menu stakeholderMenu;

    @Override
    public void display() {
        for (String s : Arrays.asList("Welcome to Employee Management System",
                "1. Administrator",
                "2. Stakeholder",
                "3. Exit")) {
            System.out.println(s);
        }
        System.out.print("Select user type: ");

        int choice = inputHandler.getOption();

        switch (choice) {
            case 1:
                adminMenu.display();
                break;
            case 2:
                stakeholderMenu.display();
                break;
            case 3:
                System.out.println("Exiting the application. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
