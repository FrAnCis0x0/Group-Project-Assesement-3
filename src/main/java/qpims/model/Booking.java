package qpims.model;

import java.time.LocalDate;

public class Booking {
    // Variables for the booking class
    private int jobId;
    private int propertyId;
    private String description;
    private LocalDate bookingDate;
    private LocalDate completionDate;
    private double charge;
    private String staffName;
    private JobType jobType;
    
    private String associatedAddress;

    // Default constructor
    public Booking(){}
    // Constructor to initialize variables
    public Booking(int propertyId, String description, LocalDate bookingDate, LocalDate completionDate, double charge, String staffName, JobType jobType) {
        this.propertyId = propertyId;
        this.description = description;
        this.bookingDate = bookingDate;
        this.completionDate = completionDate;
        this.charge = charge;
        this.staffName = staffName;
        this.jobType = jobType;
    }

    // Getters and setters
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

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }
    public void setAssociatedAddress(String address){
        this.associatedAddress = address;
    }
    public String getAssociatedAddress(){
        return associatedAddress;
    }

}
