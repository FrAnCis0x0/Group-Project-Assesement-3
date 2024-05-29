package qpims.model;

import java.util.List;

// Interface for Booking class that defines the methods that will be implemented in the Booking class
public interface IBooking {
    public void addBooking(int propertyId, String description, String bookingDate, String completionDate, double charge, String staffName,JobType jobType ); //add booking to Booking table
    public List<Booking> searchBookingByAddress(String address); //search booking by address
    public List<Booking> getAllBookings(); //get all bookings
    public List<Double> getAllBookingCharges(); //get all booking charges
    public List<Booking> getAllCompletedBookingsByType(JobType jobType); //get all completed bookings by job type
    public void deleteBookingById(int jobId); //delete booking by jobId
    public void updateBooking(int jobId, int propertyId, String desciption, String bookingDate, String completionDate, double charge, String staffName,JobType jobType); //update booking
}
