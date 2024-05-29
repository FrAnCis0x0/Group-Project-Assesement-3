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


public class CustomerDetailsController implements Initializable {
    
    // FXML variables for the UI components
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPhone;
   
    private Customer selectedCustomer;// stores selected customer
    private QPropertyDAO dao; //data access object
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = QPropertyDAO.getInstance();
        
    }
    private void goBack(){
        QProperty.setBorderCenter("customer");
    }

    @FXML
    private void goToCustomerView(ActionEvent event) {
        //go back to customer view
        goBack();
    }

    @FXML
    private void deleteCustomer(ActionEvent event) {
        //delete customer from database
        dao.deleteCustomerById(selectedCustomer.getCustomerId());
        //go back to customer view
        goBack();
        
    }

    @FXML
    private void updateCustomer(ActionEvent event) {
        //validate inputs
        if(!Validate.getInstance().validateCustomer(tfFirstName.getText(), tfLastName.getText(), tfAddress.getText(), tfPhone.getText())){
            return;
        }
        //update customer in database
        dao.updateCustomer(selectedCustomer.getCustomerId(), tfFirstName.getText(), tfLastName.getText(), tfAddress.getText(), tfPhone.getText());
        //show success message
        MessageBox.getInstance().showInfo("Customer updated successfully.");
        
        
    }
    //set the data of the selected customer
    public void setData(Customer customer) {
        selectedCustomer = customer;
        tfFirstName.setText(selectedCustomer.getFirstName());
        tfLastName.setText(selectedCustomer.getLastName());
        tfAddress.setText(selectedCustomer.getEmail());
        tfPhone.setText(selectedCustomer.getPhone());
    }
}
