package dao;

import model.Employee;
import utils.PreparedStatementSetter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:h2:./employees_db";
        String USER = "";
        String PASSWORD = "";
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    private void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS employees (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "ssn VARCHAR(11) NOT NULL," +
                "jobTitle VARCHAR(255) NOT NULL," +
                "division VARCHAR(255) NOT NULL," +
                "salary DOUBLE NOT NULL)";
        executeUpdate(sql);
    }

    public boolean create(Employee employee) throws SQLException {
        createTableIfNotExists();
        String sql = "INSERT INTO employees (name, ssn, jobTitle, division, salary) VALUES (?,?,?,?,?)";
        return executeUpdate(sql, stmt -> {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getSsn());
            stmt.setString(3, employee.getJobTitle());
            stmt.setString(4, employee.getDivision());
            stmt.setDouble(5, employee.getSalary());
        }) > 0;
    }

    public Employee findById(int id) throws SQLException {
        String sql = "SELECT * FROM employees WHERE id = ?";
        List<Employee> results = executeQuery(sql, stmt -> stmt.setInt(1, id));
        return results.isEmpty() ? null : results.getFirst();
    }

    public List<Employee> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM employees WHERE name = ?";
        return executeQuery(sql, stmt -> stmt.setString(1, name));
    }

    public List<Employee> findBySSN(String ssn) throws SQLException {
        String sql = "SELECT * FROM employees WHERE ssn = ?";
        return executeQuery(sql, stmt -> stmt.setString(1, ssn));
    }

    public List<Employee> findByJobTitle(String jobTitle) throws SQLException {
        String sql = "SELECT * FROM employees WHERE jobTitle = ?";
        return executeQuery(sql, stmt -> stmt.setString(1, jobTitle));
    }

    public List<Employee> findByDivision(String division) throws SQLException {
        String sql = "SELECT * FROM employees WHERE division = ?";
        return executeQuery(sql, stmt -> stmt.setString(1, division));
    }

    public List<Employee> findAll() throws SQLException {
        String sql = "SELECT * FROM employees";
        return executeQuery(sql, stmt -> {});
    }

    public boolean update(Integer id, Employee employee) throws SQLException {
        String sql = "UPDATE employees SET name = ?, ssn = ?, jobTitle = ?, division = ?, salary = ? WHERE id = ?";
        return executeUpdate(sql, stmt -> {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getSsn());
            stmt.setString(3, employee.getJobTitle());
            stmt.setString(4, employee.getDivision());
            stmt.setDouble(5, employee.getSalary());
            stmt.setInt(6, id);
        }) > 0;

    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        return executeUpdate(sql, stmt -> stmt.setInt(1, id)) > 0;
    }

    private List<Employee> executeQuery(String sql, PreparedStatementSetter setter) throws SQLException {
       Connection conn = getConnection();
       PreparedStatement stmt = conn.prepareStatement(sql);
       setter.setValues(stmt);
       ResultSet rs = stmt.executeQuery();
       List<Employee> employees = new ArrayList<>();
       while (rs.next()) {
           employees.add(createEmployeeFromResultSet(rs));
       }
       return employees;
    }

    private void executeUpdate(String sql) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }

    private int executeUpdate(String sql, PreparedStatementSetter setter) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        setter.setValues(stmt);
        return stmt.executeUpdate();
    }

    private Employee createEmployeeFromResultSet(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setSsn(rs.getString("ssn"));
        employee.setJobTitle(rs.getString("jobTitle"));
        employee.setDivision(rs.getString("division"));
        employee.setSalary(rs.getDouble("salary"));
        return employee;
    }
}
