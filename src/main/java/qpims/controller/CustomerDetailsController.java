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
import qpims.model.QPropertyDAO;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class CustomerDetailsController implements Initializable {
    
    
    

    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPhone;
   
    private Customer selectedCustomer;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    
        
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

}
