package qpims.model;

import java.util.List;

// Interface for Customer class that defines the methods that will be implemented in the Customer class
public interface ICustomer {
    public void addCustomer(String firstName, String lastName, String email, String phone); //add a new customer to customer table
    public List<Customer> searchCustomerByNameOrPhone(String value); //search for a customer by name or phone number
    public List<Customer> getAllCustomers(); //return a list of all customers in the customer table
    public void deleteCustomerById(int customerId); //delete a customer by customer id from the customer table
    public void updateCustomer(int customerId, String firstName, String lastName, String email, String phone); //update a customer's information in the customer table by customer id

}
