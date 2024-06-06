package qpims.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import qpims.QProperty;
import qpims.model.JobType;
import qpims.model.MessageBox;
import qpims.model.Property;
import qpims.model.QPropertyDAO;
import qpims.model.Validate;

public class CreateBookingController implements Initializable {

    // FXML variables for the UI components
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

    private QPropertyDAO dao; // DAO for accessing the data
    private ObservableList<Property> propertyObservableList; // Observable list for the properties
    private ObservableList<JobType> jobTypeObservableList; // Observable list for the job types

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = QPropertyDAO.getInstance();

        // Initialize the property ComboBox
        propertyObservableList = FXCollections.observableArrayList();
        List<Property> propertyList = dao.getAllProperty();
        // Add the properties to the observable list if they are not null
        if (propertyList != null) {
            propertyObservableList.addAll(propertyList);
        }
        cbPropertyID.setItems(propertyObservableList);

        // Initialize the job type ComboBox with enum values
        jobTypeObservableList = FXCollections.observableArrayList(JobType.values());
        cbJobType.setItems(jobTypeObservableList);
    
        // Open the DatePicker's calendar when the user clicks on the DatePicker
        dpBookingDate.getEditor().setOnMouseClicked(event -> dpBookingDate.show());
    
        dpCompletionDate.getEditor().setOnMouseClicked(event -> {
            //return error message if booking date is not selected
            if(dpBookingDate.getValue() == null || dpBookingDate.getEditor().getText() == null){
                MessageBox.getInstance().showError("Please select a booking date first.");
                return;
            }
            dpCompletionDate.show();
        });
        
    
        dpCompletionDate.setOnShowing(event -> {
            
            //return error message if booking date is not selected
            if(dpBookingDate.getValue() == null || dpBookingDate.getEditor().getText() == null){
                MessageBox.getInstance().showError("Please select a booking date first.");
                event.consume(); // Prevent the DatePicker's calendar from being shown
            }
            //select the com
        });
    
        // Set the start date for the completion date DatePicker
        dpCompletionDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate bookingDate = dpBookingDate.getValue();
                // Disable the date cell if it is empty, or if the booking date is null or the date is before the booking date
                setDisable(empty || bookingDate == null || date.compareTo(bookingDate) < 0 );
            }
        });
    }

    // Go to the booking view
    @FXML
    private void goToBookingView(ActionEvent event) {
        QProperty.setBorderCenter("booking");
    }

    // Create a booking
    @FXML
    private void createBooking(ActionEvent event) {
        // Validate inputs using the Validate class
        if (!Validate.getInstance().validateBooking(
                taDescription.getText(),
                dpBookingDate.getEditor().getText() != null ? dpBookingDate.getValue().toString() : "",
                dpCompletionDate.getEditor().getText() != null ? dpCompletionDate.getValue().toString() : "",
                tfCharge.getText(),
                tfStaffName.getText(),
                cbJobType.getValue() != null ? cbJobType.getValue().name() : "",
                cbPropertyID.getValue() != null ? String.valueOf(cbPropertyID.getValue().getPropertyId()) : ""
        )) {
            return; // If the inputs are invalid, return
        }

        Property associatedProperty = cbPropertyID.getValue(); // Get the selected property
        int propertyId = associatedProperty.getPropertyId(); // Get the property ID
        double charge = Double.parseDouble(tfCharge.getText()); // Get the charge as a double value
        // Add the booking to the database
        dao.addBooking(
                propertyId,
                taDescription.getText(),
                dpBookingDate.getValue().toString(),
                dpCompletionDate.getValue().toString(),
                charge,
                tfStaffName.getText(),
                cbJobType.getValue()
        );
        MessageBox.getInstance().showInfo("Booking created successfully."); // Show a success message
        clearInputs(); // Clear the inputs after a successful booking
    }

    // Clear the inputs
    @FXML
    private void clear(ActionEvent event) {
        clearInputs();
    }

    //Method to clear all input fields
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
