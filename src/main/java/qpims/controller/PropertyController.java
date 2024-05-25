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

    private List<Property> propertyList;
    private Property selectedProperty;
    private ObservableList<Property> propertyObservableList;
    private boolean isAllowed;

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

        // Get all properties from database
        propertyList = QPropertyDAO.getInstance().getAllProperty();
        propertyObservableList.addAll(propertyList);

        // Set tableview items
        tbDisplay.setItems(propertyObservableList);

        // Get selected property from tableview
        tbDisplay.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedProperty = newSelection; // Set selected property
                // Send selected property to property details controller
                QProperty.sendDataToController("propertyDetails", selectedProperty);
            }
        });

        // Set isAllowed to true
        isAllowed = true;

        // Add listener to search textfield
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                if (isAllowed) {
                    propertyObservableList.clear();
                    propertyObservableList.addAll(QPropertyDAO.getInstance().getAllProperty());
                    isAllowed = false;
                }
            } else {
                // Validate search input
                if (!Validate.getInstance().validateSearchInput(newValue)) {
                    tfSearch.clear();
                    return;
                }
                // Search property by address
                propertyObservableList.clear();
                propertyObservableList.addAll(QPropertyDAO.getInstance().searchPropertyByAddress(newValue));
                isAllowed = true;
            }
        });
    }

    @FXML
    private void clearSearch(ActionEvent event) {
        tfSearch.clear();
    }

    @FXML
    private void goToCreateProperty() {
        QProperty.setBorderCenter("createProperty");
    }
}
