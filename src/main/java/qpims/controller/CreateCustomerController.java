package qpims.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import qpims.QProperty;
import qpims.model.MessageBox;
import qpims.model.QPropertyDAO;
import qpims.model.Validate;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateCustomerController implements Initializable {

    // FXML variables for the UI components
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPhone;

    private QPropertyDAO dao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = QPropertyDAO.getInstance();
    }

    // Go back to customer view
    @FXML
    private void goToCustomerView(ActionEvent event) {
        clearInputs();
        QProperty.setBorderCenter("customer");
    }

    // Create a new customer and add to the database
    @FXML
    private void createCustomer(ActionEvent event) {
        // Validate inputs
        if (!Validate.getInstance().validateCustomer(tfFirstName.getText(), tfLastName.getText(), tfEmail.getText(), tfPhone.getText())) {
            return;
        }
        dao.addCustomer(tfFirstName.getText(), tfLastName.getText(), tfEmail.getText(), tfPhone.getText());
        // Show success message
        MessageBox.getInstance().showInfo("Customer created successfully.");
        clearInputs();
        // Go back to customer view
        QProperty.setBorderCenter("customer");
    }

    // Clear the input fields
    @FXML
    private void clear() {
        clearInputs();
    }

    private void clearInputs() {
        tfFirstName.setText("");
        tfLastName.setText("");
        tfEmail.setText("");
        tfPhone.setText("");
    }
}
