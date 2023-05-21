package iessfranciscodelosrios.proyecto3.GestorAutocares;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        Parent root = loadFXML("login");
        scene = new Scene(root, 507, 400);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void setRoot(String fxml) throws IOException {
        Parent p = loadFXML(fxml);
        if (primaryStage != null) {
            Scene newScene;
            if (fxml.equals("login")) {
                newScene = createScene(fxml, 507, 400);
                primaryStage.setResizable(false);
            } else if (fxml.equals("ConsumerRegister")) {
                newScene = createScene(fxml, 396, 520);
                primaryStage.setResizable(false);
            }else if (fxml.equals("JourneyRegister")) {
                    newScene = createScene(fxml, 396, 379); 
                    primaryStage.setResizable(false);
            } else {
                newScene = createScene(fxml, 755, 400);
                primaryStage.setResizable(true);
            }
            Platform.runLater(() -> {
                primaryStage.setScene(newScene);
                scene.setRoot(p);
            });
        } else {
        	//poner mesaje de error
            System.out.println("Stage is null");
        }
    }

    private static Scene createScene(String fxml, double width, double height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        return new Scene(root, width, height);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
