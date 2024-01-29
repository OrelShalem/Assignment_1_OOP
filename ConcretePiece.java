import java.awt.*;
import java.util.ArrayList;
/**
 * Abstract class providing a concrete implementation of the Piece interface.
 */
public abstract class ConcretePiece implements Piece{

    protected Player owner;  // The player who owns this piece
    protected String id;  // Unique identifier for the piece
    protected int number;  // Number associated with the piece
    private final ArrayList<Position> positionsHistory = new ArrayList<>();  // History of positions visited by the piece
    protected int distance;  // Cached value for the distance traveled by the piece
    protected String type;

    /**
     * Constructor for ConcretePiece.
     *
     * @param owner  The player who owns this piece.
     * @param id     Unique identifier for the piece.
     * @param number Number associated with the piece.
     */
    public ConcretePiece (Player owner, String id, int number){
        this.owner = owner;
        this.id = id + number;
        this.number = number;
    }

    /**
     * Gets the unique identifier of the piece.
     *
     * @return The unique identifier of the piece.
     */
    public String getId(){
        return id;
    }

    /**
     * Gets the number associated with the piece.
     *
     * @return The number associated with the piece.
     */
    public int getNumber() {
        return number;
    }
    /**
     * Gets the player who owns this piece.
     *
     * @return The player who owns this piece.
     */
    @Override
    public Player getOwner() {
        return owner;
    }
    /**
     * Gets the history of positions visited by the piece.
     *
     * @return The history of positions visited by the piece.
     */
    public ArrayList<Position> getPositionsHistory() {
        return positionsHistory;
    }
    /**
     * Sets a new position for the piece and updates the position history.
     *
     * @param position The new position of the piece.
     */
    public void setNewPosition(Position position) {
        positionsHistory.add(position);
    }

    /**
     * Gets the cached distance traveled by the piece.
     *
     * @return The cached distance traveled by the piece.
     */
    public int getDistance() {
        if(distance == 0){
            calcDistance();
        }
        return distance;
    }
    /**
     * Calculates and updates the distance traveled by the piece based on its position history.
     *
     * @return The calculated distance traveled by the piece.
     */
    public int calcDistance() {
        for (int i = 0; i < positionsHistory.size() - 1; i++) {
            int x = positionsHistory.get(i).getCol(), y = positionsHistory.get(i).getRow();
            int x1 = positionsHistory.get(i+1).getCol(), y1 = positionsHistory.get(i+1).getRow();

            this.distance += Math.abs(x - x1) + Math.abs(y - y1);
        }
        return distance;
    }
}
