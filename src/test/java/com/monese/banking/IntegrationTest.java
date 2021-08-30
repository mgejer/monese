package com.monese.banking;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class IntegrationTest {

    private static Connection connection;

    @BeforeAll
    public static void setupDB() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:memory");
    }

    @Test
    public void testCreateDB() throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute("CREATE TABLE `Account` ( `id` INTEGER, `balance` REAL )");
        statement.execute("Insert into `Account` values(123, 567)");
        ResultSet resultSet = statement.executeQuery("select * from `Account`");

        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            double balance = resultSet.getDouble("balance");
            System.out.println(id + " " +  balance);
        }
    }
}
