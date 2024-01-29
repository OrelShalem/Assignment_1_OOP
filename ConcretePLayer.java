
/**
 * Concrete implementation of the Player interface.
 */
public class ConcretePLayer implements Player{

    private boolean playerOne;
    protected boolean playerOneWon;
    private int wins;

    public ConcretePLayer() {
        this.playerOne = playerOne;
        this.wins = 0;
    }
    /**
     * Constructor for ConcretePlayer with specified playerOne status.
     *
     * @param playerOne True if this player is player one, false otherwise.
     */
    public ConcretePLayer(boolean playerOne) {
        this.playerOne = playerOne;
        this.wins = 0;
        this.playerOneWon = false;
    }
    /**
     * Checks if this player is player one.
     *
     * @return True if this player is player one, false otherwise.
     */

    @Override
    public boolean isPlayerOne() {
        return playerOne;
    }

    /**
     * Gets the number of wins by this player.
     *
     * @return The number of wins by this player.
     */
    @Override
    public int getWins() {
        return wins;
    }
    /**
     * Sets the number of wins for this player.
     *
     * @param win The number of wins to be added.
     */
    public void setWins(int win) {
        this.wins += win;
    }
    /**
     * Sets the playerOne status.
     *
     * @param playerOne True if this player is player one, false otherwise.
     */
    public void setPlayerOne(boolean playerOne) {
        this.playerOne = playerOne;
    }
    /**
     * Checks if player one has won.
     *
     * @return True if player one has won, false otherwise.
     */
    public boolean isPlayerOneWon() {
        return playerOneWon;
    }

    public void setPlayerOneWon(boolean playerOneWon) {
        this.playerOneWon = playerOneWon;
    }
}
