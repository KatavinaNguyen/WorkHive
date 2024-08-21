package service;

import dao.EmployeeDAO;
import lombok.RequiredArgsConstructor;
import model.Employee;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class EmployeeManagementService {
    private final EmployeeDAO employeeDAO;

    public boolean createEmployee(Employee employee) throws Exception {
        return employeeDAO.create(employee);
    }

    public boolean deleteEmployee(int id) throws SQLException {
        return employeeDAO.delete(id);
    }

    public Employee findEmployee(int id) throws SQLException {
        return employeeDAO.findById(id);
    }

    public List<Employee> findEmployeeBySSN(String ssn) throws SQLException {
        return employeeDAO.findBySSN(ssn);
    }

    public List<Employee> findAllEmployees() throws SQLException {
        return employeeDAO.findAll();
    }

    public List<Employee>  findAllEmployeesByName(String name) throws SQLException {
        return employeeDAO.findByName(name);
    }

    public List<Employee>  findAllEmployeesByDivision(String division) throws SQLException {
        return employeeDAO.findByDivision(division);
    }

    public List<Employee>  findAllEmployeesByJobTitle(String jobTitle) throws SQLException {
        return employeeDAO.findByJobTitle(jobTitle);
    }

    public boolean updateEmployee(int id, Employee employee) throws SQLException {
        return employeeDAO.update(id, employee);
    }
}
