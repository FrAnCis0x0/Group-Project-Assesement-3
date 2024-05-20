package qpims.model;

public interface IUser {
    public void addUser(String firstName, String lastName, String email, String username, String password);
    public boolean userExists(String name); 
    public User getUserByUsernameAndPassword(String username, String password);
    public String getSha256Hash(String value);
}
