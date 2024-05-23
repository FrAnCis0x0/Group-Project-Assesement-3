package qpims;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * JavaFX QProperty
 */
public class QProperty extends Application {

    private static Scene scene;
    private static BorderPane sharedBorderPane;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("view/login"), 1000, 600);
        stage.setScene(scene);
        stage.setTitle("QProperty Information Management System");
        stage.setResizable(false);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(QProperty.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    //Takes in fxml file name and references a border pane from main view to change/display the center view.
    public static void setBorderCenter(String fileName){
        try {
            Pane view;
            FXMLLoader loader = new FXMLLoader(QProperty.class.getResource("view/"+fileName+".fxml"));
            view = loader.load();
            sharedBorderPane.setCenter(view);
        } catch (IOException ex) {
            Logger.getLogger(QProperty.class.getName()).log(Level.SEVERE, "Failed to load view "+fileName, ex);
        }
    }
    
    public static void setSharedBorderPane(BorderPane pane){
        sharedBorderPane = pane;
    }

}