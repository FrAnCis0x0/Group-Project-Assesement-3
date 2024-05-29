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
import qpims.model.PropertyType;
import qpims.model.QPropertyDAO;
import qpims.model.Validate;

public class CreatePropertyController implements Initializable {
    // FXML variables for UI elements
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

    private QPropertyDAO dao;
    private ObservableList<Customer> customerObservableList;

    // Initialize the controller class
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = QPropertyDAO.getInstance();

        // Populate the property type ComboBox with enum values
        cbPropertyType.setItems(FXCollections.observableArrayList(PropertyType.values()));

        // Initialize the customer ComboBox with all customers
        customerObservableList = FXCollections.observableArrayList();
        List<Customer> customerList = dao.getAllCustomers();
        // Add the customers to the observable list if they are not null
        if (customerList != null) {
            customerObservableList.addAll(customerList);
        }
        cbAssociatedCustomer.setItems(customerObservableList);
    }

    // Go back to the property view
    @FXML
    private void goToPropertyView(ActionEvent event) {
        QProperty.setBorderCenter("property");
    }

    // Create a new property and add to the database
    @FXML
    private void createProperty(ActionEvent event) {
        // Validate inputs using the Validate class
        if (!Validate.getInstance().validateProperty(
                tfAddress.getText(),
                tfDescription.getText(),
                tfYear.getText(),
                tfAgentName.getText(),
                cbPropertyType.getValue() != null ? cbPropertyType.getValue().name() : "",
                cbAssociatedCustomer.getValue() != null ? cbAssociatedCustomer.getValue().toString() : ""
        )) {
            return;
        }

        Customer associatedCustomer = cbAssociatedCustomer.getValue(); // Get the selected customer
        int customerId = associatedCustomer.getCustomerId(); // Get the customer ID from the selected customer
        dao.addProperty(
                tfAddress.getText(),
                tfDescription.getText(),
                tfYear.getText(),
                tfAgentName.getText(),
                cbPropertyType.getValue(),
                customerId
        );
        MessageBox.getInstance().showInfo("Property created successfully."); // Show success message
        clearInputs(); // Clear inputs after successful creation
    }

    // Clear all input fields
    @FXML
    private void clear(ActionEvent event) {
        clearInputs();
    }
    private void clearInputs() {
        tfAddress.clear();
        tfDescription.clear();
        tfYear.clear();
        tfAgentName.clear();
        cbPropertyType.setValue(null);
        cbAssociatedCustomer.setValue(null);
    }
}
