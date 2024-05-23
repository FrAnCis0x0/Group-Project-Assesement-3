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
    private TextField tfStaffName;
    @FXML
    private TextField tfCharge;
    @FXML
    private DatePicker dpBookingDate;
    @FXML
    private DatePicker dpCompletionDate;
    @FXML
    private ComboBox<?> cbPropertyID;
    @FXML
    private ComboBox<?> cbJobType;
    
    @FXML
    private TableView<Booking> tbDisplay;
    @FXML
    private TextField tfSearch;
    @FXML
    private TextArea taDescription;
    private QPropertyDAO dao;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = QPropertyDAO.getInstance();
    }    

    @FXML
    private void goToBookingView(ActionEvent event) {
        QProperty.setBorderCenter("booking");
        
    }

    @FXML
    private void createBooking(ActionEvent event) {
    }
    
    @FXML
    private void goToCreateBooking(){
        QProperty.setBorderCenter("createBooking");

    }
    
    @FXML
    private void deteleBooking(){}
    
    @FXML
    private void updateBooking(){}
}
