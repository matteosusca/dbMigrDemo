package com.example;

import java.sql.*;

public class FirebirdDatabaseExplorer {
    private static final String DB_URL = "jdbc:firebirdsql:embedded:employee.fdb";
    private static final String USER = "sysdba";
    private static final String PASSWORD = "masterkey";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tableResultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});

            while (tableResultSet.next()) {
                String tableName = tableResultSet.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);
                System.out.println("----------");

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM \"" + tableName + "\"");

                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();

                // Print column names
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSetMetaData.getColumnName(i) + "\t");
                }
                System.out.println();

                // Print rows
                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(resultSet.getString(i) + "\t");
                    }
                    System.out.println();
                }

                System.out.println();
                resultSet.close();
                statement.close();
            }
            tableResultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
