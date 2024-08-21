import dao.EmployeeDAO;
import dao.StubDAO;
import service.EmployeeManagementService;
import service.EmployeeReportingService;
import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        StubDAO stubDAO = new StubDAO();
        EmployeeManagementService employeeManagementService = new EmployeeManagementService(employeeDAO);
        EmployeeReportingService employeeReportingService = new EmployeeReportingService(stubDAO);
        ConsoleUI ui = ConsoleUI.getConsoleUI(employeeManagementService, employeeReportingService);
        ui.startApplication();
    }
}