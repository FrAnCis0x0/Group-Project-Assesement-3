package qpims.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserModel implements IUser {
    private final Connection connection;
    // Prepared statements for queries
    private PreparedStatement insertUser;
    private PreparedStatement selectUserByUsernameAndPassword;
    private PreparedStatement selectUserByUsername;

    // Constructor to initialize the prepared statements for the queries
    public UserModel(Connection connection) {
        this.connection = connection;
        try {
            // Create a query to insert a new user into the user table
            insertUser = connection.prepareStatement("INSERT INTO user (first_name, last_name, email, username, password) VALUES (?, ?, ?, ?, ?)");
            // Create a query to select a user by username and password
            selectUserByUsernameAndPassword = connection.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
            // Create a query to select a user by username
            selectUserByUsername = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
        } catch (SQLException ex) {
            System.out.println("Database does not exist!!"); //log error message
        }
    }

    // Add a new user to the user table
    @Override
    public void addUser(String firstName, String lastName, String email, String username, String password) {
        try {
            // Insert a new entry into the user table
            insertUser.setString(1, firstName);
            insertUser.setString(2, lastName);
            insertUser.setString(3, email);
            insertUser.setString(4, username);
            insertUser.setString(5, getSha256Hash(password));
            insertUser.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, "Failed to add new user", ex);
        }
    }

    // Check if a user exists by username
    @Override
    public boolean userExists(String name) {
        try {
            selectUserByUsername.setString(1, name); //set the username to search for in the query
            ResultSet rs = selectUserByUsername.executeQuery(); //execute the query
            return rs.next(); //return true if user exists
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, "Failed to check if user exists", ex);
        }
        return false;
    }

    // Get a user by username and password
    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
    
        try {
            selectUserByUsernameAndPassword.setString(1, username); //set the username to search for in the query
            selectUserByUsernameAndPassword.setString(2, getSha256Hash(password)); //set the password to search for in the query
            ResultSet rs = selectUserByUsernameAndPassword.executeQuery();
            if (rs.next()) {
                //create a new user object and set the values from the result set
                User user = new User();
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, "Failed to get user by username and password", ex);
        }
        return null;
    }

    @Override
    public String getSha256Hash(String value) {
        //Generate sha256 hash
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //apply sha256
            byte[] hash = digest.digest(value.getBytes());
            //convert byte array to string
            StringBuilder hexString = new StringBuilder();
            //loop through byte array
            for (int i = 0; i < hash.length; i++) {
                //convert byte to hex
                String hex = Integer.toHexString(0xff & hash[i]);
                //append 0 to hex if it is less than 1 character
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                //append hex to string
                hexString.append(hex);
                
            }
            return hexString.toString().toUpperCase();
            
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, "Failed to generate hash", e);
        }
        return ""; //return empty string if hash generation fails
    }
    
}
