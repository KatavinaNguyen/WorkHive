package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stub {
    private int eid;
    private double salary;
    private double deductions;
    private double grossPay;
    private double netPay;
    private double hours;
    @Builder.Default private Integer id = null;
    @Builder.Default private Date date = new Date();

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return String.format(
                """
                    Paystub Details:
                    ----------------
                    ID: %d
                    Employee ID: %d
                    Date: %s
                    Hours Worked: %.2f
                    Salary: $%.2f
                    Gross Pay: $%.2f
                    Deductions: $%.2f

                    Net Pay: $%.2f
                    \n
                """,
                id, eid, dateFormat.format(date), hours, salary, grossPay, deductions, netPay
        );
    }
}
