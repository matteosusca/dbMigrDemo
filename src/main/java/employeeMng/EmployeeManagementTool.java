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

            System.out.println("Querying table...");
            sql = "SELECT id, name, surname, date_of_birth FROM person";
            rs = stmt.executeQuery(sql);
            List<Person> persons = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String dateOfBirth = rs.getString("date_of_birth");
                Person person = new Person(id, name, surname, dateOfBirth);
                persons.add(person);
            }
            sql = "SELECT id, salary, department FROM employee";
            rs = stmt.executeQuery(sql);
            List<Employee> employees = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                int salary = rs.getInt("salary");
                String department = rs.getString("department");
                Employee employee = new Employee(id, salary, department);
                employees.add(employee);                
            }
            //print employee names and salaries
            System.out.println("Printing employee names and salaries...");
            for (Person person : persons) {
                for (Employee employee : employees) {
                    if (person.id == employee.id) {
                        System.out.println(person.name + " " + person.surname + " " + employee.salary + " " + employee.department);
                    }
                }
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

    static class Person {
        int id;
        String name;
        String surname;
        String dateOfBirth;

        public Person(int id, String name, String surname, String dateOfBirth) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.dateOfBirth = dateOfBirth;
        }

        @Override
        public String toString() {
            return "Person{" +
                   "id=" + id +
                   ", name='" + name + '\'' +
                   ", surname='" + surname + '\'' +
                   ", dateOfBirth='" + dateOfBirth + '\'' +
                   '}';
        }
    }

    static class Employee {
        int id;
        int salary;
        String department;

        public Employee(int id, int salary, String department) {
            this.id = id;
            this.salary = salary;
            this.department = department;
        }

        @Override
        public String toString() {
            return "Employee{" +
                   "id=" + id +
                   ", salary=" + salary +
                   ", department='" + department + '\'' +
                   '}';
        }
    }
}
