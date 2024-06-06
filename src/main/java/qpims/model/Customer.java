package qpims.model;

public class Customer {
    // Variables for the customer class
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    
    private String associatedAddress;

    // Default constructor
    public Customer() {}
    // Constructor to initialize variables
    public Customer(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    // Getters and setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setAssociatedAddress(String associatedAddress){
        this.associatedAddress = associatedAddress;
    }
    public String getAssociatedAddress(){
        return associatedAddress;
    }

    // toString method to return customer ID as a string for display purposes in the table view
    @Override
    public String toString() {
        return "Customer ID: " + getCustomerId();
    }

}
