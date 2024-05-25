package qpims.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import qpims.QProperty;
import qpims.model.Customer;
import qpims.model.QPropertyDAO;
import qpims.model.Validate;


public class CustomerController implements Initializable {

    @FXML
    private TableView<Customer> tbDisplay;
    //Table columns
    @FXML
    private TableColumn<Customer, Integer> colId;
    @FXML
    private TableColumn<Customer, String> colFirstName;
    @FXML
    private TableColumn<Customer, String> colLastName;
    @FXML
    private TableColumn<Customer, String> colEmail;
    @FXML
    private TableColumn<Customer, String> colPhone;
    
    
    
    
    
    @FXML
    private TextField tfSearch;

    private List<Customer> customerList;
    private Customer selectedCustomer;
    private ObservableList<Customer> customerObservableList;
    private boolean isAllowed; //check if search is allowed to prevent multiple database calls
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set table view columns
        colId.setCellValueFactory( new PropertyValueFactory<>("customerId"));
        colFirstName.setCellValueFactory( new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory( new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory( new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory( new PropertyValueFactory<>("phone"));
        
        //center columns
        colId.setStyle("-fx-alignment: CENTER;");
        colFirstName.setStyle("-fx-alignment: CENTER;");
        colLastName.setStyle("-fx-alignment: CENTER;");
        colEmail.setStyle("-fx-alignment: CENTER;");
        colPhone.setStyle("-fx-alignment: CENTER;");
        
        
        //Create observable list
        customerObservableList = FXCollections.observableArrayList();
        
        //Get all customers from database
        customerList = QPropertyDAO.getInstance().getAllCustomers();
        customerObservableList.addAll(customerList);
        //Set tableview items
        tbDisplay.setItems(customerObservableList);
        //Get selected customer from tableview
        tbDisplay.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedCustomer = newSelection; //Set selected customer
                
                //send selected customer to customer details controller
                QProperty.sendDataToController("customerDetails", selectedCustomer);
                
            }
        });
        //set isAllowed to true
        isAllowed = true;
        
        //Add listener to search textfield
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty()){
                if(isAllowed){
                    customerObservableList.clear();
                    customerObservableList.addAll(QPropertyDAO.getInstance().getAllCustomers());
                    isAllowed = false;
                }
            }else{
                //validate search input
                if(!Validate.getInstance().validateSearchInput(newValue)){
                    tfSearch.clear();
                    return;
                }
                //search customer by name or phone
                //update observable list
                customerObservableList.clear();
                customerObservableList.addAll(QPropertyDAO.getInstance().searchCustomerByNameOrPhone(newValue));
                isAllowed = true;
                
            }
        });
        
        
    }    
    

    @FXML
    private void clearSearch(ActionEvent event) {
        tfSearch.clear();
    }
    
    @FXML
    private void goToCreateCustomer(){
        QProperty.setBorderCenter("createCustomer");
        
    }
}
