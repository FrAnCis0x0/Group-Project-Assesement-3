package qpims.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerModel implements ICustomer {
    private Connection connection;
    private PreparedStatement insertCustomer;
    private PreparedStatement selectCustomerByNameOrPhone;
    private PreparedStatement getAllCustomers;
    private PreparedStatement deleteCustomerById;
    private PreparedStatement updateCustomer;
    

    public CustomerModel(Connection connection) {
        this.connection = connection;
        try {
            insertCustomer = connection.prepareStatement("INSERT INTO customer (first_name, last_name, address, phone) VALUES (?, ?, ?, ?)");
            selectCustomerByNameOrPhone = connection.prepareStatement("SELECT * FROM customer WHERE first_name LIKE ? OR last_name LIKE ? OR phone LIKE ?");
            getAllCustomers = connection.prepareStatement("SELECT * FROM customer");
            deleteCustomerById = connection.prepareStatement("DELETE FROM customer WHERE customer_id = ?");
            updateCustomer = connection.prepareStatement("UPDATE customer SET first_name = ?, last_name = ?, address = ?, phone = ? WHERE customer_id = ?");
        } catch (SQLException ex) {
            Logger.getLogger(BookingModel.class.getName()).log(Level.SEVERE, "Database does not exist!!", ex);
    
        }
    }
    @Override
    public void addCustomer(String firstName, String lastName, String address, String phone) {
        try {
            insertCustomer.setString(1, firstName);
            insertCustomer.setString(2, lastName);
            insertCustomer.setString(3, address);
            insertCustomer.setString(4, phone);
            insertCustomer.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, "Failed to add new customer", ex);
        }
    }

    @Override
    public List<Customer> searchCustomerByNameOrPhone(String value) {
        try {
            selectCustomerByNameOrPhone.setString(1, "%" + value + "%");
            selectCustomerByNameOrPhone.setString(2, "%" + value + "%");
            selectCustomerByNameOrPhone.setString(3, "%" + value + "%");
            ResultSet rs = selectCustomerByNameOrPhone.executeQuery();
            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setAddress(rs.getString("address"));
                customer.setPhone(rs.getString("phone"));
                customers.add(customer);
            }
            return customers;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, "Failed to search customer", ex);
        }
        return null;
    }

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
                customer.setAddress(rs.getString("address"));
                customer.setPhone(rs.getString("phone"));
                customers.add(customer);
            }
            return customers;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, "Failed to get all customers", ex);
        }
        return null;
    }

    @Override
    public void deleteCustomerById(int customerId) {
        try {
            deleteCustomerById.setInt(1, customerId);
            deleteCustomerById.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, "Failed to delete customer", ex);
        }
    }

    @Override
    public void updateCustomer(int customerId, String firstName, String lastName, String address, String phone) {
        try {
            updateCustomer.setString(1, firstName);
            updateCustomer.setString(2, lastName);
            updateCustomer.setString(3, address);
            updateCustomer.setString(4, phone);
            updateCustomer.setInt(5, customerId);
            updateCustomer.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, "Failed to update customer", ex);
        }
    }
    
}
