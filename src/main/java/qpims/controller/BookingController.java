package qpims.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import qpims.QProperty;
import qpims.model.Booking;
import qpims.model.QPropertyDAO;
import qpims.model.Validate;

public class BookingController implements Initializable {

    // FXML variables for the UI components
    @FXML
    private TableView<Booking> tbDisplay;
    @FXML
    private TableColumn<Booking, Integer> colJobId;
    @FXML
    private TableColumn<Booking, String> colDescription;
    @FXML
    private TableColumn<Booking, String> colBookingDate;
    @FXML
    private TableColumn<Booking, String> colCompletionDate;
    @FXML
    private TableColumn<Booking, String> colCharge;
    @FXML
    private TableColumn<Booking, String> colStaffName;
    @FXML
    private TableColumn<Booking, String> colJobType;
    @FXML
    private TableColumn<Booking, String> colAssociatedAddress;
    @FXML
    private TextField tfSearch;

    private List<Booking> bookingList; // List of bookings from database
    private Booking selectedBooking; // Selected booking from tableview
    private ObservableList<Booking> bookingObservableList; // Observable list for the bookings
    private boolean isAllowed; // Boolean to check if search is allowed

    private QPropertyDAO dao; //DAO for accessing the data

    // Initialize the controller class
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set table view columns
        colJobId.setCellValueFactory(new PropertyValueFactory<>("jobId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colBookingDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        colCompletionDate.setCellValueFactory(new PropertyValueFactory<>("completionDate"));
        colCharge.setCellValueFactory(new PropertyValueFactory<>("charge"));
        colStaffName.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        colJobType.setCellValueFactory(new PropertyValueFactory<>("jobType"));
        colAssociatedAddress.setCellValueFactory(new PropertyValueFactory<>("associatedAddress"));

        // Center columns
        colJobId.setStyle("-fx-alignment: CENTER;");
        colDescription.setStyle("-fx-alignment: CENTER;");
        colBookingDate.setStyle("-fx-alignment: CENTER;");
        colCompletionDate.setStyle("-fx-alignment: CENTER;");
        colCharge.setStyle("-fx-alignment: CENTER;");
        colStaffName.setStyle("-fx-alignment: CENTER;");
        colJobType.setStyle("-fx-alignment: CENTER;");
        colAssociatedAddress.setStyle("-fx-alignment: CENTER;");

        // Create observable list for bookings
        bookingObservableList = FXCollections.observableArrayList();

        // Get all bookings from database
        dao = QPropertyDAO.getInstance();
        bookingList = dao.getAllBookings();
        bookingObservableList.addAll(bookingList);

        // Set tableview items to observable list
        tbDisplay.setItems(bookingObservableList);

        // Get selected booking from tableview
        tbDisplay.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedBooking = newSelection; // Set selected booking
                QProperty.sendDataToController("bookingDetails", selectedBooking); // Send selected booking to booking details controller
            }
        });

        isAllowed = true; // Set isAllowed to true to allow search

        // Add listener to search textfield
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                refreshTableView();
                if (isAllowed) {
                    bookingObservableList.clear();
                    bookingObservableList.addAll(dao.getAllBookings());
                    isAllowed = false; // Set isAllowed to false to prevent search when textfield is empty
                }
            } else {
                // Validate search input using the Validate class
                if (!Validate.getInstance().validateSearchInput(newValue)) {
                    tfSearch.clear();
                    return; // Return if search input is invalid
                }
                // Search booking by description
                bookingObservableList.clear();
                bookingObservableList.addAll(dao.searchBookingByAddress(newValue));
                isAllowed = true; // Set isAllowed to true to allow search
            }
        });
    }

    // Clear search textfield
    @FXML
    private void clearSearch(ActionEvent event) {
        tfSearch.clear();
    }

    // Go to create booking view
    @FXML
    private void goToCreateBooking() {
        QProperty.setBorderCenter("createBooking");
    }
    private void refreshTableView() {
        // Ensure the table view is properly refreshed
        ObservableList<Booking> currentItems = tbDisplay.getItems();
        tbDisplay.setItems(null);
        tbDisplay.setItems(currentItems);

        // Ensure layout is properly updated
        tbDisplay.layout();

        // Scroll to the top-left corner to ensure proper display
        tbDisplay.scrollTo(0);
        tbDisplay.scrollToColumnIndex(0);
    }
}
