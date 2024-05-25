package qpims.model;

import javafx.application.Platform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    //create a connection to the database using singleton pattern
    private static DatabaseConnection instance = null;
    private String connectionStatus;
    private Connection connection;
    private static String user;
    private static String password;
    private static String url;
    private DatabaseConnection() {
        //connect to the database
        url = "jdbc:mysql://localhost/qpimsdb";
        user = "admin";
        password = "Str0ngP@ssw0rd!!";//this is a weak password :)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                setConnectionStatus("Online");
            }
        } catch (ClassNotFoundException | SQLException e) {
            setConnectionStatus("Offline");
            MessageBox.getInstance().showError(e.getMessage());
        }
    }
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    public Connection getConnection() {
        //get the connection
        return connection;
    }
    public String getConnectionStatus() {
        //get the connection status
        return connectionStatus;
    }
    private void setConnectionStatus(String connectionStatus) {
        //set the connection status
        this.connectionStatus = connectionStatus;
    }

}
