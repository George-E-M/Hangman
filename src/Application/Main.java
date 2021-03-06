package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    //Starts the application at the StartMenu.
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("StartMenu.fxml"));
        primaryStage.setTitle("Hangman");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

	// Added comment
    public static void main(String[] args) {
        launch(args);
    }
}
