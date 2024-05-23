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

    @FXML
    private void Login(ActionEvent event) throws IOException {
        if(status.equals("Online")){
            QProperty.setRoot("view/main");
        }else{
            showErrorMessage();
        }
    }
    
    private void showErrorMessage(){
        //show error message
        JOptionPane optionPane = new JOptionPane("Database is offline. Please check the connection", JOptionPane.ERROR_MESSAGE);
        JDialog dialog = optionPane.createDialog("Failure");
        dialog.setAlwaysOnTop(true);//set the dialog always on top
        dialog.setVisible(true);
        
    }
    
}
