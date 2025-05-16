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
        board = new PuzzleBoard(3, "204153876"); 

        BorderPane root = new BorderPane();

        
        createUI(root);

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Sliding Puzzle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createUI(BorderPane root) {
        
        HBox infoBox = new HBox(10);
        moveLabel = new Label("Moves: " + board.getMoveCount());
        scoreLabel = new Label("Score: " + board.getScore());
        infoBox.getChildren().addAll(moveLabel, scoreLabel);
        infoBox.setAlignment(Pos.CENTER);
        root.setTop(infoBox);

        
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        updateGrid();
        root.setCenter(gridPane);

        
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
                final int finalRow = row; 
                final int finalCol = col; 

                Tile tile = board.getTile(row, col);
                Button btn = new Button(tile.isEmpty() ? "" : String.valueOf(tile.getValue()));
                btn.setPrefSize(80, 80);

                if (!tile.isEmpty()) {
                    btn.setOnAction(_ -> {
                        if (board.moveTile(finalRow, finalCol)) { 
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
        
        System.out.println("Félicitations! Vous avez résolu le puzzle!");
    }

    public static void main(String[] args) {
        launch(args);
    }
}