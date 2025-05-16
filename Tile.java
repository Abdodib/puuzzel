public class Tile {
    private int value; 
    private int correctPosition;
    
    public Tile(int value, int correctPosition) {
        this.value = value;
        this.correctPosition = correctPosition;
    }
    
    public int getValue() { return value; }
    public int getCorrectPosition() { return correctPosition; }
    public boolean isEmpty() { return value == 0; }
}