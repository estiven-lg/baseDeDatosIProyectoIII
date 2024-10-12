package model;

import controller.SqlQueryExecutor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlConnection {

    private static MySqlConnection instance;
    private Connection connection;
    private Statement statement;
    private String username = "root";
    private String password = "1234";
    private String host = "localhost:3306";
    private String database = "renap";

    String connectionUrl = String.format(
            "jdbc:mysql://%s/%s?user=%s&password=%s",
            host, database, username, password
    );

    private MySqlConnection() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(connectionUrl);
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public static SqlQueryExecutor execute() throws SQLException {
        if (instance == null) {
            instance = new MySqlConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new MySqlConnection();
        }
        return new SqlQueryExecutor(instance.statement);
    }

    private Connection getConnection() {
        return connection;
    }

}
