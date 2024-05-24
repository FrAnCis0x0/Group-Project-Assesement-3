/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package qpims.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import qpims.QProperty;
import qpims.model.Property;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author renza
 */
public class CreatePropertyController implements Initializable {

    @FXML
    private Button btnCreate;

    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfAgentName;
 
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
    private void createProperty(){

    }

}
