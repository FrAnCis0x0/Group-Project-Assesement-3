package qpims.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyModel implements IProperty {
    private final Connection connection;
    private PreparedStatement insertProperty;
    private PreparedStatement selectPropertyByAddress;
    private PreparedStatement selectAllProperties;
    private PreparedStatement deletePropertyById;
    private PreparedStatement updateProperty;
    
    public PropertyModel(Connection connection) {
        this.connection = connection;
        
        try {
            insertProperty = connection.prepareStatement("INSERT INTO property (address, description, year, property_type, customer_id) VALUES (?, ?, ?, ?, ?)");
            selectPropertyByAddress = connection.prepareStatement("SELECT * FROM property WHERE address LIKE ?");
            selectAllProperties = connection.prepareStatement("SELECT * FROM property");
            deletePropertyById = connection.prepareStatement("DELETE FROM property WHERE property_id = ?");
            updateProperty = connection.prepareStatement("UPDATE property SET address = ?, description = ?, year = ?, property_type = ?, customer_id = ? WHERE property_id = ?");
        } catch (SQLException ex) {
            Logger.getLogger(BookingModel.class.getName()).log(Level.SEVERE, "Database does not exist!!", ex);
    
        }
        
    }
    

    @Override
    public void addProperty(String address, String description, String year, String propertyType, int customerId) {
        try {
            insertProperty.setString(1, address);
            insertProperty.setString(2, description);
            insertProperty.setString(3, year);
            insertProperty.setString(4, propertyType);
            insertProperty.setInt(5, customerId);
            insertProperty.executeUpdate();
        } catch (SQLException ex) {
            
            Logger.getLogger(PropertyModel.class.getName()).log(Level.SEVERE, "Failed to add new property", ex);
        }
    }

    @Override
    public List<Property> searchPropertyByAddress(String address) {
        try {
            selectPropertyByAddress.setString(1, "%" + address + "%");
            ResultSet rs = selectPropertyByAddress.executeQuery();
            List<Property> properties = new ArrayList<>();
            //loop through the result set
            while (rs.next()) {
                Property property = new Property();
                property.setPropertyId(rs.getInt("property_id"));
                property.setAddress(rs.getString("address"));
                property.setDescription(rs.getString("description"));
                property.setYear(rs.getString("year"));
                property.setPropertyType(rs.getString("property_type"));
                property.setCustomerId(rs.getInt("customer_id"));
                properties.add(property);
            }
            return properties;
        } catch (SQLException ex) {
            Logger.getLogger(PropertyModel.class.getName()).log(Level.SEVERE, "Failed to search property by address", ex);
        }
        return null;
    }

    @Override
    public List<Property> getAllProperty() {
        try {
            ResultSet rs = selectAllProperties.executeQuery();
            List<Property> properties = new ArrayList<>();
            while (rs.next()) {
                Property property = new Property();
                property.setPropertyId(rs.getInt("property_id"));
                property.setAddress(rs.getString("address"));
                property.setDescription(rs.getString("description"));
                property.setYear(rs.getString("year"));
                property.setPropertyType(rs.getString("property_type"));
                property.setCustomerId(rs.getInt("customer_id"));
                properties.add(property);
            }
            return properties;
        } catch (SQLException ex) {
            Logger.getLogger(PropertyModel.class.getName()).log(Level.SEVERE, "Failed to get all properties", ex);
        }
        return null;
    }

    @Override
    public void updateProperty(int propertyId, String address, String description, String year, String propertyType, int customerId) {
        try {
            updateProperty.setString(1, address);
            updateProperty.setString(2, description);
            updateProperty.setString(3, year);
            updateProperty.setString(4, propertyType);
            updateProperty.setInt(5, customerId);
            updateProperty.setInt(6, propertyId);
            updateProperty.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PropertyModel.class.getName()).log(Level.SEVERE, "Failed to update property", ex);
        }
    }

    @Override
    public void deletePropertyById(int propertyId) {
        try {
            deletePropertyById.setInt(1, propertyId);
            deletePropertyById.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PropertyModel.class.getName()).log(Level.SEVERE, "Failed to delete property", ex);
        }
    }
    
}
