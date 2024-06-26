package qpims.model;

public class Property {
    // Variables for the property class
    private int propertyId;
    private String address;
    private String description;
    private String year;
    private String agentName;
    private PropertyType propertyType;
    private int customerId;

    // Default constructor
    public Property() {}
    // Constructor to initialize variables
    public Property(String address, String description, String year,String agentName, PropertyType propertyType, int customerId) {
        this.address = address;
        this.description = description;
        this.year = year;
        this.agentName = agentName;
        this.propertyType = propertyType;
        this.customerId = customerId;
    }

    // Getters and setters
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
    

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    // toString method to return the property ID as a string for display purposes in the table view
    @Override
    public String toString() {
        return "Property ID: " + getPropertyId();
    }
}
