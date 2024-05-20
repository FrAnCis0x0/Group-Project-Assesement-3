package qpims.model;

import java.util.List;

public interface IProperty {
    public void addProperty(String address, String description, String year, String propertyType, int customerId);
    public List<Property> searchPropertyByAddress(String address);
    public List<Property> getAllProperty();
    public void updateProperty(int propertyId, String address, String description, String year, String propertyType, int customerId);
    public void deletePropertyById(int propertyId);
}
