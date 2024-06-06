package qpims.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyModel implements IProperty {
    private final Connection connection; // Connection object
    // Prepared statements for queries
    private PreparedStatement insertProperty;
    private PreparedStatement selectPropertyByAddress;
    private PreparedStatement selectAllProperties;
    private PreparedStatement deletePropertyById;
    private PreparedStatement updateProperty;
    
    private PreparedStatement selectPropertyById;

    public PropertyModel(Connection connection) {
        this.connection = connection;
        
        try {
            // Create query that inserts new entry into Property table
            insertProperty = connection.prepareStatement("INSERT INTO property (address, description, built_year, agent_name, property_type, customer_id) VALUES (?, ?, ?, ?, ?, ?)");
            // Create query that selects all entries from Property table by address
            selectPropertyByAddress = connection.prepareStatement("SELECT * FROM property WHERE address LIKE ?");
            // Create query that selects a property with a specific property id
            selectPropertyById = connection.prepareStatement("SELECT * FROM property WHERE property_id = ?");
            // Create query that selects all entries from Property table
            selectAllProperties = connection.prepareStatement("SELECT * FROM property");
            deletePropertyById = connection.prepareStatement("DELETE FROM property WHERE property_id = ?");
            // Create query that updates entry in Property table by propertyId
            updateProperty = connection.prepareStatement("UPDATE property SET address = ?, description = ?, built_year = ?, agent_name = ?, property_type = ?, customer_id = ? WHERE property_id = ?");
        } catch (SQLException ex) {
            Logger.getLogger(BookingModel.class.getName()).log(Level.SEVERE, "Database does not exist!!", ex); //log error message
    
        }
        
    }
    
    // Add a new property to the property table
    @Override
    public void addProperty(String address, String description, String built_year, String agentName, PropertyType propertyType, int customerId) {
        try {
            // Insert a new entry into the property table
            insertProperty.setString(1, address);
            insertProperty.setString(2, description);
            insertProperty.setString(3, built_year);
            insertProperty.setString(4, agentName);
            insertProperty.setString(5, propertyType.name());
            insertProperty.setInt(6, customerId);
            insertProperty.executeUpdate();
        } catch (SQLException ex) {
            
            Logger.getLogger(PropertyModel.class.getName()).log(Level.SEVERE, "Failed to add new property", ex);
        }
    }

    // Search for a property by address
    @Override
    public List<Property> searchPropertyByAddress(String address) {
        try {
            selectPropertyByAddress.setString(1, "%" + address + "%"); //set the address to search for in the query
            ResultSet rs = selectPropertyByAddress.executeQuery(); //execute the query
            List<Property> properties = new ArrayList<>(); //create a list to store the result set
            //loop through the result set
            while (rs.next()) {
                Property property = new Property();
                property.setPropertyId(rs.getInt("property_id"));
                property.setAddress(rs.getString("address"));
                property.setDescription(rs.getString("description"));
                property.setYear(rs.getString("built_year"));
                property.setAgentName(rs.getString("agent_name"));
                property.setPropertyType(PropertyType.valueOf(rs.getString("property_type")));
                property.setCustomerId(rs.getInt("customer_id"));
                properties.add(property);
            }
            return properties;
        } catch (SQLException ex) {
            Logger.getLogger(PropertyModel.class.getName()).log(Level.SEVERE, "Failed to search property by address", ex);
        }
        return null; //return null if the search fails
    }

    // Get all properties from the property table
    @Override
    public List<Property> getAllProperty() {
        try {
            ResultSet rs = selectAllProperties.executeQuery();
            List<Property> properties = new ArrayList<>();
            //loop through the result set and add each entry to the list
            while (rs.next()) {//
                Property property = new Property();
                property.setPropertyId(rs.getInt("property_id"));
                property.setAddress(rs.getString("address"));
                property.setDescription(rs.getString("description"));
                property.setYear(rs.getString("built_year"));
                property.setAgentName(rs.getString("agent_name"));
                property.setPropertyType(PropertyType.valueOf(rs.getString("property_type")));
                property.setCustomerId(rs.getInt("customer_id"));
                properties.add(property);
            }
            return properties;
        } catch (SQLException ex) {
            Logger.getLogger(PropertyModel.class.getName()).log(Level.SEVERE, "Failed to get all properties", ex);
        }
        return null;
    }

    // Update a property from the property table
    @Override
    public void updateProperty(int propertyId, String address, String description, String built_year, String agentName, PropertyType propertyType, int customerId) {
        try {
            updateProperty.setString(1, address);
            updateProperty.setString(2, description);
            updateProperty.setString(3, built_year);
            updateProperty.setString(4, agentName);
            updateProperty.setString(5, propertyType.name());
            updateProperty.setInt(6, customerId);
            updateProperty.setInt(7, propertyId);
            updateProperty.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PropertyModel.class.getName()).log(Level.SEVERE, "Failed to update property", ex);
        }
    }

    // Delete a property by property id from the property table
    @Override
    public void deletePropertyById(int propertyId) {
        try {
            deletePropertyById.setInt(1, propertyId); //set the property id in the query
            deletePropertyById.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PropertyModel.class.getName()).log(Level.SEVERE, "Failed to delete property", ex);
        }
    }
    @Override
    public String getPropertyAddressById(int propertyId) {
        try {
            selectPropertyById.setInt(1, propertyId); //set the property id in the query
            ResultSet rs = selectPropertyById.executeQuery();
            if (rs.next()) {
                return rs.getString("address");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PropertyModel.class.getName()).log(Level.SEVERE, "Failed to get property address by id", ex);
        }
        return null;
    }
    
}
