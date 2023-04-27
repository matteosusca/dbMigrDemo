package com.example;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagementTool {
    // Connects to Firebird using the Firebird embedded driver
    private static final String DB_URL = "jdbc:firebirdsql:embedded:%s";

    // Database credentials
    private static final String USER = "sysdba";
    private static final String PASS = "masterkey";

    private static final String DATABASE_NAME = "employee.fdb";

    public static void main(String[] args) {

        // Check for the existence of the database file
        if (!new File(DATABASE_NAME).exists()) {
            System.out.println("Database file does not exist, exiting...");
            return;
        }

        try (Connection conn = DriverManager.getConnection(String.format(DB_URL, DATABASE_NAME), USER, PASS);
             Statement stmt = conn.createStatement()) {
            // Check if table exists
            String sql = "SELECT COUNT(*) FROM RDB$RELATIONS WHERE RDB$RELATION_NAME = 'EMPLOYEE'";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                rs.next();
                if (rs.getInt(1) == 0) {
                    // Table does not exist
                    System.out.println("Table does not exist, exiting...");
                    return;
                }
            }

            // Query the database for employees and their information
            List<Person> persons = new ArrayList<>();
            sql = "SELECT id, name, surname, date_of_birth FROM person";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String dateOfBirth = rs.getString("date_of_birth");
                    Person person = new Person(id, name, surname, dateOfBirth);
                    persons.add(person);
                }
            }
            List<Employee> employees = new ArrayList<>();
            sql = "SELECT id, salary, department FROM employee";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int salary = rs.getInt("salary");
                    String department = rs.getString("department");
                    Employee employee = new Employee(id, salary, department);
                    employees.add(employee);
                }
            }

            // Print employee names and salaries
            System.out.println("Printing employee names and salaries...");
            for (Person person : persons) {
                for (Employee employee : employees) {
                    if (person.id == employee.id) {
                        System.out.println(person.name + " " + person.surname + " " + employee.salary + " " + employee.department);
                    }
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    // Class representing a person
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
