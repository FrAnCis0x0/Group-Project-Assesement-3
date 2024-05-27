package qpims.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import qpims.QProperty;
import qpims.model.Booking;
import qpims.model.JobType;
import qpims.model.MessageBox;
import qpims.model.Property;
import qpims.model.QPropertyDAO;
import qpims.model.Validate;

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
    private ComboBox<Property> cbPropertyID;
    @FXML
    private ComboBox<JobType> cbJobType;
    @FXML
    private TextArea taDescription;

    private Booking selectedBooking;
    private QPropertyDAO dao;
    private ObservableList<Property> propertyObservableList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = QPropertyDAO.getInstance();

        // Populate the job type ComboBox with enum values
        cbJobType.setItems(FXCollections.observableArrayList(JobType.values()));

        // Initialize the property ComboBox
        propertyObservableList = FXCollections.observableArrayList();
        List<Property> propertyList = dao.getAllProperty();
        if (propertyList != null) {
            propertyObservableList.addAll(propertyList);
        }
        cbPropertyID.setItems(propertyObservableList);
    }

    private void goBack() {
        QProperty.setBorderCenter("booking");
    }

    @FXML
    private void goToRepairJobView(ActionEvent event) {
        goBack();
    }

    @FXML
    private void deleteBooking(ActionEvent event) {
        dao.deleteBookingById(selectedBooking.getJobId());
        MessageBox.getInstance().showInfo("Booking deleted successfully.");
        goBack();
    }

    @FXML
    private void updateBooking(ActionEvent event) {
        if (!Validate.getInstance().validateBooking(
                taDescription.getText(),
                dpBookingDate.getValue().toString(),
                dpCompletionDate.getValue().toString(),
                tfCharge.getText(),
                tfStaffName.getText(),
                cbJobType.getValue() != null ? cbJobType.getValue().name() : "",
                cbPropertyID.getValue() != null ? String.valueOf(cbPropertyID.getValue().getPropertyId()) : ""
        )) {
            return;
        }

            double charge = Double.parseDouble(tfCharge.getText());
            dao.updateBooking(
                    selectedBooking.getJobId(),
                    cbPropertyID.getValue().getPropertyId(),
                    taDescription.getText(),
                    dpBookingDate.getValue().toString(),
                    dpCompletionDate.getValue().toString(),
                    charge,
                    tfStaffName.getText(),
                    cbJobType.getValue()
            );
            MessageBox.getInstance().showInfo("Booking updated successfully.");

    }

    public void setData(Booking booking)
    {
        selectedBooking = booking;
        taDescription.setText(selectedBooking.getDescription());
        dpBookingDate.setValue(LocalDate.ofEpochDay(selectedBooking.getBookingDate().toEpochDay()));
        dpCompletionDate.setValue(LocalDate.ofEpochDay(selectedBooking.getCompletionDate().toEpochDay()));
        tfCharge.setText(String.valueOf(selectedBooking.getCharge()));
        tfStaffName.setText(selectedBooking.getStaffName());
        cbJobType.setValue(selectedBooking.getJobType());

        for (Property property : propertyObservableList)
        {
            if (property.getPropertyId() == selectedBooking.getPropertyId())
            {
                cbPropertyID.setValue(property);
                break;
            }
        }
    }
}
