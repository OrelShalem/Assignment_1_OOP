/**
 * Class representing a position on a game board with columns and rows.
 */
public class Position {
    private int col;
    private int row;
    /**
     * Constructor for creating a Position with specified column and row.
     *
     * @param col Column of the position.
     * @param row Row of the position.
     */
    Position (int col, int row){
        this.col = col;
        this.row = row;
    }
    /**
     * Gets the column of the position.
     *
     * @return The column of the position.
     */
    public int getCol() {
        return col;
    }
    /**
     * Sets the column of the position.
     *
     * @param col The new column of the position.
     */
    public void setCol(int col) {
        this.col = col;
    }
    /**
     * Gets the row of the position.
     *
     * @return The row of the position.
     */
    public int getRow() {
        return row;
    }
    /**
     * Sets the row of the position.
     *
     * @param row The new row of the position.
     */
    public void setRow(int row) {
        this.row = row;
    }
    /**
     * Checks if the position is valid on the game board.
     *
     * @return true if the position is valid, false otherwise.
     */
    public boolean isValid(){
        if(this.getCol() < 11 && this.getCol() >= 0 && this.getRow() < 11 && this.getRow() >= 0){
            return true;
        }
        return false;
    }








}
