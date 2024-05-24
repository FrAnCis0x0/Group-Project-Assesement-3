package qpims.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import qpims.QProperty;
import qpims.model.Booking;
import qpims.model.QPropertyDAO;


public class BookingController implements Initializable {

  
    
    @FXML
    private TableView<Booking> tbDisplay;
    @FXML
    private TextField tfSearch;

    private QPropertyDAO dao;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = QPropertyDAO.getInstance();
    }    

   

   
    
    @FXML
    private void goToCreateBooking() {
        QProperty.setBorderCenter("createBooking");
    
    }
 
}
