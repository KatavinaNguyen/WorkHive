package ui;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminMenu implements Menu {
    private final EmployeeMenu employeeMenu;

    @Override
    public void display() {
        System.out.println("Welcome, Administrator");
        employeeMenu.display();
    }
}
