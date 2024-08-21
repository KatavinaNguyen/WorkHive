package dao;

import model.Stub;
import utils.PreparedStatementSetter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StubDAO {
    public Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:h2:./employees_db";
        String USER = "";
        String PASSWORD = "";
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public List<Stub> findByDivisionAndDate(String division, int year, int month) throws SQLException {
        String sql = "SELECT s.* FROM stubs s " +
                "JOIN employees e ON s.eid = e.id " +
                "WHERE e.division = ? " +
                "AND YEAR(s.date) = ? " +
                "AND MONTH(s.date) = ?";
        return executeQuery(sql, stmt -> {
            stmt.setString(1, division);
            stmt.setInt(2, year);
            stmt.setInt(3, month);
        });
    }

    public List<Stub> findByJobTitleAndDate(String jobTitle, int year, int month) throws SQLException {
        String sql = "SELECT s.* FROM stubs s " +
                "JOIN employees e ON s.eid = e.id " +
                "WHERE e.jobTitle = ? " +
                "AND YEAR(s.date) = ? " +
                "AND MONTH(s.date) = ?";
        return executeQuery(sql, stmt -> {
            stmt.setString(1, jobTitle);
            stmt.setInt(2, year);
            stmt.setInt(3, month);
        });
    }

//    public List<Stub> findAllEmployeeInfoWithStubs(int eid) throws SQLException {
//        String sql = "SELECT employees.*, stubs.* " +
//                "FROM employees " +
//                "JOIN stubs ON employees.id = stubs.eid " +
//                "WHERE employees.id = ?";
//        return executeQuery(sql, stmt -> {stmt.setInt(1, eid);});
//    }

    private void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS stubs (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "eid INT NOT NULL," +
                "salary DOUBLE NOT NULL," +
                "deductions DOUBLE NOT NULL," +
                "grossPay DOUBLE NOT NULL," +
                "netPay DOUBLE NOT NULL," +
                "hours DOUBLE NOT NULL," +
                "date DATE NOT NULL)";
        executeUpdate(sql);
    }

    public boolean create(Stub stub) throws SQLException {
        createTableIfNotExists();
        String sql = "INSERT INTO stubs (eid, salary, deductions, grossPay, netPay, hours, date) VALUES (?,?,?,?,?,?,?)";
        return executeUpdate(sql, stmt -> {
            stmt.setInt(1, stub.getEid());
            stmt.setDouble(2, stub.getSalary());
            stmt.setDouble(3, stub.getDeductions());
            stmt.setDouble(4, stub.getGrossPay());
            stmt.setDouble(5, stub.getNetPay());
            stmt.setDouble(6, stub.getHours());
            stmt.setDate(7, new java.sql.Date(stub.getDate().getTime()));
        }) > 0;
    }

    public Stub findById(int id) throws SQLException {
        String sql = "SELECT * FROM stubs WHERE id = ?";
        List<Stub> stubs = executeQuery(sql, stmt -> stmt.setInt(1, id));
        return stubs.isEmpty() ? null : stubs.getFirst();
    }

    public List<Stub> findByEmployeeId(int eid) throws SQLException {
        String sql = "SELECT * FROM stubs WHERE eid = ?";
        return executeQuery(sql, stmt -> stmt.setInt(1, eid));
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM stubs WHERE id = ?";
        return executeUpdate(sql, stmt -> stmt.setInt(1, id)) > 0;
    }

    private List<Stub> executeQuery(String sql, PreparedStatementSetter setter) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setter.setValues(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Stub> stubs = new ArrayList<>();
                while (rs.next()) {
                    stubs.add(createStubFromResultSet(rs));
                }
                return stubs;
            }
        }
    }

    private void executeUpdate(String sql) throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    private int executeUpdate(String sql, PreparedStatementSetter setter) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            setter.setValues(stmt);
            return stmt.executeUpdate();
        }
    }

    private Stub createStubFromResultSet(ResultSet rs) throws SQLException {
        return Stub.builder()
                .id(rs.getInt("id"))
                .eid(rs.getInt("eid"))
                .salary(rs.getDouble("salary"))
                .deductions(rs.getDouble("deductions"))
                .grossPay(rs.getDouble("grossPay"))
                .netPay(rs.getDouble("netPay"))
                .hours(rs.getDouble("hours"))
                .date(rs.getDate("date"))
                .build();
    }
}
