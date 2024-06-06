package qpims.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// Class to handle customer data in the database
public class CustomerModel implements ICustomer {
    private Connection connection = null;
    private PreparedStatement insertCustomer;
    private PreparedStatement selectCustomerByNameOrPhone;
    private PreparedStatement getAllCustomers;
    private PreparedStatement deleteCustomerById;
    private PreparedStatement updateCustomer;

    // Constructor to initialize the prepared statements for the queries
    public CustomerModel(Connection connection) {
        this.connection = connection;
        try {
            // Create a query to insert a new customer into the customer table
            insertCustomer = connection.prepareStatement("INSERT INTO customer (first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?)");
            // Create a query to search for a customer by name or phone number
            selectCustomerByNameOrPhone = connection.prepareStatement("Select customer.customer_id, customer.first_name, customer.last_name, customer.email, customer.phone_number, property.address\n" +
                    "from customer inner join property on customer.customer_id = property.customer_id WHERE first_name LIKE ? OR last_name LIKE ? OR phone_number LIKE ?");
            // Create a query to get all customers from the customer table
            
            getAllCustomers = connection.prepareStatement("SELECT customer.customer_id, customer.first_name, customer.last_name, customer.phone_number, customer.email, property.address\n" +
                    "From customer join property on customer.customer_id = property.customer_id;");
            // Create a query that deletes a customer by customer id from the customer table.
            deleteCustomerById = connection.prepareStatement("DELETE FROM customer WHERE customer_id = ?");
            // Create a query that updates a customer's information in customer table by customer id
            updateCustomer = connection.prepareStatement("UPDATE customer SET first_name = ?, last_name = ?, email = ?, phone_number = ? WHERE customer_id = ?");
        } catch (SQLException ex) {
            Logger.getLogger(BookingModel.class.getName()).log(Level.SEVERE, "Database does not exist!!", ex); //log error message
    
        }
    }
    // add a new customer to customer table
    @Override
    public void addCustomer(String firstName, String lastName, String email, String phone) {
        try {
            // Insert a new entry into the customer table
            insertCustomer.setString(1, firstName);
            insertCustomer.setString(2, lastName);
            insertCustomer.setString(3, email);
            insertCustomer.setString(4, phone);
            insertCustomer.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, "Failed to add new customer", ex);
        }
    }

    // search for a customer by name or phone number
    @Override
    public List<Customer> searchCustomerByNameOrPhone(String value) {
        try {
            selectCustomerByNameOrPhone.setString(1, "%" + value + "%");
            selectCustomerByNameOrPhone.setString(2, "%" + value + "%");
            selectCustomerByNameOrPhone.setString(3, "%" + value + "%");
            ResultSet rs = selectCustomerByNameOrPhone.executeQuery(); //execute the query
            List<Customer> customers = new ArrayList<>(); //create a list to store the result set

            //loop through the result set and add each entry to the list
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone_number"));
                customer.setAssociatedAddress(rs.getString("address"));
                customers.add(customer);
            }
            return customers;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, "Failed to search customer", ex);
        }
        return null; //return null if the search fails
    }

    // return a list of all customers in the customer table
    @Override
    public List<Customer> getAllCustomers() {
        try {
            ResultSet rs = getAllCustomers.executeQuery();
            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone_number"));
                customer.setAssociatedAddress(rs.getString("address"));
                customers.add(customer);
            }
            return customers;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, "Failed to get all customers", ex);
        }
        return null;
    }

    // delete a customer by customer id from the customer table
    @Override
    public void deleteCustomerById(int customerId) {
        try {
            deleteCustomerById.setInt(1, customerId); //set the customer id in the query
            deleteCustomerById.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, "Failed to delete customer", ex);
        }
    }

    // update a customer's information in the customer table by customer id
    @Override
    public void updateCustomer(int customerId, String firstName, String lastName, String email, String phone) {
        try {
            updateCustomer.setString(1, firstName);
            updateCustomer.setString(2, lastName);
            updateCustomer.setString(3, email);
            updateCustomer.setString(4, phone);
            updateCustomer.setInt(5, customerId);
            updateCustomer.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, "Failed to update customer", ex);
        }
    }
    
}
