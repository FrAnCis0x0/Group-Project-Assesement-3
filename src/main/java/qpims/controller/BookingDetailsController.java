package qpims.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import qpims.QProperty;
import qpims.model.Booking;
import qpims.model.QPropertyDAO;

import java.net.URL;
import java.util.ResourceBundle;


public class BookingDetailsController implements Initializable {

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
    private void deteleBooking(){}


    @FXML
    private void updateBooking(){}
}
