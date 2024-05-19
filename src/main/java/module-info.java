module qpims {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens qpims to javafx.fxml;
    exports qpims;
}
