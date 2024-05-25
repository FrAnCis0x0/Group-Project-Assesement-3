package qpims.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import qpims.QProperty;
import qpims.model.MessageBox;
import qpims.model.QPropertyDAO;
import javax.swing.*;

public class LoginController implements Initializable {

    @FXML
    private Text tfDbStatus;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    private String status;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        status = QPropertyDAO.getInstance().getDBStatus();
        //initialize the database status
        tfDbStatus.setText(status);
    }    

    @FXML
    private void signUp(ActionEvent event) throws IOException {
        if(status.equals("Online")){
            QProperty.setRoot("view/user");
        }else{
            showErrorMessage();
        
        }
    }
    private boolean isCorrectCredentials(){
        //check user credentials are correct
        return QPropertyDAO.getInstance().getUserByUsernameAndPassword(tfUsername.getText(), tfPassword.getText()) != null;
    }

    @FXML
    private void Login(ActionEvent event) throws IOException {
        if(status.equals("Online")){
            //check user credentials
            if(isCorrectCredentials()) {
                //go to main view
                QProperty.setRoot("view/main");
            }else{
                //show error message
                MessageBox.getInstance().showError("Invalid username or password.");
            }
        }else{
            showErrorMessage();
        }
    }
    
    private void showErrorMessage(){
        //show error message
        MessageBox.getInstance().showError("Database is offline. Please try again later.");
        
    }
    
}
