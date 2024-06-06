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
import qpims.model.Booking;
import qpims.model.JobType;
import qpims.model.MessageBox;
import qpims.model.Property;
import qpims.model.QPropertyDAO;
import qpims.model.Validate;

public class BookingDetailsController implements Initializable {

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

    private Booking selectedBooking; // The selected booking to be updated
    private QPropertyDAO dao; //DAO for accessing the data
    private ObservableList<Property> propertyObservableList; //Observable list for the properties

    // Initialize the controller class
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = QPropertyDAO.getInstance();

        // Populate the job type ComboBox with enum values
        cbJobType.setItems(FXCollections.observableArrayList(JobType.values()));

        // Initialize the property ComboBox with the properties from the database
        propertyObservableList = FXCollections.observableArrayList();
        List<Property> propertyList = dao.getAllProperty();
        // Add the properties to the observable list if they are not null
        if (propertyList != null) {
            propertyObservableList.addAll(propertyList);
        }
        cbPropertyID.setItems(propertyObservableList);

        // Open the DatePicker's calendar when the user clicks on the DatePicker
        dpBookingDate.getEditor().setOnMouseClicked(event -> dpBookingDate.show());

        // Add a listener to the showing property of dpCompletionDate
        dpCompletionDate.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (isNowShowing) {
                // Show error message if booking date is not selected
                if (dpBookingDate.getValue() == null || dpBookingDate.getEditor().getText().isEmpty()) {
                    MessageBox.getInstance().showError("Please select a booking date first.");
                    dpCompletionDate.hide(); // Hide the completion date picker
                }
            }
        });

        // Add a listener to the mouse click event of dpCompletionDate's editor
        dpCompletionDate.getEditor().setOnMouseClicked(event -> {
            // Show error message if booking date is not selected
            if (dpBookingDate.getValue() == null || dpBookingDate.getEditor().getText().isEmpty()) {
                MessageBox.getInstance().showError("Please select a booking date first.");
                dpCompletionDate.hide(); // Hide the completion date picker
            } else {
                dpCompletionDate.show(); // Show the completion date picker
            }
        });

        dpCompletionDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate bookingDate = dpBookingDate.getValue();
                // Disable the date cell if it is empty, or if the booking date is null or the date is before the booking date
                setDisable(empty || bookingDate == null || date.compareTo(bookingDate) < 0);
            }
        });

    }

    // Go back to the repair job view
    private void goBack() {
        QProperty.setBorderCenter("booking");
    }
    // Event handlers to go back to the repair job view
    @FXML
    private void goToRepairJobView(ActionEvent event) {
        goBack();
    }

    // Event handlers to delete the booking
    @FXML
    private void deleteBooking(ActionEvent event) {
        dao.deleteBookingById(selectedBooking.getJobId());
        MessageBox.getInstance().showInfo("Booking deleted successfully."); // Show a success message box
        goBack();
    }

    // Event handlers to update the selected booking
    @FXML
    private void updateBooking(ActionEvent event) {
        // Validate the input fields using the Validate class
        if (!Validate.getInstance().validateBooking(
                taDescription.getText(),
                dpBookingDate.getValue().toString(),
                dpCompletionDate.getValue().toString(),
                tfCharge.getText(),
                tfStaffName.getText(),
                cbJobType.getValue() != null ? cbJobType.getValue().name() : "",
                cbPropertyID.getValue() != null ? String.valueOf(cbPropertyID.getValue().getPropertyId()) : ""
        )) {
            return; // If the inputs are invalid, return
        }

            double charge = Double.parseDouble(tfCharge.getText()); // Parse the charge to double value
           // Update the booking using the DAO
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

    // Set the data of the selected booking
    public void setData(Booking booking)
    {
        selectedBooking = booking;
        taDescription.setText(selectedBooking.getDescription());
        dpBookingDate.setValue(LocalDate.ofEpochDay(selectedBooking.getBookingDate().toEpochDay()));
        dpCompletionDate.setValue(LocalDate.ofEpochDay(selectedBooking.getCompletionDate().toEpochDay()));
        tfCharge.setText(String.valueOf(selectedBooking.getCharge()));
        tfStaffName.setText(selectedBooking.getStaffName());
        cbJobType.setValue(selectedBooking.getJobType());

        // Find and set the associated property
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
