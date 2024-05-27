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

    @FXML
    private TableView<Booking> tbDisplay;
    @FXML
    private TableColumn<Booking, Integer> colJobId;  // Corrected: Only one colJobId
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
    private TextField tfSearch;

    private List<Booking> bookingList;
    private Booking selectedBooking;
    private ObservableList<Booking> bookingObservableList;
    private boolean isAllowed;

    private QPropertyDAO dao;

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

        // Center columns
        colJobId.setStyle("-fx-alignment: CENTER;");
        colDescription.setStyle("-fx-alignment: CENTER;");
        colBookingDate.setStyle("-fx-alignment: CENTER;");
        colCompletionDate.setStyle("-fx-alignment: CENTER;");
        colCharge.setStyle("-fx-alignment: CENTER;");
        colStaffName.setStyle("-fx-alignment: CENTER;");
        colJobType.setStyle("-fx-alignment: CENTER;");

        // Create observable list
        bookingObservableList = FXCollections.observableArrayList();

        // Get all bookings from database
        dao = QPropertyDAO.getInstance();
        bookingList = dao.getAllBookings();
        bookingObservableList.addAll(bookingList);

        // Set tableview items
        tbDisplay.setItems(bookingObservableList);

        // Get selected booking from tableview
        tbDisplay.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedBooking = newSelection; // Set selected booking
                // Send selected booking to booking details controller
                QProperty.sendDataToController("bookingDetails", selectedBooking);
            }
        });

        // Set isAllowed to true
        isAllowed = true;

        // Add listener to search textfield
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                if (isAllowed) {
                    bookingObservableList.clear();
                    bookingObservableList.addAll(dao.getAllBookings());
                    isAllowed = false;
                }
            } else {
                // Validate search input
                if (!Validate.getInstance().validateSearchInput(newValue)) {
                    tfSearch.clear();
                    return;
                }
                // Search booking by description
                bookingObservableList.clear();
                bookingObservableList.addAll(dao.searchBookingByAddress(newValue));
                isAllowed = true;
            }
        });
    }

    @FXML
    private void clearSearch(ActionEvent event) {
        tfSearch.clear();
    }

    @FXML
    private void goToCreateBooking() {
        QProperty.setBorderCenter("createBooking");
    }
}
