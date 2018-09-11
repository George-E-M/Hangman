package Application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * things to do
 * add a diagram to show how many moves left
 * give all guessed letters and you can't repeat
 */

/**
 * This class provides functionality for the play menu
 */
public class PlayController implements Initializable {
    @FXML
    TextField textField;
    @FXML
    Button guessButton;
    @FXML
    Label wordDisplay;
    @FXML
    Label instructionLabel;
    @FXML
    Label guessesLabel;
    @FXML
    Button quitButton;
    String word;
    Boolean hasWon;
    Boolean hasLost;
    int guessesLeft;

    /**
     * This method is executed when the user presses the guess button.
     */
    @FXML
    private void guess() {
        String input = textField.getText();
        //Checks if the game is done, because when the game is done this button is instead used to return to the start.
        if (checkIfDone()) {
            //this is done to ensure the rest of the function isn't executed if the game is over
        } else if(checkIllegalCharacters(input)){
            //If the user is trying to guess a letter in the word
            if (input.length() == 1) {
                guessCharacter(input);
            } else if (input.length() == word.length()) {
                //if the user is trying to guess the word
                guessWord(input);
            } else {
                //If the user doesn't enter a valid string
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect input");
                alert.setHeaderText("The value you input was invalid");
                alert.setContentText("Please ensure the value you enter is either a single character\n" +
                        "or is the length of the word you are guesing");
                alert.showAndWait();
            }

            //Checks if the user has lost
            if (guessesLeft == 0) {
                hasLost = true;
            }
            //Checks if the user has won by getting all the letter
            if (!wordDisplay.getText().contains("_")) {
                hasWon = true;
            }
            //Updates the UI based on whether they have won or lost
            if (hasWon) {
                instructionLabel.setText("Congratulations you got the word");
                guessButton.setText("Return to Start Menu");
            } else if (hasLost) {
                instructionLabel.setText("Better luck next time the word was " + word);
                guessButton.setText("Return to the Start Menu");
            }
        }
    }

    /**
     * Checks if the user has finished their game.
     * @return
     */
    private Boolean checkIfDone() {
        if (hasWon || hasLost) {
            returnToStart();
            return true;
        }
        return false;
    }

    /**
     * Checks that the characters match the allowed characters, letters only and length is less than or equal
     * to 25
     */
    private Boolean checkIllegalCharacters(String input) {
        if (input.matches("[a-zA-Z]+")) {
            return true;
        } else {
            //Sends an alert if the input is invalid
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Illegal Characters Used");
            alert.setHeaderText("Please use only letters");
            alert.showAndWait();
            return false;
        }
    }

    /**
     * This function is used to handle inputs which are guessing a character
     * @param input the character the user is guessing
     */
    private void guessCharacter(String input) {
        //Checks the character is in the word
        if (word.toLowerCase().contains(input)) {
            //Gets the input character
            Character letter = input.charAt(0);
            //gets the index of the first ocurrence of the character
            int index = word.toLowerCase().indexOf(input);
            //Initialises the string to update the GUI with
            String updatedText = wordDisplay.getText();
            //loops through to find all ocurrences of the character
            while (index >= 0) {
                //Creates a character array to make dealing with the string easier
                char[] wordArray = updatedText.toCharArray();
                //Takes into account the spaces which increase visibility
                wordArray[2 * index] = letter;
                //Changes back to a string
                updatedText = String.valueOf(wordArray);
                //Gets the index of the next ocurrence of the character returns -1 if the character doesn't
                //occur
                index = word.toLowerCase().indexOf(input, index + 1);
            }
            wordDisplay.setText(updatedText);
        } else {
            guessesLeft -= 1;
            guessesLabel.setText("You have " + guessesLeft + " guesses remaining");
        }
    }

    /**
     *
     */
    private void guessWord(String input) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("You are making a guess");
        alert.setHeaderText("Are you sure you want to guess " + input + " as the word");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            if (Objects.equals(word.toLowerCase(), input)) {
                hasWon = true;
                wordDisplay.setText(word);
            } else {
                guessesLeft -= 1;
                guessesLabel.setText("You have " + guessesLeft + " incorrect guesses remaining");
            }
        }
    }
    /**
     * This method is executed when the user presses the quit button, it then confirms the user wants to leave
     * before calling the returnToStart method.
     */
    @FXML
    private void returnToMenu() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Return to Menu");
        alert.setHeaderText("Are you sure you want to return to the Start Menu");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            returnToStart();
        }
    }

    /**
     * This method is used to return the user to the start menu.
     */
    private void returnToStart() {
        try {
            Parent createScene = FXMLLoader.load(getClass().getResource("StartMenu.fxml"));
            Scene newScene = new Scene(createScene, 600, 400);
            Stage stage = (Stage) guessButton.getScene().getWindow();
            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateDisplay() {


    }

    /**
     * This is used to pass the word from the startmenucontroller to the playcontroller and then it initialises
     * the word display
     * @param word the word the user is guessing
     */
    public void setWord(String word) {
        this.word = word;
        int size = word.length();
        for (int i = 0; i < size; i++) {
            wordDisplay.setText(wordDisplay.getText() + "_ ");
        }
    }

    /**
     * Initializes the number of guesses and a number of other variables.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        guessesLeft = 15;
        hasWon = false;
        hasLost = false;
    }
}
