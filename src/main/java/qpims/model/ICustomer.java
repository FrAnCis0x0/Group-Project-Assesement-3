package qpims.model;

import java.util.List;

public interface ICustomer {
    public void addCustomer(String firstName, String lastName, String email, String phone);
    public List<Customer> searchCustomerByNameOrPhone(String value);
    public List<Customer> getAllCustomers();
    public void deleteCustomerById(int customerId);
    public void updateCustomer(int customerId, String firstName, String lastName, String email, String phone);

}
