package qpims.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import qpims.QProperty;
import qpims.model.Customer;
import qpims.model.MessageBox;
import qpims.model.Property;
import qpims.model.PropertyType;
import qpims.model.QPropertyDAO;
import qpims.model.Validate;

public class PropertyDetailsController implements Initializable {

    // FXML variables for the UI components
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfAgentName;
    @FXML
    private ComboBox<Customer> cbAssociatedCustomer;
    @FXML
    private ComboBox<PropertyType> cbPropertyType;
    @FXML
    private TextField tfYear;
    @FXML
    private TextArea tfDescription;

    private Property selectedProperty; // The selected property to be updated
    private QPropertyDAO dao; // DAO for accessing the data
    private ObservableList<Customer> customerObservableList; // Observable list for the customers

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = QPropertyDAO.getInstance();

        // Populate the property type ComboBox with enum values
        cbPropertyType.setItems(FXCollections.observableArrayList(PropertyType.values()));

        // Initialize the customer ComboBox with the customers from the database
        customerObservableList = FXCollections.observableArrayList();
        List<Customer> customerList = dao.getAllCustomers();
        if (customerList != null) {
            customerObservableList.addAll(customerList); // Add the customers to the observable list if they are not null
        }
        cbAssociatedCustomer.setItems(customerObservableList); // Set the items of the ComboBox to the observable list
    }

    // Method to go back to the property view
    private void goBack() {
        QProperty.setBorderCenter("property");
    }

    // Event handler to go back to the property view
    @FXML
    private void goToPropertyView(ActionEvent event) {
        goBack();
    }

    // Event handlers to delete the property
    @FXML
    private void deleteProperty(ActionEvent event) {
        dao.deletePropertyById(selectedProperty.getPropertyId());
        MessageBox.getInstance().showInfo("Property deleted successfully.");
        goBack();
    }

    // Event handlers to update the property
    @FXML
    private void updateProperty(ActionEvent event) {
        // Validate the property details using the Validate class
        if (!Validate.getInstance().validateProperty(
                tfAddress.getText(),
                tfDescription.getText(),
                tfYear.getText(),
                tfAgentName.getText(),
                cbPropertyType.getValue() != null ? cbPropertyType.getValue().name() : "",
                cbAssociatedCustomer.getValue() != null ? String.valueOf(cbAssociatedCustomer.getValue().getCustomerId()) : ""
        )) {
            return; // If the inputs are invalid, return
        }

        // Update the property details in the database
        dao.updateProperty(
                selectedProperty.getPropertyId(),
                tfAddress.getText(),
                tfDescription.getText(),
                tfYear.getText(),
                tfAgentName.getText(),
                cbPropertyType.getValue(),
                cbAssociatedCustomer.getValue().getCustomerId()
        );
        MessageBox.getInstance().showInfo("Property updated successfully.");
    }

    // Method to set the data of the selected property
    public void setData(Property property) {
        selectedProperty = property;
        tfAddress.setText(selectedProperty.getAddress());
        tfDescription.setText(selectedProperty.getDescription());
        tfYear.setText(selectedProperty.getYear());
        tfAgentName.setText(selectedProperty.getAgentName());
        cbPropertyType.setValue(selectedProperty.getPropertyType());

        // Set the associated customer in the ComboBox
        for (Customer customer : customerObservableList) {
            if (customer.getCustomerId() == selectedProperty.getCustomerId()) {
                cbAssociatedCustomer.setValue(customer);
                break;
            }
        }
    }
}
