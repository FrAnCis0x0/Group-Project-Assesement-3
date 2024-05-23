package qpims.model;

import java.util.List;
import java.sql.*;
/**
 *
 * Qproperty DAO class implements the interfaces ICustomer, IProperty, IBooking, IUser
 * It is responsible for handling the CRUD operations for the Customer, Property, Booking and User entities
 * It uses the CustomerModel, PropertyModel, BookingModel and UserModel classes to interact with the database
 * It uses the DatabaseConnection class to establish a connection to the database
 *
 * **/
public class QPropertyDAO implements ICustomer, IProperty, IBooking, IUser {
    private static QPropertyDAO instance;
    private  CustomerModel customerModel;
    private  UserModel userModel;
    private  PropertyModel propertyModel;
    private  BookingModel bookingModel;
    private final Connection dbConnection;

    private  QPropertyDAO() {
        dbConnection = DatabaseConnection.getInstance().getConnection();
        if(dbConnection != null){
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
    
    
    public String getDBStatus() {
        return DatabaseConnection.getInstance().getConnectionStatus();
    }
    @Override
    public void addCustomer(String firstName, String lastName, String address, String phone) {
        customerModel.addCustomer(firstName, lastName, address, phone);
    }

    @Override
    public List<Customer> searchCustomerByNameOrPhone(String value) {
        return customerModel.searchCustomerByNameOrPhone(value);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerModel.getAllCustomers();
    }

    @Override
    public void deleteCustomerById(int customerId) {
        customerModel.deleteCustomerById(customerId);
    }

    @Override
    public void updateCustomer(int customerId, String firstName, String lastName, String address, String phone) {
        customerModel.updateCustomer(customerId, firstName, lastName, address, phone);
    }

    @Override
    public void addProperty(String address, String description, String year, String agentName, PropertyType propertyType, int customerId) {
        propertyModel.addProperty(address, description, year, agentName, propertyType, customerId);
    }

    @Override
    public List<Property> searchPropertyByAddress(String address) {
        return propertyModel.searchPropertyByAddress(address);
    }

    @Override
    public List<Property> getAllProperty() {
        return propertyModel.getAllProperty();
    }

    @Override
    public void updateProperty(int propertyId, String address, String description, String year, String agentName, PropertyType propertyType, int customerId) {
        propertyModel.updateProperty(propertyId, address, description, year, agentName, propertyType, customerId);
    }

    @Override
    public void deletePropertyById(int propertyId) {
        propertyModel.deletePropertyById(propertyId);
    }

    @Override
    public void addBooking(int propertyId, String description, String bookingDate, String completionDate, double charge, String staffName, JobType jobType) {
        bookingModel.addBooking(propertyId, description, bookingDate, completionDate, charge, staffName, jobType);
    }

    @Override
    public List<Booking> searchBookingByAddress(String address) {
        return bookingModel.searchBookingByAddress(address);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingModel.getAllBookings();
    }

    @Override
    public List<Double> getAllBookingCharges() {
        return bookingModel.getAllBookingCharges();
    }

    @Override
    public List<Booking> getAllCompletedBookingsByType(JobType jobType) {
        return bookingModel.getAllCompletedBookingsByType(jobType);
    }

    @Override
    public void deleteBookingById(int jobId) {
        bookingModel.deleteBookingById(jobId);
    }

    @Override
    public void updateBooking(int jobId, int propertyId, String description, String bookingDate, String completionDate, double charge, String staffName, JobType jobType) {
        bookingModel.updateBooking(jobId, propertyId, description, bookingDate, completionDate, charge, staffName, jobType);
    }

    @Override
    public void addUser(String firstName, String lastName, String email, String username, String password) {
        userModel.addUser(firstName, lastName, email, username, password);
    }

    @Override
    public boolean userExists(String name) {
        return userModel.userExists(name);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        return userModel.getUserByUsernameAndPassword(username, password);
    }

    @Override
    public String getSha256Hash(String value) {
        return userModel.getSha256Hash(value);
    }
    
}
