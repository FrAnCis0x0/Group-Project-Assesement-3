package qpims.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import qpims.QProperty;

public class AboutController implements Initializable {
    private Stage aboutStage;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    @FXML
    private void closeAboutStage(){
        //close the about stage
        aboutStage.close();
    }
    public void setAboutStage(Stage stage){
        //set the about stage
        this.aboutStage = stage;
    
    }
}
