package qpims.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import qpims.QProperty;
import qpims.model.Customer;
import qpims.model.MessageBox;
import qpims.model.QPropertyDAO;
import qpims.model.Validate;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class CreateCustomerController implements Initializable {

 
    
    
    
 
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

    @FXML
    private void goToCustomerView(ActionEvent event) {
        clearInputs();
        QProperty.setBorderCenter("customer");
    }
    
    @FXML
    private void createCustomer(ActionEvent event) {
        //validate inputs
        if(!Validate.getInstance().validateCustomer(tfFirstName.getText(), tfLastName.getText(), tfEmail.getText(), tfPhone.getText())){
            return;
        }
        dao.addCustomer(tfFirstName.getText(), tfLastName.getText(), tfEmail.getText(), tfPhone.getText());
        //show success message
        MessageBox.getInstance().showInfo("Customer created successfully.");
        clearInputs();
        //go back to customer view
        QProperty.setBorderCenter("customer");
    }
    
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
