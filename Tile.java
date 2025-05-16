public class Tile {
    private int value; // 0 représente la case vide
    private int correctPosition;
    
    public Tile(int value, int correctPosition) {
        this.value = value;
        this.correctPosition = correctPosition;
    }
    
    // Getters et setters
    public int getValue() { return value; }
    public int getCorrectPosition() { return correctPosition; }
    public boolean isEmpty() { return value == 0; }
}