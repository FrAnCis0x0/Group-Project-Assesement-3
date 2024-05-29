package qpims.model;

// Interface for User class that defines the methods that will be implemented in the User class
public interface IUser {
    public void addUser(String firstName, String lastName, String email, String username, String password); //add a new user to user table
    public boolean userExists(String name);  //check if a user exists
    public User getUserByUsernameAndPassword(String username, String password); //get a user by username and password
    public String getSha256Hash(String value); //get the SHA-256 hash of a string value
}
