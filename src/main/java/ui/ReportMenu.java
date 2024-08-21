package ui;

import lombok.RequiredArgsConstructor;
import model.Stub;
import service.EmployeeReportingService;
import utils.InputHandler;

import java.sql.SQLException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
public class ReportMenu implements Menu {
    private final InputHandler inputHandler;
    private final EmployeeReportingService reportingService;

    @Override
    public void display() {
        while (true) {
            System.out.println("Report Menu");
            for (String s : Arrays.asList(
                    "WorkHive Employee Management System",
                    "1. Generate Employee Report",
                    "2. Generate Monthly Division Report",
                    "3. Generate Monthly Job Title Report",
                    "4. Return to Main Menu")) {
                System.out.println(s);
            }
            System.out.print("Select an option: ");

            int choice = inputHandler.getOption();

            switch (choice) {
                case 1:
                    employeeInfoWithPayHistReport();
                    break;
                case 2:
                    monthlyDivisionReport();
                    break;
                case 3:
                    monthlyJobTitleReport();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void employeeInfoWithPayHistReport(){
        System.out.println("Employee Info Report (Enter '" + inputHandler.CANCEL_COMMAND + "' at any prompt to cancel)");
        try {
            int eid = inputHandler.getEmployeeIdFromUser();

            List<Stub> stubs = reportingService.getEmployeeInfoWithPayHistReport(eid);

            if (stubs.isEmpty()){
                System.out.println("No stubs found.");
                return;
            }

            printReportInfo(stubs);

        } catch (SQLException e){
            System.out.println("Error generating report: " + e.getMessage());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void monthlyJobTitleReport() {
        System.out.println("Monthly Job Title Report (Enter '" + inputHandler.CANCEL_COMMAND + "' at any prompt to cancel)");
        try {
            String jobTitle = inputHandler.getValidInput("Enter Job Title: ", inputHandler::isValidJobTitle);
            if (jobTitle == null) return;

            int year = inputHandler.getValidYear();
            if (year == -1) return;

            int month = inputHandler.getValidMonth();
            if (month == -1) return;

            List <Stub> stubs = reportingService.getStubsByJobTitleAndDate(jobTitle, year, month);
            if (stubs.isEmpty()){
                System.out.println("No stubs found for job title " + jobTitle + "and date provided");
                return;
            }

            double totalGrossPay = reportingService.calculateGrossPay(stubs);
            double totalNetPay = reportingService.calculateNetPay(stubs);

            printReportJobTitle(jobTitle, year, month, stubs, totalGrossPay, totalNetPay);

        } catch (SQLException e) {
            System.out.println("Error generating report: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void monthlyDivisionReport() {
        System.out.println("Monthly Division Report (Enter '" + inputHandler.CANCEL_COMMAND + "' at any prompt to cancel)");
        try {
            String division = inputHandler.getValidInput("Enter division: ", inputHandler::isValidDivision);
            if (division == null) return;

            int year = inputHandler.getValidYear();
            if (year == -1) return;

            int month = inputHandler.getValidMonth();
            if (month == -1) return;

            List<Stub> stubs = reportingService.getStubsByDivisionAndDate(division, year, month);
            if (stubs.isEmpty()) {
                System.out.println("No data found for the specified division and date range.");
                return;
            }

            double totalGrossPay = reportingService.calculateGrossPay(stubs);
            double totalNetPay = reportingService.calculateNetPay(stubs);

            printReport(division, year, month, stubs, totalGrossPay, totalNetPay);

        } catch (SQLException e) {
            System.out.println("Error generating report: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void printReport(String division, int year, int month, List<Stub> stubs, double totalGrossPay, double totalNetPay) {
        String monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault());
        System.out.println("\n====================================================");
        System.out.printf("     Monthly Division Report - %s %d%n", monthName, year);
        System.out.println("====================================================");
        System.out.printf("Division: %s%n", division);
        System.out.printf("Total Employees: %d%n", stubs.size()); // Need sleep... Let's just pretend every employee can only have 1 stub
        System.out.printf("Total Gross Pay: $%.2f%n", totalGrossPay);
        System.out.printf("Total Net Pay: $%.2f%n", totalNetPay);
        System.out.println("====================================================");
    }

    private void printReportJobTitle(String jobTitle, int year, int month, List<Stub> stubs, double totalGrossPay, double totalNetPay) {
        String monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault());
        System.out.println("\n====================================================");
        System.out.printf("     Monthly Job Title Report - %s %d%n", monthName, year);
        System.out.println("====================================================");
        System.out.printf("Job Title: %s%n", jobTitle);
        System.out.printf("Total Employees: %d%n", stubs.size());
        System.out.printf("Total Gross Pay: $%.2f%n", totalGrossPay);
        System.out.printf("Total Net Pay: $%.2f%n", totalNetPay);
        System.out.println("====================================================");
    }

    private void printReportInfo(List<Stub> stubs) {
        System.out.println("\n===================================================");
        System.out.println("     Employee with Stubs Info    ");
        System.out.println("===================================================");
        for (Stub stub : stubs) {
            System.out.println(stub.toString());
        }
    }
}
