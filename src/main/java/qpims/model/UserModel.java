package qpims.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserModel implements IUser {
    private final Connection connection;
    private PreparedStatement insertUser;
    private PreparedStatement selectUserByUsernameAndPassword;
    private PreparedStatement selectUserByUsername;
    
    
    
    public UserModel(Connection connection) {
        this.connection = connection;
        try {
            insertUser = connection.prepareStatement("INSERT INTO user (first_name, last_name, email, username, password) VALUES (?, ?, ?, ?, ?)");
            selectUserByUsernameAndPassword = connection.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
            selectUserByUsername = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
        } catch (SQLException ex) {
            System.out.println("Database does not exist!!");
        }
    }
    @Override
    public void addUser(String firstName, String lastName, String email, String username, String password) {
        try {
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

    @Override
    public boolean userExists(String name) {
        try {
            selectUserByUsername.setString(1, name);
            ResultSet rs = selectUserByUsername.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, "Failed to check if user exists", ex);
        }
        return false;
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
    
        try {
            selectUserByUsernameAndPassword.setString(1, username);
            selectUserByUsernameAndPassword.setString(2, getSha256Hash(password));
            ResultSet rs = selectUserByUsernameAndPassword.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
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
        return "";
    }
    
}
