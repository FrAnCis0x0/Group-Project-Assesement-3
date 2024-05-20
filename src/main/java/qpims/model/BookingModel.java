package qpims.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingModel implements IBooking {
    
    private final Connection connection; //connection object
    
    private PreparedStatement selectAllBookings;
    private PreparedStatement selectBookingByAddress;
    private PreparedStatement selectAllBookingCharges;
    private PreparedStatement insertBooking;
    private PreparedStatement selectAllCompletedBookingByType;
    private PreparedStatement deleteBookingById;
    private PreparedStatement updateBooking;
    
    
    public BookingModel(Connection connection) {
        this.connection = connection;
        
        try {
            //create query that selects all entires from Booking table
            selectAllBookings = connection.prepareStatement("SELECT * FROM Booking");
            
            //create query that selects all entires from Booking table by address
            selectBookingByAddress = connection.prepareStatement("SELECT * FROM Booking WHERE propertyId = ?");
            
            //create query that selects all charges from Booking table
            selectAllBookingCharges = connection.prepareStatement("SELECT charge FROM Booking");
            
            //create query that inserts new entry into Booking table
            insertBooking = connection.prepareStatement("INSERT INTO Booking (propertyId, description, bookingDate, completionDate, charge, staffName, jobType) VALUES (?,?,?,?,?,?,?)");
            
            //create query that selects all completed entries from Booking table by job type
            selectAllCompletedBookingByType = connection.prepareStatement("SELECT * FROM Booking WHERE jobType = ? AND completionDate IS NOT NULL");
            
            //create query that deletes entry from Booking table by jobId
            deleteBookingById = connection.prepareStatement("DELETE FROM Booking WHERE jobId = ?");
            
            //create query that updates entry in Booking table by jobId
            updateBooking = connection.prepareStatement("UPDATE Booking SET propertyId = ?, description = ?, bookingDate = ?, completionDate = ?, charge = ?, staffName = ?, jobType = ? WHERE jobId = ?");
        } catch (SQLException ex) {
            Logger.getLogger(BookingModel.class.getName()).log(Level.SEVERE, "Database does not exist!!", ex);
        }
    }
    
    
    @Override
    public void addBooking(int propertyId, String description, String bookingDate, String completionDate, double charge, String staffName, String jobType) {
        
        try {
            //insert new entry into Booking table
            insertBooking.setInt(1, propertyId);
            insertBooking.setString(2, description);
            insertBooking.setString(3, bookingDate);
            insertBooking.setString(4, completionDate);
            insertBooking.setDouble(5, charge);
            insertBooking.setString(6, staffName);
            insertBooking.setString(7, jobType);
            
            //execute query
            insertBooking.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BookingModel.class.getName()).log(Level.SEVERE, "Failed to insert entry!", ex);
        }
    }
    

    @Override
    public List<Booking> searchBookingByAddress(String address) {
        try {
            selectBookingByAddress.setString(1, address);
            ResultSet resultSet = selectBookingByAddress.executeQuery();
            ArrayList bookings = new ArrayList<Booking>();
            
            //loop through result set and add each entry to list
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setJobId(resultSet.getInt("jobId"));
                booking.setPropertyId(resultSet.getInt("propertyId"));
                booking.setDescription(resultSet.getString("description"));
                booking.setBookingDate(resultSet.getDate("bookingDate").toLocalDate());
                booking.setCompletionDate(resultSet.getDate("completionDate").toLocalDate());
                booking.setCharge(resultSet.getDouble("charge"));
                booking.setStaffName(resultSet.getString("staffName"));
                booking.setJobType(resultSet.getString("jobType"));
                bookings.add(booking);
            }
            return bookings;
            
        }catch (SQLException ex){
            Logger.getLogger(BookingModel.class.getName()).log(Level.SEVERE, "Failed to search by address!", ex);
        }
        return null;
    }

    @Override
    public List<Booking> getAllBookings() {
        try {
            ResultSet resultSet = selectAllBookings.executeQuery();
            ArrayList bookings = new ArrayList<Booking>();
            
            //loop through result set and add each entry to list
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setJobId(resultSet.getInt("jobId"));
                booking.setPropertyId(resultSet.getInt("propertyId"));
                booking.setDescription(resultSet.getString("description"));
                booking.setBookingDate(resultSet.getDate("bookingDate").toLocalDate());
                booking.setCompletionDate(resultSet.getDate("completionDate").toLocalDate());
                booking.setCharge(resultSet.getDouble("charge"));
                booking.setStaffName(resultSet.getString("staffName"));
                booking.setJobType(resultSet.getString("jobType"));
                bookings.add(booking);
            }
            return bookings;
            
        }catch (SQLException ex){
            Logger.getLogger(BookingModel.class.getName()).log(Level.SEVERE, "Failed to get all bookings!", ex);
        }
        return null;
    }

    @Override
    public List<Double> getAllBookingCharges() {
        try {
            ResultSet resultSet = selectAllBookingCharges.executeQuery();
            ArrayList bookings = new ArrayList<Double>();
            
            //loop through result set and add each entry to list
            while (resultSet.next()) {
                bookings.add(resultSet.getDouble("charge"));
            }
            
            return bookings;
        }catch (SQLException ex){
            Logger.getLogger(BookingModel.class.getName()).log(Level.SEVERE, "Failed to get all booking charges!", ex);
        }
        return null;
    }

    @Override
    public List<Booking> getAllCompletedBookingsByType(String jobType) {
        try {
            selectAllCompletedBookingByType.setString(1, jobType);
            ResultSet resultSet = selectAllCompletedBookingByType.executeQuery();
            ArrayList bookings = new ArrayList<Booking>();
            
            //loop through result set and add each entry to list
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setJobId(resultSet.getInt("jobId"));
                booking.setPropertyId(resultSet.getInt("propertyId"));
                booking.setDescription(resultSet.getString("description"));
                booking.setBookingDate(resultSet.getDate("bookingDate").toLocalDate());
                booking.setCompletionDate(resultSet.getDate("completionDate").toLocalDate());
                booking.setCharge(resultSet.getDouble("charge"));
                booking.setStaffName(resultSet.getString("staffName"));
                booking.setJobType(resultSet.getString("jobType"));
                bookings.add(booking);
            }
            
           return bookings;
        }catch (SQLException ex){
            Logger.getLogger(BookingModel.class.getName()).log(Level.SEVERE, "Failed to get all completed bookings by type!", ex);
        }
        return null;
    }

    @Override
    public void deleteBookingById(int jobId) {
        try {
            deleteBookingById.setInt(1, jobId);
            deleteBookingById.executeUpdate();
            
        }catch (SQLException ex){
            Logger.getLogger(BookingModel.class.getName()).log(Level.SEVERE, "Failed to delete booking by id!", ex);
        }
    }

    @Override
    public void updateBooking(int jobId, int propertyId, String desciption, String bookingDate, String completionDate, double charge, String staffName, String jobType) {
        try {
            updateBooking.setInt(1, propertyId);
            updateBooking.setString(2, desciption);
            updateBooking.setString(3, bookingDate);
            updateBooking.setString(4, completionDate);
            updateBooking.setDouble(5, charge);
            updateBooking.setString(6, staffName);
            updateBooking.setString(7, jobType);
            updateBooking.setInt(8, jobId);
            updateBooking.executeUpdate();
            
        }catch (SQLException ex){
            Logger.getLogger(BookingModel.class.getName()).log(Level.SEVERE, "Failed to update booking!", ex);
        }
    }
    
}
