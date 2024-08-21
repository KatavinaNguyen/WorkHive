package ui;

import lombok.RequiredArgsConstructor;
import service.EmployeeManagementService;
import service.EmployeeReportingService;
import utils.InputHandler;

import java.util.Scanner;

@RequiredArgsConstructor
public class ConsoleUI {
    private final Menu loginMenu;

    public void startApplication() {
        loginMenu.display();
    }

    public static ConsoleUI getConsoleUI(EmployeeManagementService employeeManagementService, EmployeeReportingService employeeReportingService) {
        Scanner scanner = new Scanner(System.in);
        InputHandler inputHandler = new InputHandler(scanner, employeeManagementService);

        EmployeeMenu employeeMenu = new EmployeeMenu(inputHandler, employeeManagementService);
        ReportMenu reportMenu = new ReportMenu(inputHandler, employeeReportingService);
        Menu adminMenu = new AdminMenu(employeeMenu);
        Menu stakeHolderMenu = new StakeholderMenu(inputHandler, employeeMenu, reportMenu);


        Menu loginMenu = new LoginMenu(inputHandler, adminMenu, stakeHolderMenu);

        return new ConsoleUI(loginMenu);
    }
}
