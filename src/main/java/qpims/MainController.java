/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package qpims;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author renza
 */
public class MainController implements Initializable {
    
    @FXML
    private Button btnCustomer;
    @FXML
    private Button btnProperty;
    @FXML
    private Button btnRepairJob;
    @FXML
    private Button btnManagerReport;
    
    
    @FXML
    private BorderPane mainPane;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mainPane.setCenter(getView("customer"));
    }
    
    @FXML
    private void gotoCustomerView(){
        mainPane.setCenter(getView("customer"));
    }
    @FXML
    private void gotoPropertyView(){
        mainPane.setCenter(getView("property"));
    }
    @FXML
    private void gotoRepairJobView(){
        mainPane.setCenter(getView("repairJob"));
    }
    @FXML
    private void gotoManagerReportView(){
        mainPane.setCenter(getView("managerReport"));
    }
    private Pane getView(String fileName){
        Pane view = null;
        try{
            FXMLLoader loader = new FXMLLoader(QProperty.class.getResource(fileName+".fxml"));
            view = loader.load();
            
        }
        catch(Exception e){
            e.printStackTrace();
            
        }
        return view;
    }

    @FXML
    private void LogOut(ActionEvent event) throws IOException {
        QProperty.setRoot("login");
    }
    
}
