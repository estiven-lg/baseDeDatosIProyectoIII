package model;

import controller.SqlQueryExecutor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgressConnection {

    private static PostgressConnection instance;
    private Connection connection;
    private Statement statement;

    private String username = "root";
    private String password = "1234";
    private String host = "localhost:5433";
    private String database = "renap";

    String connectionUrl = String.format(
            "jdbc:postgresql://%s/%s",
            host, database
    );

    private PostgressConnection() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(connectionUrl, username, password);
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public static SqlQueryExecutor execute() throws SQLException {
        if (instance == null) {
            instance = new PostgressConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new PostgressConnection();
        }
        return new SqlQueryExecutor(instance.statement);
    }

    private Connection getConnection() {
        return connection;
    }

}
