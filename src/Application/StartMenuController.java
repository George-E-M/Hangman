package Application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This controller provides functionality for the start menu.
 */
public class StartMenuController implements Initializable {
    @FXML
    TextField textField;
    @FXML
    Button continueButton;
    String word;
    List<String> wordsList;

    /**
     * This method is run if the user selects continue, it first gets the word the user will be guessing,
     * checks the input and then swaps to the Play scene.
     */
    @FXML
    private void beginPlaying() {
        //This gets the word the user will be guessing
        getWord();
        //Does nothing if the characters are illegal, otherwise load the Play scene.
        if (checkIllegalCharacters()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Play.fxml"));
                Parent createScene = fxmlLoader.load();
                //These next two lines are used to pass the word to the Play controller
                PlayController controller = fxmlLoader.getController();
                controller.setWord(word);
                Scene newScene = new Scene(createScene, 600, 400);
                Stage stage = (Stage) textField.getScene().getWindow();
                stage.setScene(newScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used to get the word the user has input, if the user didn't input a word a random word is
     * taken from a word.txt file in the current working directory.
     */
    private void getWord() {
        if (textField.getText().isEmpty()) {
            int index = (int)(Math.random()*wordsList.size());
            word = wordsList.get(index);
        } else {
            word = textField.getText().trim();
        }
    }

    /**
     * Checks that the characters match the allowed characters, letters only and length is less than or equal
     * to 25
     */
    private Boolean checkIllegalCharacters() {
        if (word.matches("[a-zA-Z]+") && textField.getText().length() <= 25) {
            return true;
        } else {
            //Sends an alert if the input is invalid
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Illegal Characters Used");
            alert.setHeaderText("Please use only letters");
            alert.setContentText("Max 25 characters");
            alert.showAndWait();
            return false;
        }
    }

    /**
     * Gets a list of words from a txt file to select from if the user does not input a word to guess.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wordsList = new ArrayList<>();
        File file = new File("words.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String fileWord;
            while ((fileWord = reader.readLine()) != null) {
                wordsList.add(fileWord);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
