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
import qpims.model.Property;
import qpims.model.PropertyType;
import qpims.model.QPropertyDAO;
import qpims.model.MessageBox;
import qpims.model.Validate;

public class PropertyDetailsController implements Initializable {

    @FXML
    private TextField tfAddress;
    @FXML
    private TextArea tfDescription;
    @FXML
    private TextField tfYear;
    @FXML
    private TextField tfAgentName;
    @FXML
    private ComboBox<String> cbPropertyType;
    @FXML
    private ComboBox<Customer> cbAssociatedCustomer;

    private Property selectedProperty;
    private QPropertyDAO dao;
    private ObservableList<Customer> customerObservableList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = QPropertyDAO.getInstance();

        // Populate the property type ComboBox with enum values
        for (PropertyType type : PropertyType.values()) {
            cbPropertyType.getItems().add(type.name());
        }

        // Initialize the customer ComboBox
        customerObservableList = FXCollections.observableArrayList();
        List<Customer> customerList = dao.getAllCustomers();
        if (customerList != null) {
            customerObservableList.addAll(customerList);
        }
        cbAssociatedCustomer.setItems(customerObservableList);
    }

    private void goBack() {
        QProperty.setBorderCenter("property");
    }

    @FXML
    private void goToPropertyView(ActionEvent event) {
        goBack();
    }

    @FXML
    private void deleteProperty(ActionEvent event) {
        dao.deletePropertyById(selectedProperty.getPropertyId());
        MessageBox.getInstance().showInfo("Property deleted successfully.");
        goBack();
    }

    @FXML
    private void updateProperty(ActionEvent event) {
        if (!Validate.getInstance().validateProperty(tfAddress.getText(), tfDescription.getText(), tfYear.getText(), tfAgentName.getText(), cbPropertyType.getValue())) {
            return;
        }

        dao.updateProperty(selectedProperty.getPropertyId(), tfAddress.getText(), tfDescription.getText(), tfYear.getText(), tfAgentName.getText(), PropertyType.valueOf(cbPropertyType.getValue()), cbAssociatedCustomer.getValue().getCustomerId());
        MessageBox.getInstance().showInfo("Property updated successfully.");
    }

    public void setData(Property property) {
        selectedProperty = property;
        tfAddress.setText(selectedProperty.getAddress());
        tfDescription.setText(selectedProperty.getDescription());
        tfYear.setText(selectedProperty.getYear());
        tfAgentName.setText(selectedProperty.getAgentName());
        cbPropertyType.setValue(selectedProperty.getPropertyType().name());

        for (Customer customer : customerObservableList) {
            if (customer.getCustomerId() == selectedProperty.getCustomerId()) {
                cbAssociatedCustomer.setValue(customer);
                break;
            }
        }
    }
}
