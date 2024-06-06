package qpims.model;

import java.util.List;

// Interface for Property class that defines the methods that will be implemented in the Property class
public interface IProperty {
    public void addProperty(String address, String description, String year, String agentName, PropertyType propertyType, int customerId); //add a new property to the property table
    public List<Property> searchPropertyByAddress(String address); //search for a property by address
    public List<Property> getAllProperty(); //return a list of all properties in the property table
    public void updateProperty(int propertyId, String address, String description, String year, String agentName, PropertyType propertyType, int customerId); //update a property's information in the property table
    public void deletePropertyById(int propertyId); //delete a property by property id from the property table
    public String getPropertyAddressById(int propertyId); //get the address of a property by property id
}
