package qpims.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import qpims.QProperty;
import qpims.model.Customer;
import qpims.model.QPropertyDAO;


public class CustomerController implements Initializable {

    @FXML
    private TableView<Customer> tbDisplay;
    @FXML
    private TextField tfSearch;
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPhone;
    private List<Customer> customerList;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Todo - create observer for customer list
        customerList = QPropertyDAO.getInstance().getAllCustomers();
        
    }    

    @FXML
    private void goToCustomerView(ActionEvent event) {
        QProperty.setBorderCenter("customer");
    }

    @FXML
    private void deleteCustomer(ActionEvent event) {
    }

    @FXML
    private void updateCustomer(ActionEvent event) {
    }
    
    @FXML
    private void goToCreateCustomer(){
        QProperty.setBorderCenter("createCustomer");
    }
}
