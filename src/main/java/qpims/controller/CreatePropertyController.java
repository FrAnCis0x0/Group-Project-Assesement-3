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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = QPropertyDAO.getInstance();

        // Populate the property type ComboBox with enum values
        cbPropertyType.setItems(FXCollections.observableArrayList(PropertyType.values()));

        // Initialize the customer ComboBox
        customerObservableList = FXCollections.observableArrayList();
        List<Customer> customerList = dao.getAllCustomers();
        if (customerList != null) {
            customerObservableList.addAll(customerList);
        }
        cbAssociatedCustomer.setItems(customerObservableList);
    }

    @FXML
    private void goToPropertyView(ActionEvent event) {
        QProperty.setBorderCenter("property");
    }

    @FXML
    private void createProperty(ActionEvent event) {
        // Validate inputs using the Validate class
        if (!Validate.getInstance().validateProperty(tfAddress.getText(), tfDescription.getText(), tfYear.getText(), tfAgentName.getText(), cbPropertyType.getValue() != null ? cbPropertyType.getValue().name() : "")) {
            return;
        }

        Customer associatedCustomer = cbAssociatedCustomer.getValue();
        if (associatedCustomer != null) {
            int customerId = associatedCustomer.getCustomerId();
            dao.addProperty(tfAddress.getText(), tfDescription.getText(), tfYear.getText(), tfAgentName.getText(), cbPropertyType.getValue(), customerId);

            // Show success message
            MessageBox.getInstance().showInfo("Property created successfully.");
            // Clear input fields
            clearFields();
        } else {
            // Handle the case when no customer is selected
            MessageBox.getInstance().showError("Please select an associated customer.");
        }
    }

    private void clearFields() {
        tfAddress.clear();
        tfDescription.clear();
        tfYear.clear();
        tfAgentName.clear();
        cbPropertyType.setValue(null);
        cbAssociatedCustomer.setValue(null);
    }
}
