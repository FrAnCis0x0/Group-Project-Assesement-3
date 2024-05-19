/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package qpims;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author renza
 */
public class RepairJobController implements Initializable {

    @FXML
    private ComboBox<?> cbPropertyID;
    @FXML
    private ComboBox<?> cbJobType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void goToRepairJobView(ActionEvent event) {
    }

    @FXML
    private void createRepairJob(ActionEvent event) {
    }
    
    @FXML
    private void goToCreateRepairJob(){}
}
