import java.util.Objects;
/**
 * Represents a Pawn piece in the game.
 */
public class Pawn extends ConcretePiece{
    private int kills;// Counter for the number of kills made by this pawn


    /**
     * Constructor to initialize a Pawn object.
     *
     * @param player The player who owns the pawn.
     * @param id     The identifier for the pawn.
     * @param number The unique number assigned to the pawn.
     */
    public Pawn(Player player, String id, int number) {
        super(player,id,number);
        this.kills = 0;
    }
    /**
     * Gets the number of kills made by the pawn.
     *
     * @return The number of kills made by the pawn.
     */

    public int getKills() {
        return kills;
    }
    /**
     * Sets the number of kills made by the pawn.
     *
     * @param kills The number of kills made by the pawn.
     */
    public void setKills(int kills) {
        this.kills = kills;
    }


    @Override

    public String getType() {
        return (getOwner().isPlayerOne()) ? "♙" : "♟";
    }
}
