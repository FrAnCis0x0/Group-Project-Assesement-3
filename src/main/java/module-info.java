module qpims {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires java.desktop;
    
    
    opens qpims to javafx.fxml;
    opens qpims.controller to javafx.fxml;
    opens qpims.model to javafx.base;
    exports qpims;
}
