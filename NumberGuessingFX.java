import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Random;

public class NumberGuessingFX extends Application {

    private int targetNumber;
    private int attempts = 0;
    private final int MAX_ATTEMPTS = 7;

    private Label instructionLabel;
    private Label attemptsLabel;
    private TextField guessInput;
    private Button guessButton;
    private Label feedbackLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("数当てゲーム");

        Random random = new Random();
        targetNumber = random.nextInt(100) + 1;

        instructionLabel = new Label("1から100までの数字を当ててください。");
        attemptsLabel = new Label("残り試行回数: " + (MAX_ATTEMPTS - attempts));
        guessInput = new TextField();
        guessInput.setPromptText("数字を入力");
        guessButton = new Button("予想する");
        feedbackLabel = new Label("");

        guessButton.setOnAction(e -> handleGuess());

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(
            instructionLabel,
            attemptsLabel,
            guessInput,
            guessButton,
            feedbackLabel
        );

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleGuess() {
        if (attempts >= MAX_ATTEMPTS) {
            return;
        }

        String inputText = guessInput.getText();
        int guess;

        try {
            guess = Integer.parseInt(inputText);
            if (guess < 1 || guess > 100) {
                feedbackLabel.setText("無効な入力です。1から100の数字を入力してください。");
                guessInput.clear();
                return;
            }
        } catch (NumberFormatException e) {
            feedbackLabel.setText("無効な入力です。数字を入力してください。");
            guessInput.clear();
            return;
        }

        attempts++;
        attemptsLabel.setText("残り試行回数: " + (MAX_ATTEMPTS - attempts));

        if (guess < targetNumber) {
            feedbackLabel.setText("もっと大きい数字です。");
        }
        if (guess > targetNumber) {
            feedbackLabel.setText("もっと小さい数字です。");
        }
        if (guess == targetNumber) {
            feedbackLabel.setText("正解！ " + attempts + "回で当たりました！");
            endGame();
        }
        if (attempts >= MAX_ATTEMPTS) {
            feedbackLabel.setText("残念！試行回数を超えました。\n正解は " + targetNumber + " でした。");
            endGame();
        }

        guessInput.clear();
        guessInput.requestFocus();
    }

    private void endGame() {
        guessInput.setDisable(true);
        guessButton.setDisable(true);
    }
} 