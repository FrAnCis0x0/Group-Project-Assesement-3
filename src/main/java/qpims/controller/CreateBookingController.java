package qpims.controller;

import java.net.URL;
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
import qpims.model.JobType;
import qpims.model.MessageBox;
import qpims.model.Property;
import qpims.model.QPropertyDAO;
import qpims.model.Validate;

public class CreateBookingController implements Initializable {

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

    private QPropertyDAO dao;
    private ObservableList<Property> propertyObservableList;
    private ObservableList<JobType> jobTypeObservableList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = QPropertyDAO.getInstance();

        // Initialize the property ComboBox
        propertyObservableList = FXCollections.observableArrayList();
        List<Property> propertyList = dao.getAllProperty();
        if (propertyList != null) {
            propertyObservableList.addAll(propertyList);
        }
        cbPropertyID.setItems(propertyObservableList);

        // Initialize the job type ComboBox
        jobTypeObservableList = FXCollections.observableArrayList(JobType.values());
        cbJobType.setItems(jobTypeObservableList);
    }

    @FXML
    private void goToBookingView(ActionEvent event) {
        QProperty.setBorderCenter("booking");
    }

    @FXML
    private void createBooking(ActionEvent event) {
        // Validate inputs using the Validate class
        if (!Validate.getInstance().validateBooking(
                taDescription.getText(),
                dpBookingDate.getValue() != null ? dpBookingDate.getValue().toString() : "",
                dpCompletionDate.getValue() != null ? dpCompletionDate.getValue().toString() : "",
                tfCharge.getText(),
                tfStaffName.getText(),
                cbJobType.getValue() != null ? cbJobType.getValue().name() : "",
                cbPropertyID.getValue() != null ? String.valueOf(cbPropertyID.getValue().getPropertyId()) : ""
        )) {
            return;
        }

        Property associatedProperty = cbPropertyID.getValue();
        int propertyId = associatedProperty.getPropertyId();
        double charge = Double.parseDouble(tfCharge.getText());
        dao.addBooking(
                propertyId,
                taDescription.getText(),
                dpBookingDate.getValue().toString(),
                dpCompletionDate.getValue().toString(),
                charge,
                tfStaffName.getText(),
                cbJobType.getValue()
        );

        // Show success message
        MessageBox.getInstance().showInfo("Booking created successfully.");
        // Clear input fields
        clearInputs();
    }

    @FXML
    private void clear(ActionEvent event) {
        clearInputs();
    }

    private void clearInputs() {
        taDescription.clear();
        dpBookingDate.setValue(null);
        dpCompletionDate.setValue(null);
        tfCharge.clear();
        tfStaffName.clear();
        cbPropertyID.setValue(null);
        cbJobType.setValue(null);
    }
}
