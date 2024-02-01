/**
 * Class representing a King piece on a game board.
 */
public class King extends ConcretePiece{
    /**
     * Constructor for creating a King piece associated with a player.
     *
     * @param player The player that owns the King piece.
     */
    public King(Player player) {
        super(player, "K", 7);
    }


    /**
     * Gets the type of the piece, represented by a Unicode character for the King.
     *
     * @return The Unicode character representing the King piece.
     */
    @Override
    public String getType() {
        return "\u2654";  // Unicode for king
    }
}
