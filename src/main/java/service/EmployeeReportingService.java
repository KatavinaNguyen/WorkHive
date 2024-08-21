package service;

import dao.StubDAO;
import lombok.RequiredArgsConstructor;
import model.Stub;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class EmployeeReportingService {
    private final StubDAO stubDAO;

    public List<Stub> getStubsByDivisionAndDate(String division, int year, int month) throws SQLException {
        return stubDAO.findByDivisionAndDate(division, year, month);
    }

    public double calculateGrossPay(List<Stub> stubs) {
        return stubs.stream().mapToDouble(Stub::getGrossPay).sum();
    }

    public double calculateNetPay(List<Stub> stubs) {
        return stubs.stream().mapToDouble(Stub::getNetPay).sum();
    }

    public List<Stub> getStubsByJobTitleAndDate(String jobTitle, int year, int month) throws SQLException{
        return stubDAO.findByJobTitleAndDate(jobTitle, year, month);
    }

    public List<Stub> getEmployeeInfoWithPayHistReport(int eid) throws SQLException{
        return stubDAO.findByEmployeeId(eid);
    }
}
