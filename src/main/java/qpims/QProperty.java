package qpims;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import qpims.model.Customer;
import qpims.model.User;

/**
 * JavaFX QProperty
 */
public class QProperty extends Application {

    private static Scene scene; // The main scene of the application
    private static BorderPane sharedBorderPane; // The shared border pane to display the center view

    // Start the application
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("view/login"), 1000, 600); // Load the login view
        stage.setScene(scene);
        stage.setTitle("QProperty Information Management System"); // Set the title of the stage
        stage.setResizable(false); // Disable resizing of the stage
        // Handle the close request
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0); // Ensure JVM terminates
        });
        stage.show();
    }
    // Load the FXML file and set it as the root of the scene
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // Load the FXML file and return the parent node
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(QProperty.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch();
    }
    //Takes in fxml file name and references a border pane from main view to change/display the center view.
    public static  void setBorderCenter(String fileName){
        //Load the fxml file and set it as the center of the shared border pane
        try {
            Pane view;
            FXMLLoader loader = new FXMLLoader(QProperty.class.getResource("view/"+fileName+".fxml"));
            view = loader.load();
            sharedBorderPane.setCenter(view);
        } catch (IOException ex) {
            Logger.getLogger(QProperty.class.getName()).log(Level.SEVERE, "Failed to load view "+fileName, ex);
        }
    }

    //Takes in fxml file name and object to send to the controller of the fxml file
    public  static <T> void sendDataToController(String fileName, T object){
        try {
            Pane view;
            FXMLLoader loader = new FXMLLoader(QProperty.class.getResource("view/"+fileName+".fxml"));
            view = loader.load();//load the view
            //get controller from the loader
            T controller = loader.getController();
            //call a method in the controller and pass the object
            controller.getClass().getMethod("setData", object.getClass()).invoke(controller, object);
            //set the view to the center of the shared border pane
            sharedBorderPane.setCenter(view);
        } catch (IOException ex) {
            Logger.getLogger(QProperty.class.getName()).log(Level.SEVERE, "Failed to load view "+fileName, ex);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setSharedBorderPane(BorderPane pane){
        sharedBorderPane = pane;
    }

}