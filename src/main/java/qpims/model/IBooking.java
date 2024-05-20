package qpims.model;

import java.util.List;

public interface IBooking {
    public void addBooking(int propertyId, String desciption, String bookingDate, String completionDate, double charge, String staffName,String jobType );
    public List<Booking> searchBookingByAddress(String address);
    public List<Booking> getAllBookings();
    public List<Double> getAllBookingCharges();
    public List<Booking> getAllCompletedBookingsByType(String jobType);
    public void deleteBookingById(int jobId);
    public void updateBooking(int jobId, int propertyId, String desciption, String bookingDate, String completionDate, double charge, String staffName,String jobType);
}
