/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package TDD;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import qpims.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QPropertyTests {
    
    
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    // Test of getAllCustomers method, of class CustomerModel.
    // This test is expected to fail, if the method returns an empty list.
    @Test
    public void testGetAllCustomers() {
        System.out.println("getAllCustomers");
        List<Customer> result = QPropertyDAO.getInstance().getAllCustomers();
        assertFalse(result.isEmpty());
    
    }
    
    // Test of addCustomer method, of class CustomerModel.
    // This test is expected to pass, if the method adds a new customer to the database.
    @Test
    public void testAddCustomer() {
        System.out.println("addCustomer");
        String firstName = "Test";
        String lastName = "Test";
        String address = "123 Main St";
        String phone = "123-456-7890";
        QPropertyDAO.getInstance().addCustomer(firstName, lastName, address, phone);
        List<Customer> result = QPropertyDAO.getInstance().searchCustomerByNameOrPhone("Test");
        assertFalse(result.isEmpty());
    }
    
    // Test of searchCustomerByNameOrPhone method, of class CustomerModel.
    // This test is expected to pass, if the method returns a list of customers with the search value.
    @Test
    public void testSearchCustomerByNameOrPhone() {
        System.out.println("searchCustomerByNameOrPhone");
        String value = "Test";
        List<Customer> result = QPropertyDAO.getInstance().searchCustomerByNameOrPhone(value);
        assertFalse(result.isEmpty());
    }
    
    // Test of updateCustomer method, of class CustomerModel.
    // This test is expected to pass, if the method updates a customer in the database.
    @Test
    public void testUpdateCustomer() {
        System.out.println("updateCustomer");
        //get the customer id of the customer to be updated
        List<Customer> customers = QPropertyDAO.getInstance().searchCustomerByNameOrPhone("Test");
        int customerId = customers.get(0).getCustomerId();
        String firstName = "Test2";
        String lastName = "Test2";
        String address = "123 Main St";
        String phone = "123-456-7890";
        QPropertyDAO.getInstance().updateCustomer(customerId, firstName, lastName, address, phone);
        List<Customer> result = QPropertyDAO.getInstance().searchCustomerByNameOrPhone("Test2");
        assertFalse(result.isEmpty());
    }
    
    // Test of deleteCustomerById method, of class CustomerModel.
    // This test is expected to pass, if the method deletes a customer from the database.
    @Test
    public void testDeleteCustomerById() {
        System.out.println("deleteCustomerById");
        //get the customer id of the customer to be deleted
        List<Customer> customers = QPropertyDAO.getInstance().searchCustomerByNameOrPhone("Test2");
        int customerId = customers.get(0).getCustomerId();
        QPropertyDAO.getInstance().deleteCustomerById(customerId);
        List<Customer> result = QPropertyDAO.getInstance().searchCustomerByNameOrPhone("Test2");
        assertTrue(result.isEmpty());
    }
    
    
    // Test of getAllProperty method, of class PropertyModel.
    // This test is expected to fail, if the method returns an empty list.
    @Test
    public void testGetAllProperty() {
        System.out.println("getAllProperty");
        List<Property> result = QPropertyDAO.getInstance().getAllProperty();
        assertFalse(result.isEmpty());
    }
    
    // Test of addProperty method, of class PropertyModel.
    // This test is expected to pass, if the method adds a new property to the database.
    @Test
    public void testAddProperty() {
        System.out.println("addProperty");
        String address = "123 Main St";
        String description = "Test";
        String year = "2021";
        String agentName = "Test";
        PropertyType propertyType = PropertyType.HOUSE;
        int customerId = 1;
        QPropertyDAO.getInstance().addProperty(address, description, year, agentName, propertyType, customerId);
        List<Property> result = QPropertyDAO.getInstance().searchPropertyByAddress("123 Main St");
        assertFalse(result.isEmpty());
    }
    
    // Test of searchPropertyByAddress method, of class PropertyModel.
    // This test is expected to pass, if the method returns a list of properties with the search value.
    @Test
    public void testSearchPropertyByAddress() {
        System.out.println("searchPropertyByAddress");
        String address = "123 Main St";
        List<Property> result = QPropertyDAO.getInstance().searchPropertyByAddress(address);
        assertFalse(result.isEmpty());
    }
    
    // Test of updateProperty method, of class PropertyModel.
    // This test is expected to pass, if the method updates a property in the database.
    @Test
    public void testUpdateProperty() {
        System.out.println("updateProperty");
        //get the property id of the property to be updated
        List<Property> properties = QPropertyDAO.getInstance().searchPropertyByAddress("123 Main St");
        int propertyId = properties.get(0).getPropertyId();
        String address = "123 Main St";
        String description = "Test2";
        String year = "2021";
        String agentName = "Test";
        PropertyType propertyType = PropertyType.HOUSE;
        int customerId = 1;
        QPropertyDAO.getInstance().updateProperty(propertyId, address, description, year, agentName, propertyType, customerId);
        List<Property> result = QPropertyDAO.getInstance().searchPropertyByAddress("123 Main St");
        assertFalse(result.isEmpty());
    }
    
    // Test of deletePropertyById method, of class PropertyModel.
    // This test is expected to pass, if the method deletes a property from the database.
    @Test
    public void testDeletePropertyById() {
        System.out.println("deletePropertyById");
        //get the property id of the property to be deleted
        List<Property> properties = QPropertyDAO.getInstance().searchPropertyByAddress("123 Main St");
        int propertyId = properties.get(0).getPropertyId();
        QPropertyDAO.getInstance().deletePropertyById(propertyId);
        List<Property> result = QPropertyDAO.getInstance().searchPropertyByAddress("123 Main St");
        assertTrue(result.isEmpty());
    }
    
    // Test of getDBStatus method, of class QPropertyDAO.
    // This test is expected to pass, if the method returns a connection status.
    @Test
    public void testGetDBStatus() {
        System.out.println("getDBStatus");
        String result = QPropertyDAO.getInstance().getDBStatus();
        assertNotNull(result);
    }
    
    // Test of getSha265 method, of class QPropertyDAO.
    // This test is expected to pass, if the method returns a SHA-256 hash.
    @Test
    public void testGetSha256() {
        System.out.println("getSha256");
        String password = "password";
        String result = QPropertyDAO.getInstance().getSha256Hash(password);
        assertNotNull(result);
    }
    
   
    
}
