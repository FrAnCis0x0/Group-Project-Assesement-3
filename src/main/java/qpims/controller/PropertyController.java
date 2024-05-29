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
import qpims.model.Property;
import qpims.model.QPropertyDAO;
import qpims.model.Validate;

public class PropertyController implements Initializable {

    // FXML variables for the UI components
    @FXML
    private TableView<Property> tbDisplay;
    @FXML
    private TableColumn<Property, Integer> colId;
    @FXML
    private TableColumn<Property, String> colAddress;
    @FXML
    private TableColumn<Property, String> colDescription;
    @FXML
    private TableColumn<Property, Integer> colBuiltYear;
    @FXML
    private TableColumn<Property, String> colAgentName;
    @FXML
    private TableColumn<Property, String> colPropertyType;

    @FXML
    private TextField tfSearch;


    private List<Property> propertyList; // List of properties from database
    private Property selectedProperty; // Selected property from tableview
    private ObservableList<Property> propertyObservableList; // Observable list for the properties
    private boolean isAllowed; // Boolean to check if search is allowed

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set table view columns
        colId.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colBuiltYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colAgentName.setCellValueFactory(new PropertyValueFactory<>("agentName"));
        colPropertyType.setCellValueFactory(new PropertyValueFactory<>("propertyType"));

        // Center columns
        colId.setStyle("-fx-alignment: CENTER;");
        colAddress.setStyle("-fx-alignment: CENTER;");
        colDescription.setStyle("-fx-alignment: CENTER;");
        colBuiltYear.setStyle("-fx-alignment: CENTER;");
        colAgentName.setStyle("-fx-alignment: CENTER;");
        colPropertyType.setStyle("-fx-alignment: CENTER;");

        // Create observable list
        propertyObservableList = FXCollections.observableArrayList();

        propertyList = QPropertyDAO.getInstance().getAllProperty(); // Get all properties from database
        propertyObservableList.addAll(propertyList); // Add all properties to observable list

        // Set tableview items to observable list
        tbDisplay.setItems(propertyObservableList);

        // Get selected property from tableview
        tbDisplay.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            // Check if new selection is not null
            if (newSelection != null) {
                selectedProperty = newSelection; // Set selected property
                QProperty.sendDataToController("propertyDetails", selectedProperty); // Send selected property to propertyDetails
            }
        });

        // Set isAllowed to true to allow search
        isAllowed = true;

        // Add listener to search textfield
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                if (isAllowed) {
                    propertyObservableList.clear(); // Clear observable list
                    propertyObservableList.addAll(QPropertyDAO.getInstance().getAllProperty()); // Add all properties to observable list
                    isAllowed = false; // Set isAllowed to false to prevent multiple database calls
                }
            } else {
                // Validate search input
                if (!Validate.getInstance().validateSearchInput(newValue)) {
                    tfSearch.clear();
                    return; // If the input is invalid, clear the textfield and return
                }
                // Search property by address
                propertyObservableList.clear();
                propertyObservableList.addAll(QPropertyDAO.getInstance().searchPropertyByAddress(newValue));
                isAllowed = true; // Set isAllowed to true to allow search
            }
        });
    }

    // Clear search textfield
    @FXML
    private void clearSearch(ActionEvent event) {
        tfSearch.clear();
    }

    // Go to create property view
    @FXML
    private void goToCreateProperty() {
        QProperty.setBorderCenter("createProperty");
    }
}
