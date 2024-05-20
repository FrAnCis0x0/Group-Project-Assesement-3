package qpims.model;

public class Property {
    private int propertyId;
    private String address;
    private String description;
    private String year;
    private String propertyType;
    private int customerId;
    
    public Property() {}
    public Property(String address, String description, String year, String propertyType, int customerId) {
        this.address = address;
        this.description = description;
        this.year = year;
        this.propertyType = propertyType;
        this.customerId = customerId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    
}
