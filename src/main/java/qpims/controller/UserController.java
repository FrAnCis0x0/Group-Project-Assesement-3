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
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import qpims.QProperty;
import qpims.model.MessageBox;
import qpims.model.QPropertyDAO;

public class UserController implements Initializable {
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfUsername;
    private QPropertyDAO dao;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialize data access object
        dao = QPropertyDAO.getInstance();
        
    }    

    @FXML
    private void goToLoginView(ActionEvent event) throws IOException {
        QProperty.setRoot("view/login");
    }
    
    @FXML
    private void createUser(){
        //create user
        dao.addUser(tfFirstName.getText(), tfLastName.getText(), tfEmail.getText(), tfUsername.getText(), tfPassword.getText());
        MessageBox.getInstance().showInfo("User created successfully.");
        clearInputs();
    }
    
    private void clearInputs(){
        tfFirstName.clear();
        tfLastName.clear();
        tfEmail.clear();
        tfUsername.clear();
        tfPassword.clear();
    }
    @FXML
    private void clear(){
        clearInputs();
    }
    
}
