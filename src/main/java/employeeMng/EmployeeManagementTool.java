package employeeMng;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagementTool {
    // JDBC driver and database URL
    static final String JDBC_DRIVER = "org.firebirdsql.jdbc.FBDriver";
    static final String DB_URL = "jdbc:firebirdsql://localhost:3050/C:/Program Files/Firebird/Firebird_4_0/employee.fdb";

    // Database credentials
    static final String USER = "sysdba";
    static final String PASS = "masterkey";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            
            //check if table exists
            String sql = "SELECT COUNT(*) FROM RDB$RELATIONS WHERE RDB$RELATION_NAME = 'EMPLOYEE'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();

            if (rs.getInt(1) == 0) {
                //create table
                System.out.println("table does not exist, exiting...");
                return;                
            }

            //clear table
            
            System.out.println("Querying table...");
            sql = "SELECT id, name, age, salary FROM employee";
            rs = stmt.executeQuery(sql);
            List<Employee> employees = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                int salary = rs.getInt("salary");
                Employee employee = new Employee(id, name, age, salary);
                employees.add(employee);
            }
            System.out.println("Employees:");
            for (Employee employee : employees) {
                System.out.println(employee);
            }

            // Clean up
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                // nothing we can do
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    static class Employee {
        int id;
        String name;
        int age;
        int salary;

        public Employee(int id, String name, int age, int salary) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Employee{" +
                   "id=" + id +
                   ", name='" + name + '\'' +
                   ", age=" + age + ", salary=" + salary + '}';
        }
    }
}
