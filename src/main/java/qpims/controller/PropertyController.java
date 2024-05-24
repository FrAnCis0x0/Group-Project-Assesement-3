/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package qpims.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import qpims.QProperty;
import qpims.model.Property;

/**
 * FXML Controller class
 *
 * @author renza
 */
public class PropertyController implements Initializable {

    @FXML
    private Button btnCreate;
    @FXML
    private TextField tfSearch;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfAgentName;
    @FXML
    private TableView<Property> tbDisplay;
    @FXML
    private ComboBox<?> cbAssociatedCustomer;
    @FXML
    private ComboBox<?> cbPropertyType;
    @FXML
    private TextField tfYear;
    @FXML
    private TextArea taDisplay;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void goToPropertyView(ActionEvent event) {
                QProperty.setBorderCenter("property");

        
    }
    @FXML
    private void goToCreateProperty(){
        QProperty.setBorderCenter("createProperty");

    }
    @FXML
    private void deleteProperty(){
        
    }
    
    @FXML
    private void updateProperty(){
        
    }
    @FXML
    private void createProperty(){

    }

}
