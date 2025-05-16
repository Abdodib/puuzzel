import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzleBoard {
    private Tile[][] board;
    private int size;
    private int emptyRow;
    private int emptyCol;
    private int moveCount;
    private int score;

    public PuzzleBoard(int size, String configuration) {
        this.size = size;
        initializeBoard(configuration);
    }

    private void initializeBoard(String config) {
        board = new Tile[size][size];
        int index = 0;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int value = Character.getNumericValue(config.charAt(index));
                board[row][col] = new Tile(value, index + 1);

                if (value == 0) {
                    emptyRow = row;
                    emptyCol = col;
                }
                index++;
            }
        }

        moveCount = 0;
        score = 0;
    }

    public boolean moveTile(int row, int col) {
        if (isValidMove(row, col)) {
            
            board[emptyRow][emptyCol] = board[row][col];
            board[row][col] = new Tile(0, 0);
            emptyRow = row;
            emptyCol = col;

            moveCount++;
            score += 5; 

            return true;
        }
        return false;
    }

    private boolean isValidMove(int row, int col) {
        return (Math.abs(row - emptyRow) == 1 && col == emptyCol) ||
                (Math.abs(col - emptyCol) == 1 && row == emptyRow);
    }

    public boolean isSolved() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (!board[row][col].isEmpty() &&
                        board[row][col].getValue() != row * size + col + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    
    public int getMoveCount() {
        return moveCount;
    }

    public int getScore() {
        return score;
    }

    public Tile getTile(int row, int col) {
        return board[row][col];
    }

    public int getSize() {
        return size;
    }

    public void shuffle() {
    
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            values.add(i);
        }
        Collections.shuffle(values);

        initializeBoard(values.stream().map(String::valueOf).reduce("", String::concat));
    }
}