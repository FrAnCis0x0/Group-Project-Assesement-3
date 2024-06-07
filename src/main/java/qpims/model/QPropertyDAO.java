package qpims.model;

import java.sql.Connection;
import java.util.List;

/**
 * Qproperty DAO class implements the interfaces ICustomer, IProperty, IBooking, IUser
 * It is responsible for handling the CRUD operations for the Customer, Property, Booking and User entities
 * It uses the CustomerModel, PropertyModel, BookingModel and UserModel classes to interact with the database
 * It uses the DatabaseConnection class to establish a connection to the database
 **/
public class QPropertyDAO implements ICustomer, IProperty, IBooking, IUser {
    private static QPropertyDAO instance;
    private CustomerModel customerModel;
    private UserModel userModel;
    private PropertyModel propertyModel;
    private BookingModel bookingModel;
    private final Connection dbConnection;

    private QPropertyDAO() {
        dbConnection = DatabaseConnection.getInstance().getConnection();
        if (dbConnection != null) {
            customerModel = new CustomerModel(dbConnection);
            userModel = new UserModel(dbConnection);
            propertyModel = new PropertyModel(dbConnection);
            bookingModel = new BookingModel(dbConnection);
        }
    }

    public static QPropertyDAO getInstance() {
        if (instance == null) {
            instance = new QPropertyDAO();
        }
        return instance;
    }

    // Method to get the database connection status
    public String getDBStatus() {
        return DatabaseConnection.getInstance().getConnectionStatus();
    }

    // Method to add a new customer to the customer table
    @Override
    public void addCustomer(String firstName, String lastName, String email, String phone) {
        customerModel.addCustomer(firstName, lastName, email, phone);
    }

    // Method to search for a customer by name or phone number
    @Override
    public List<Customer> searchCustomerByNameOrPhone(String value) {
        return customerModel.searchCustomerByNameOrPhone(value);
    }

    // Method to get all customers from the customer table
    @Override
    public List<Customer> getAllCustomers() {
        return customerModel.getAllCustomers();
    }

    // Method to delete a customer by customer id from the customer table
    @Override
    public void deleteCustomerById(int customerId) {
        customerModel.deleteCustomerById(customerId);
    }

    // Method to update a customer's information in the customer table by customer id
    @Override
    public void updateCustomer(int customerId, String firstName, String lastName, String email, String phone) {
        customerModel.updateCustomer(customerId, firstName, lastName, email, phone);
    }

    // Method to add a new property to the property table
    @Override
    public void addProperty(String address, String description, String year, String agentName, PropertyType propertyType, int customerId) {
        propertyModel.addProperty(address, description, year, agentName, propertyType, customerId);
    }

    // Method to search for a property by address
    @Override
    public List<Property> searchPropertyByAddress(String address) {
        return propertyModel.searchPropertyByAddress(address);
    }

    // Method to get all properties from the property table
    @Override
    public List<Property> getAllProperty() {
        return propertyModel.getAllProperty();
    }

    // Method to update a property from the property table
    @Override
    public void updateProperty(int propertyId, String address, String description, String year, String agentName, PropertyType propertyType, int customerId) {
        propertyModel.updateProperty(propertyId, address, description, year, agentName, propertyType, customerId);
    }

    // Method to delete a property by property id from the property table
    @Override
    public void deletePropertyById(int propertyId) {
        propertyModel.deletePropertyById(propertyId);
    }

    @Override
    public String getPropertyAddressById(int propertyId) {
        return propertyModel.getPropertyAddressById(propertyId);
    }

    // Method to add a new booking to the booking table
    @Override
    public void addBooking(int propertyId, String description, String bookingDate, String completionDate, double charge, String staffName, JobType jobType) {
        bookingModel.addBooking(propertyId, description, bookingDate, completionDate, charge, staffName, jobType);
    }

    // Method to search for a booking by address
    @Override
    public List<Booking> searchBookingByAddress(String address) {
        return bookingModel.searchBookingByAddress(address);
    }

    // Method to get all bookings from the booking table
    @Override
    public List<Booking> getAllBookings() {
        return bookingModel.getAllBookings();
    }

    // Method to get all booking charges from the booking table
    @Override
    public List<Double> getAllBookingCharges() {
        return bookingModel.getAllBookingCharges();
    }

    // Method to get all completed bookings from the booking table
    @Override
    public List<Booking> getAllCompletedBookingsByType(JobType jobType) {
        return bookingModel.getAllCompletedBookingsByType(jobType);
    }

    // Method to delete a booking by job id from the booking table
    @Override
    public void deleteBookingById(int jobId) {
        bookingModel.deleteBookingById(jobId);
    }

    // Method to update a booking in the booking table
    @Override
    public void updateBooking(int jobId, int propertyId, String description, String bookingDate, String completionDate, double charge, String staffName, JobType jobType) {
        bookingModel.updateBooking(jobId, propertyId, description, bookingDate, completionDate, charge, staffName, jobType);
    }

    // Method to add a new user to the user table
    @Override
    public void addUser(String firstName, String lastName, String email, String username, String password) {
        userModel.addUser(firstName, lastName, email, username, password);
    }

    // Method to search for a user by name
    @Override
    public boolean userExists(String name) {
        return userModel.userExists(name);
    }

    // Method to get a user by username and password
    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        return userModel.getUserByUsernameAndPassword(username, password);
    }

    // Method to get a user by username
    @Override
    public String getSha256Hash(String value) {
        return userModel.getSha256Hash(value);
    }
}
