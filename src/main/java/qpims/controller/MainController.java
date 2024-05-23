/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package qpims.controller;

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
import qpims.QProperty;

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
        // change center to customer
        mainPane.setCenter(getView("customer"));
        //share reference to main pane
        QProperty.setSharedBorderPane(mainPane);
    }
    
    @FXML
    private void gotoCustomerView(){
        //change center to customer
        mainPane.setCenter(getView("customer"));
    }
    @FXML
    private void gotoPropertyView(){
        mainPane.setCenter(getView("property"));
    }
    @FXML
    private void gotoRepairJobView(){
        //change center to booking
        mainPane.setCenter(getView("booking"));
    }
    @FXML
    private void gotoManagerReportView(){
        //change center to manager report
        mainPane.setCenter(getView("managerReport"));
    }
    private Pane getView(String fileName){
        Pane view = null;
        try{
            //Load fxml file
            FXMLLoader loader = new FXMLLoader(QProperty.class.getResource("view/"+fileName+".fxml"));
            view = loader.load();
            
        }
        catch(Exception e){
            e.printStackTrace();
            
        }
        return view;
    }

    @FXML
    private void LogOut(ActionEvent event) throws IOException {
        QProperty.setSharedBorderPane(null);
        QProperty.setRoot("view/login");
    }
    
}
