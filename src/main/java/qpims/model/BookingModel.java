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
            selectAllBookings = connection.prepareStatement("SELECT * FROM booking");
            
            //create query that selects all entires from Booking table by address
            selectBookingByAddress = connection.prepareStatement("SELECT * FROM booking WHERE property_id = ?");
            
            //create query that selects all charges from Booking table
            selectAllBookingCharges = connection.prepareStatement("SELECT charge FROM booking");
            
            //create query that inserts new entry into Booking table
            insertBooking = connection.prepareStatement("INSERT INTO booking (property_id, description, booking_date, completion_date, charge, staff_name, job_type) VALUES (?,?,?,?,?,?,?)");
            
            //create query that selects all completed entries from Booking table by job type
            selectAllCompletedBookingByType = connection.prepareStatement("SELECT * FROM booking WHERE job_type = ? AND completion_date IS NOT NULL");
            
            //create query that deletes entry from Booking table by jobId
            deleteBookingById = connection.prepareStatement("DELETE FROM booking WHERE job_id = ?");
            
            //create query that updates entry in Booking table by jobId
            updateBooking = connection.prepareStatement("UPDATE booking SET property_id = ?, description = ?, booking_date = ?, completion_date = ?, charge = ?, staff_name = ?, job_type = ? WHERE job_id = ?");
        } catch (SQLException ex) {
            Logger.getLogger(BookingModel.class.getName()).log(Level.SEVERE, "Database does not exist!!", ex);
        }
    }
    
    
    @Override
    public void addBooking(int propertyId, String description, String bookingDate, String completionDate, double charge, String staffName, JobType jobType) {
        
        try {
            //insert new entry into Booking table
            insertBooking.setInt(1, propertyId);
            insertBooking.setString(2, description);
            insertBooking.setString(3, bookingDate);
            insertBooking.setString(4, completionDate);
            insertBooking.setDouble(5, charge);
            insertBooking.setString(6, staffName);
            insertBooking.setString(7, jobType.toMySQLName());
            
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
                booking.setJobId(resultSet.getInt("job_id"));
                booking.setPropertyId(resultSet.getInt("property_id"));
                booking.setDescription(resultSet.getString("description"));
                booking.setBookingDate(resultSet.getDate("booking_date").toLocalDate());
                booking.setCompletionDate(resultSet.getDate("completion_date").toLocalDate());
                booking.setCharge(resultSet.getDouble("charge"));
                booking.setStaffName(resultSet.getString("staff_name"));
                booking.setJobType(JobType.fromMySQLName(resultSet.getString("job_type")));
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
                booking.setJobId(resultSet.getInt("job_id"));
                booking.setPropertyId(resultSet.getInt("property_id"));
                booking.setDescription(resultSet.getString("description"));
                booking.setBookingDate(resultSet.getDate("booking_date").toLocalDate());
                booking.setCompletionDate(resultSet.getDate("completion_date").toLocalDate());
                booking.setCharge(resultSet.getDouble("charge"));
                booking.setStaffName(resultSet.getString("staff_name"));
                booking.setJobType(JobType.fromMySQLName(resultSet.getString("job_type")));
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
    public List<Booking> getAllCompletedBookingsByType(JobType jobType) {
        try {
            selectAllCompletedBookingByType.setString(1, jobType.toMySQLName());
            ResultSet resultSet = selectAllCompletedBookingByType.executeQuery();
            ArrayList bookings = new ArrayList<Booking>();
            
            //loop through result set and add each entry to list
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setJobId(resultSet.getInt("job_id"));
                booking.setPropertyId(resultSet.getInt("property_id"));
                booking.setDescription(resultSet.getString("description"));
                booking.setBookingDate(resultSet.getDate("booking_date").toLocalDate());
                booking.setCompletionDate(resultSet.getDate("completion_date").toLocalDate());
                booking.setCharge(resultSet.getDouble("charge"));
                booking.setStaffName(resultSet.getString("staff_name"));
                booking.setJobType(JobType.fromMySQLName(resultSet.getString("job_type")));
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
    public void updateBooking(int jobId, int propertyId, String description, String bookingDate, String completionDate, double charge, String staffName, JobType jobType) {
        try {
            updateBooking.setInt(1, propertyId);
            updateBooking.setString(2, description);
            updateBooking.setString(3, bookingDate);
            updateBooking.setString(4, completionDate);
            updateBooking.setDouble(5, charge);
            updateBooking.setString(6, staffName);
            updateBooking.setString(7, jobType.toMySQLName());
            updateBooking.setInt(8, jobId);
            updateBooking.executeUpdate();
            
        }catch (SQLException ex){
            Logger.getLogger(BookingModel.class.getName()).log(Level.SEVERE, "Failed to update booking!", ex);
        }
    }
    
}
