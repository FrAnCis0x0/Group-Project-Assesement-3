package qpims.model;

import java.time.LocalDate;

public class Booking {
    private int jobId;
    private int propertyId;
    private String description;
    private LocalDate bookingDate;
    private LocalDate completionDate;
    private double charge;
    private String staffName;
    private String jobType;
    
    public Booking(){}
    public Booking(int propertyId, String description, LocalDate bookingDate, LocalDate completionDate, double charge, String staffName, String jobType) {
        this.propertyId = propertyId;
        this.description = description;
        this.bookingDate = bookingDate;
        this.completionDate = completionDate;
        this.charge = charge;
        this.staffName = staffName;
        this.jobType = jobType;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
    
    
}
