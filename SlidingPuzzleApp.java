import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SlidingPuzzleApp extends Application {
    private PuzzleBoard board;
    private Label moveLabel;
    private Label scoreLabel;
    private GridPane gridPane;

    @Override
    public void start(Stage primaryStage) {
        board = new PuzzleBoard(3, "204153876"); // Configuration par défaut

        BorderPane root = new BorderPane();

        // Création de l'interface
        createUI(root);

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Sliding Puzzle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createUI(BorderPane root) {
        // Affichage des informations
        HBox infoBox = new HBox(10);
        moveLabel = new Label("Moves: " + board.getMoveCount());
        scoreLabel = new Label("Score: " + board.getScore());
        infoBox.getChildren().addAll(moveLabel, scoreLabel);
        infoBox.setAlignment(Pos.CENTER);
        root.setTop(infoBox);

        // Grille de jeu
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        updateGrid();
        root.setCenter(gridPane);

        // Contrôles
        HBox controlBox = new HBox(10);
        Button rearrangeBtn = new Button("Rearrange");
        rearrangeBtn.setOnAction(_ -> {
            board = new PuzzleBoard(3, "204153876");
            updateGrid();
            updateInfo();
        });

        Button shuffleBtn = new Button("Shuffle");
        shuffleBtn.setOnAction(_ -> {
            board.shuffle();
            updateGrid();
            updateInfo();
        });

        controlBox.getChildren().addAll(rearrangeBtn, shuffleBtn);
        controlBox.setAlignment(Pos.CENTER);
        root.setBottom(controlBox);
    }

    private void updateGrid() {
        gridPane.getChildren().clear();

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                final int finalRow = row; // Create final copies
                final int finalCol = col; // Create final copies

                Tile tile = board.getTile(row, col);
                Button btn = new Button(tile.isEmpty() ? "" : String.valueOf(tile.getValue()));
                btn.setPrefSize(80, 80);

                if (!tile.isEmpty()) {
                    btn.setOnAction(_ -> {
                        if (board.moveTile(finalRow, finalCol)) { // Use the final copies
                            updateGrid();
                            updateInfo();

                            if (board.isSolved()) {
                                showWinMessage();
                            }
                        }
                    });
                } else {
                    btn.setDisable(true);
                }

                gridPane.add(btn, col, row);
            }
        }
    }

    private void updateInfo() {
        moveLabel.setText("Moves: " + board.getMoveCount());
        scoreLabel.setText("Score: " + board.getScore());
    }

    private void showWinMessage() {
        // Afficher un message de victoire
        System.out.println("Félicitations! Vous avez résolu le puzzle!");
    }

    public static void main(String[] args) {
        launch(args);
    }
}