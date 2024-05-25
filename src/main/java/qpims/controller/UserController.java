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
        if(!validateInputs()){
            return;
        }
        //create user
        dao.addUser(tfFirstName.getText(), tfLastName.getText(), tfEmail.getText(), tfUsername.getText(), tfPassword.getText());
        MessageBox.getInstance().showInfo("User created successfully.");
        //go back to login view
        try {
            QProperty.setRoot("view/login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private boolean validateInputs(){
        if(tfFirstName.getText().isEmpty() || tfLastName.getText().isEmpty() || tfEmail.getText().isEmpty() || tfUsername.getText().isEmpty() || tfPassword.getText().isEmpty()){
            MessageBox.getInstance().showError("All fields are required.");
            return false;
        }
        if(!tfEmail.getText().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")){
            MessageBox.getInstance().showError("Invalid email address. Email must be in the format example@example.com.");
            return false;
        }
        if(!tfUsername.getText().matches("^[a-zA-Z0-9_]{5,20}$")){
            MessageBox.getInstance().showError("Invalid username. Username must be between 5 and 20 characters.");
            return false;
        }

        //password must be at least 5 characters
        if(tfPassword.getText().length() < 5){
            MessageBox.getInstance().showError("Invalid password. Password must be at least 5 characters.");
            return false;
        }
        return true;
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
