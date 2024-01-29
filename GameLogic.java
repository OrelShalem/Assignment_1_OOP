import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The GameLogic class represents the game logic for a specific game.
 * It implements the PlayableLogic interface.
 */
public class GameLogic implements PlayableLogic {
    private final ConcretePLayer p1 = new ConcretePLayer(false);
    private final ConcretePLayer p2 = new ConcretePLayer(true);
    private final ConcretePiece[][] board = new ConcretePiece[this.getBoardSize()][this.getBoardSize()];
    boolean secondPlayerTurn = true;
    private ArrayList<ArrayList<Position>> moveHistoryDeff = new ArrayList<>();
    private ArrayList<ArrayList<Position>> moveHistoryAttackers = new ArrayList<>();

    private final List<ConcretePiece> pieces = new ArrayList<>();

    private ArrayList<ConcretePiece>[][] counterOfPieces;

    private boolean attackerWin = false;
    private boolean defenderWin = false;

    private int counterAttackers;

    private final int BOARD_SIZE = 11;


    /**
     * Constructs a new GameLogic instance and initializes the game.
     */

    public GameLogic() {
        this.counterOfPieces = new ArrayList[getBoardSize()][getBoardSize()];
        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                counterOfPieces[i][j] = new ArrayList<>();
            }
        }
        this.counterAttackers = 24;
        startNewGame();

    }

    // Method to start a new game and set up the initial board state
    private void startNewGame() {
        int idP1 = 1;
        int idP2 = 1;
        // Set up pawns for player 1
        for (int i = 3; i < 8; i++) {
            this.board[0][i] = new Pawn(this.p1, "A", idP1++);
        }
        this.board[1][5] = new Pawn(this.p1, "A", idP1++);

        for (int i = 3; i < 8; i++) {
            this.board[i][0] = new Pawn(this.p1, "A", idP1++);
            if (i == 5) {
                this.board[i][1] = new Pawn(this.p1, "A", idP1++);
                this.board[i][9] = new Pawn(this.p1, "A", idP1++);
            }
            this.board[i][10] = new Pawn(this.p1, "A", idP1++);
        }
        this.board[9][5] = new Pawn(this.p1, "A", idP1++);
        for (int i = 3; i < 8; i++) {
            this.board[10][i] = new Pawn(this.p1, "A", idP1++);
        }
        // Set up pawns and king for player 2
        int[] rows = {3, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 7};
        int[] columns = {5, 4, 5, 6, 3, 4, 5, 6, 7, 4, 5, 6, 5};
        int kingRow = 5;
        int kingColumn = 5;

        for (int i = 0; i < rows.length; i++) {
            int currentRow = rows[i];
            int currentColumn = columns[i];

            if (currentRow == kingRow && currentColumn == kingColumn) {
                this.board[currentRow][currentColumn] = new King(this.p2);
                idP2++;
            } else {
                this.board[currentRow][currentColumn] = new Pawn(this.p2, "D", idP2++);
            }
        }
        // Initialize move history lists
        for (int i = 0; i <= 13; i++) {
            this.moveHistoryDeff.add(new ArrayList<Position>());
        }
        // Initialize move history lists
        for (int i = 0; i <= 24; i++) {
            this.moveHistoryAttackers.add(new ArrayList<Position>());
        }

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                ConcretePiece piece = board[row][col];
                if (piece != null) {
                    this.pieces.add(piece);
                    this.counterOfPieces[row][col].add(piece);
                    piece.setNewPosition(new Position(col, row));
                }
            }
        }
    }

    /**
     * Moves a piece from position a to position b on the game board.
     *
     * @param a The starting position of the piece.
     * @param b The destination position for the piece.
     * @return true if the move is valid and successful, false otherwise.
     */
    @Override
    public boolean move(Position a, Position b) {
        // Check if positions are valid and move is allowed
        if (!a.isValid() || !b.isValid() || isDiagonal(a, b) ||
                getPieceAtPosition(b) != null || isBlocked(a, b)) {

            // Move logic for player 2 (attackers)
//                ConcretePiece piece = board[a.getRow()][a.getCol()];
//                this.board[a.getRow()][a.getCol()] = null;
//                this.board[b.getRow()][b.getCol()] = piece;
//                eat(b, (ConcretePiece) getPieceAtPosition(b));
//                this.moveHistoryAttackers.get(piece.getNumber()).add(b);
//                piece.setNewPosition(b);
//                if (!counterOfPieces[b.getRow()][b.getCol()].contains(piece)) {
//                    counterOfPieces[b.getRow()][b.getCol()].add(piece);
//                }
//                this.secondPlayerTurn = false;
//                if(isGameFinished()){
//                    printGameSummary();
//                }

                return false;
            }
            ConcretePiece piece = this.board[a.getRow()][a.getCol()];
             if(piece == null){
                return true;
             }
            Player currentP = piece.getOwner();

            if( !isCorner(b) && isSecondPlayerTurn() && !currentP.isPlayerOne()) {
                movePiece(piece,a,b);
            }
            if(!isSecondPlayerTurn() && currentP.isPlayerOne() && piece instanceof Pawn && !isCorner(b)) {
                movePiece(piece, a, b);
            }
            if(!isSecondPlayerTurn() && currentP.isPlayerOne()){
                movePiece(piece, a, b);
            }

            // Move logic for player 1 (defenders)
//            else if (piece != null && piece.getOwner().isPlayerOne() && !this.isSecondPlayerTurn()) {
//                // Non-throne and non-corner move
//                if (!isThrone(a) && !isCorner(b)) {
//                    this.board[a.getRow()][a.getCol()] = null;
//                    this.board[b.getRow()][b.getCol()] = piece;
//                    eat(b, (ConcretePiece) getPieceAtPosition(b));
//                    this.moveHistoryDeff.get(piece.getNumber()).add(b);
//                    keepPieceMoved(new Position(b.getCol(), b.getRow()));
//                    piece.setNewPosition(b);
//                    if (!counterOfPieces[b.getRow()][b.getCol()].contains(piece)) {
//                        counterOfPieces[b.getRow()][b.getCol()].add(piece);
//                    }
//                    this.secondPlayerTurn = true;
//                    if(isGameFinished()){
//                        printGameSummary();
//                    }
//                    return true;
//                }
//                // Throne move
//                else if (isThrone(a)) {
//                    this.board[a.getRow()][a.getCol()] = null;
//                    this.board[b.getRow()][b.getCol()] = piece;
//                    this.moveHistoryDeff.get(piece.getNumber()).add(b);
//
//                    if (!counterOfPieces[b.getRow()][b.getCol()].contains(piece)) {
//                        counterOfPieces[b.getRow()][b.getCol()].add(piece);
//                    }
//                    piece.setNewPosition(b);
//                    this.secondPlayerTurn = true;
//                    if(isGameFinished()){
//                        printGameSummary();
//                    }
//                    return true;
//                }
//
//            }
//
//            return true;
//        }
        return true;
    }
    public void movePiece(ConcretePiece piece, Position a, Position b){
        this.board[a.getRow()][a.getCol()] = null;
        this.board[b.getRow()][b.getCol()] = piece;
        eat(b, (ConcretePiece) getPieceAtPosition(b));
        piece.setNewPosition(b);
        if (!counterOfPieces[b.getRow()][b.getCol()].contains(piece)) {
            counterOfPieces[b.getRow()][b.getCol()].add(piece);
        }
        this.secondPlayerTurn  = !isSecondPlayerTurn();
        if(isGameFinished()){
            printGameSummary();
        }
    }

    private ConcretePiece keepPieceMoved(Position p) {
        ConcretePiece piece = (ConcretePiece) getPieceAtPosition(p);
        return piece;
    }

    /**
     * Checks if the given position is a corner on the game board.
     *
     * @param position The position to check.
     * @return true if the position is a corner, false otherwise.
     */
    private boolean isCorner(Position position) {
        if (position.getCol() == 0 && position.getRow() == 0) {
            return true;
        }
        if (position.getCol() == 0 && position.getRow() == 10) {
            return true;
        }
        if (position.getCol() == 10 && position.getRow() == 0) {
            return true;
        }
        if (position.getCol() == 10 && position.getRow() == 10) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the move between two positions is diagonal.
     *
     * @param a The starting position.
     * @param b The destination position.
     * @return true if the move is diagonal, false otherwise.
     */
    private boolean isDiagonal(Position a, Position b) {
        if (a.getRow() == b.getRow() || a.getCol() == b.getCol()) {
            return false;
        }
        return true;
    }

    /**
     * Checks if there are any pieces blocking the path between two positions.
     *
     * @param a The starting position.
     * @param b The destination position.
     * @return true if the path is blocked, false otherwise.
     */
    private boolean isBlocked(Position a, Position b) {
        int diffRow = 0, diffCol = 0;
        if (b.getRow() > a.getRow()) {
            diffRow = 1;
        }
        if (b.getCol() > a.getCol()) {
            diffCol = 1;
        }
        if (b.getRow() < a.getRow()) {
            diffRow = -1;
        }
        if (b.getCol() < a.getCol()) {
            diffCol = -1;
        }

        Position checkPosition = new Position(a.getCol() + diffCol, a.getRow() + diffRow);

        while (checkPosition.getRow() != b.getRow() || checkPosition.getCol() != b.getCol()) {
            if (getPieceAtPosition(checkPosition) != null) {
                return true;
            }
            checkPosition.setRow(checkPosition.getRow() + diffRow);
            checkPosition.setCol(checkPosition.getCol() + diffCol);
        }

        return false;
    }

    /**
     * Moves a piece to a new position and updates game state after an attack.
     *
     * @param position The position where the attack occurred.
     * @param eater    The piece that performed the attack.
     */
    public void eat(Position position, ConcretePiece eater) {
        if (!isThrone(position)) {
            //up
            isUpEatAble(position, eater);
            //down
            isDownEatAble(position, eater);
            //left
            isLeftEatAble(position, eater);
            // right
            isRightEatAble(position, eater);
        }
    }

    private void isRightEatAble(Position position, ConcretePiece eater) {
        Position right1 = new Position(position.getCol() + 1, position.getRow());
        Position right2 = new Position(position.getCol() + 2, position.getRow());
        if (isThrone(right1)){
            return;
        }
        if (right1.isValid()) {
            if ( getPieceAtPosition(right1) != null && !right2.isValid() &&
                    getPieceAtPosition(right1).getOwner() != eater.getOwner()) {
                Pawn pawn = (Pawn) getPieceAtPosition(position);
                if ( !isKingSurrounded()&& !getPieceAtPosition(right1).getType().equals("♔")) {
                    pawn.setKills(pawn.getKills() + 1);
                }
                kill(right1);
            }
            if (right2.isValid() && getPieceAtPosition(right1) != null && getPieceAtPosition(right1).getOwner() != eater.getOwner()) {
                if ((getPieceAtPosition(right2) != null && !getPieceAtPosition(right2).getClass().equals(King.class) &&
                        getPieceAtPosition(right2).getOwner() == eater.getOwner())||(isCorner(right2) && !isThrone(right1))) {
                    Pawn pawn = (Pawn) getPieceAtPosition(position);
                    if (!isKingSurrounded()) {
                        pawn.setKills(pawn.getKills() + 1);
                    }
                    kill(right1);

                }
            }
        }
    }

    private void isLeftEatAble(Position position, ConcretePiece eater) {
        Position left1 = new Position(position.getCol() - 1, position.getRow());
        Position left2 = new Position(position.getCol() - 2, position.getRow());
        if (isThrone(left1)){
            return;
        }
        if (left1.isValid()) {
            if (getPieceAtPosition(left1) != null && !left2.isValid()  && getPieceAtPosition(left1).getOwner() != eater.getOwner()) {
                Pawn pawn = (Pawn) getPieceAtPosition(position);
                if (!isKingSurrounded() && !getPieceAtPosition(left1).getType().equals("♔")) {
                    pawn.setKills(pawn.getKills() + 1);
                }
                kill(left1);

            }
            if (getPieceAtPosition(left1) != null && left2.isValid()  && getPieceAtPosition(left1).getOwner() != eater.getOwner()
            && !Objects.equals(eater.getType(), "♔")) {
                if ((getPieceAtPosition(left2) != null && !getPieceAtPosition(left2).getClass().equals(King.class) &&
                        getPieceAtPosition(left2).getOwner() == eater.getOwner()) || (isCorner(left2) && !isThrone(left1))) {
                    Pawn pawn = (Pawn) getPieceAtPosition(position);
                    if (!isKingSurrounded()) {
                        pawn.setKills(pawn.getKills() + 1);
                    }

                    kill(left1);

                }
            }
        }
    }

    private void isDownEatAble(Position position, ConcretePiece eater) {
        Position down1 = new Position(position.getCol(), position.getRow() + 1);
        Position down2 = new Position(position.getCol(), position.getRow() + 2);
        if (isThrone(down1)){
            return;
        }
        if (down1.isValid()) {
            if (getPieceAtPosition(down1) != null && !down2.isValid() && getPieceAtPosition(down1).getOwner() != eater.getOwner()) {
                Pawn pawn = (Pawn) getPieceAtPosition(position);
                if (!isKingSurrounded() && !getPieceAtPosition(down1).getType().equals("♔")) {
                    pawn.setKills(pawn.getKills() + 1);
                }
                kill(down1);


            }
            if (down2.isValid() && getPieceAtPosition(down1) != null && getPieceAtPosition(down1).getOwner() != eater.getOwner()) {
                if ((getPieceAtPosition(down2) != null && !getPieceAtPosition(down2).getClass().equals(King.class) &&
                        getPieceAtPosition(down2).getOwner() == eater.getOwner())|| (isCorner(down2) && !isThrone(down1))) {
                    Pawn pawn = (Pawn) getPieceAtPosition(position);
                    if (!isKingSurrounded()) {
                        pawn.setKills(pawn.getKills() + 1);
                    }
                    kill(down1);


                }
            }
        }

    }

    private void isUpEatAble(Position p, ConcretePiece eater) {
        Position up1 = new Position(p.getCol(), p.getRow() - 1);
        Position up2 = new Position(p.getCol(), p.getRow() - 2);
        if (isThrone(up1)){
            return;
        }
        if (up1.isValid()) {
            if (getPieceAtPosition(up1) != null && !up2.isValid() && getPieceAtPosition(up1).getOwner() != eater.getOwner()) {
                Pawn pawn = (Pawn) getPieceAtPosition(p);
                if (!isKingSurrounded()&& !getPieceAtPosition(up1).getType().equals("♔")) {
                    pawn.setKills(pawn.getKills() + 1);
                }
                kill(up1);


            }
            if (up2.isValid() && getPieceAtPosition(up1) != null && getPieceAtPosition(up1).getOwner() != eater.getOwner()) {
                if ((getPieceAtPosition(up2) != null && !getPieceAtPosition(up2).getClass().equals(King.class) &&
                        getPieceAtPosition(up2).getOwner() == eater.getOwner())|| (isCorner(up2) && !isThrone(up1))) {
                    Pawn pawn = (Pawn) getPieceAtPosition(p);
                    if (!isKingSurrounded()) {
                        pawn.setKills(pawn.getKills() + 1);
                    }
                    kill(up1);


                }
            }
        }

    }

    /**
     * Checks if the king is surrounded by opponents.
     *
     * @return true if the king is surrounded, false otherwise.
     */
    private boolean isKingSurrounded() {
        int counterOfEnemy = 0;

        Position kingPos = findKingPosition();
        if (kingPos.getRow() > 0 && board[kingPos.getRow() - 1][kingPos.getCol()] != null && !board[kingPos.getRow() - 1][kingPos.getCol()].getOwner().isPlayerOne()) {
            counterOfEnemy++;
        }
        if (kingPos.getRow() < board.length - 1 && board[kingPos.getRow() + 1][kingPos.getCol()] != null && !board[kingPos.getRow() + 1][kingPos.getCol()].getOwner().isPlayerOne()) {
            counterOfEnemy++;
        }
        if (kingPos.getCol() > 0 && board[kingPos.getRow()][kingPos.getCol() - 1] != null && !board[kingPos.getRow()][kingPos.getCol() - 1].getOwner().isPlayerOne()) {
            counterOfEnemy++;
        }
        if (kingPos.getCol() < board[0].length - 1 && board[kingPos.getRow()][kingPos.getCol() + 1] != null && !board[kingPos.getRow()][kingPos.getCol() + 1].getOwner().isPlayerOne()) {
            counterOfEnemy++;
        }
        if (kingPos.getCol() == 0 || kingPos.getCol() == board.length - 1 || kingPos.getRow() == 0 || kingPos.getRow() == board[0].length - 1) {
            return counterOfEnemy >= 3;
        } else {
            return counterOfEnemy == 4;
        }
    }

    /**
     * Kills a piece at the specified position.
     *
     * @param p The position of the piece to be killed.
     */
    private void kill(Position p) {
        if (!isThrone(p)) {
            if (!getPieceAtPosition(p).getOwner().isPlayerOne()) {
                setCounterAttackers(getCounterAttackers() - 1);
            }
            board[p.getRow()][p.getCol()] = null;
        }
    }

    /**
     * Checks if a piece is located on the throne.
     *
     * @param position The position to check.
     * @return true if a piece is on the throne, false otherwise.
     */
    public boolean isThrone(Position position) {
        if (getPieceAtPosition(position) != null && position != null ) {
            if (getPieceAtPosition(position).getType().equals("♔")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the piece at the specified position on the game board.
     *
     * @param position The position to check.
     * @return The piece at the specified position or null if no piece is found.
     */
    @Override
    public Piece getPieceAtPosition(Position position) {
        if (position.isValid()) {
            return board[position.getRow()][position.getCol()];
        } else {
            return null;
        }
    }

    /**
     * Retrieves the first player in the game.
     *
     * @return The first player.
     */
    @Override
    public Player getFirstPlayer() {
        return p1;
    }

    /**
     * Retrieves the second player in the game.
     *
     * @return The second player.
     */
    @Override
    public Player getSecondPlayer() {
        return p2;
    }

    /**
     * Checks if the game has finished.
     *
     * @return true if the game has finished, false otherwise.
     */
    @Override
    public boolean isGameFinished() {

        if (zeroOfAttackers()) {
            this.p1.setWins(p1.getWins() + 1);
            this.p1.setPlayerOne(false);
            defenderWin = true;
            return true;
        }

        Position kingPosition = findKingPosition();
        if (kingPosition != null && isCorner(kingPosition)) {
            this.p1.setWins(p1.getWins() + 1);
            this.p1.setPlayerOne(false);
            defenderWin = true;
            return true;
        }


        boolean win = true;
        if (kingPosition != null && kingPosition.getRow() == 0) {
            if (getPieceAtPosition(new Position(kingPosition.getCol() + 1, kingPosition.getRow())) != null && !getPieceAtPosition(new Position(kingPosition.getCol() + 1, kingPosition.getRow())).getOwner().isPlayerOne()) {
                if (getPieceAtPosition(new Position(kingPosition.getCol() - 1, kingPosition.getRow())) != null && !getPieceAtPosition(new Position(kingPosition.getCol() - 1, kingPosition.getRow())).getOwner().isPlayerOne()) {
                    if (getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() + 1)) != null && !getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() + 1)).getOwner().isPlayerOne()) {
                        this.p2.setWins(p2.getWins() + 1);
                        this.p1.setPlayerOne(false);
                        attackerWin = true;
                        return win;
                    }
                }
            }
        }
        if (kingPosition != null && kingPosition.getRow() == 10) {
            if (getPieceAtPosition(new Position(kingPosition.getCol() + 1, kingPosition.getRow())) != null && !getPieceAtPosition(new Position(kingPosition.getCol() + 1, kingPosition.getRow())).getOwner().isPlayerOne()) {
                if (getPieceAtPosition(new Position(kingPosition.getCol() - 1, kingPosition.getRow())) != null && !getPieceAtPosition(new Position(kingPosition.getCol() - 1, kingPosition.getRow())).getOwner().isPlayerOne()) {
                    if (getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() - 1)) != null && !getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() - 1)).getOwner().isPlayerOne()) {
                        this.p2.setWins(p2.getWins() + 1);
                        this.p1.setPlayerOne(false);
                        attackerWin = true;
                        return win;
                    }
                }
            }
        }
        if (kingPosition != null && kingPosition.getCol() == 0) {
            if (getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() - 1)) != null && !getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() - 1)).getOwner().isPlayerOne()) {
                if (getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() + 1)) != null && !getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() + 1)).getOwner().isPlayerOne()) {
                    if (getPieceAtPosition(new Position(kingPosition.getCol() + 1, kingPosition.getRow())) != null && !getPieceAtPosition(new Position(kingPosition.getCol() + 1, kingPosition.getRow())).getOwner().isPlayerOne()) {
                        this.p2.setWins(p2.getWins() + 1);
                        this.p1.setPlayerOne(false);
                        attackerWin = true;
                        return win;
                    }
                }
            }
        }
        if (kingPosition != null && kingPosition.getCol() == 10) {
            if (getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() - 1)) != null && !getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() - 1)).getOwner().isPlayerOne()) {
                if (getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() + 1)) != null && !getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() + 1)).getOwner().isPlayerOne()) {
                    if (getPieceAtPosition(new Position(kingPosition.getCol() - 1, kingPosition.getRow())) != null && !getPieceAtPosition(new Position(kingPosition.getCol() - 1, kingPosition.getRow())).getOwner().isPlayerOne()) {
                        this.p2.setWins(p2.getWins() + 1);
                        this.p1.setPlayerOne(false);
                        attackerWin = true;
                        return win;
                    }
                }
            }
        }
        if (kingPosition != null && kingPosition.getCol() < 10 && kingPosition.getCol() > 0 && kingPosition.getRow() < 10 && kingPosition.getRow() > 0) {
//            if (getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() - 1)) != null && !getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() - 1)).getOwner().isPlayerOne()) {
//                if (getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() + 1)) != null && !getPieceAtPosition(new Position(kingPosition.getCol(), kingPosition.getRow() + 1)).getOwner().isPlayerOne()) {
//                    if (getPieceAtPosition(new Position(kingPosition.getCol() - 1, kingPosition.getRow())) != null && !getPieceAtPosition(new Position(kingPosition.getCol() - 1, kingPosition.getRow())).getOwner().isPlayerOne()) {
                       if (isKingSurrounded()) {
                           this.p2.setWins(p2.getWins() + 1);
                           this.p1.setPlayerOne(false);
                           attackerWin = true;
                           return win;
                       }
//                    }
//                }
//            }
        }
        return false;
    }

    private boolean zeroOfAttackers() {
        if (getCounterAttackers() == 0) {
            return true;
        }
        return false;
    }

    public Position findKingPosition() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                ConcretePiece piece = board[row][col];
                if (piece instanceof King) {
                    return new Position(col, row);
                }
            }
        }
        return null;
    }

    /**
     * Checks if it is currently the second player's turn.
     *
     * @return true if it is the second player's turn, false otherwise.
     */
    @Override
    public boolean isSecondPlayerTurn() {
        return secondPlayerTurn;
    }


    /**
     * Resets the game to its initial state.
     */
    @Override
    public void reset() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                this.board[row][col] = null;
            }
        }
        this.secondPlayerTurn = true;
        attackerWin = false;
        defenderWin = false;
        startNewGame();
    }

    /**
     * Undoes the last move made in the game.
     */
    @Override
    public void undoLastMove() {

    }

    /**
     * Prints the moves of a specific piece, including its positions history.
     *
     * @param piece The piece to print moves for.
     */
    private void printMoves(ConcretePiece piece) {
        StringBuilder string1 = new StringBuilder();
        string1.append(piece.getId() + ": [");
        for (int i = 0; i < piece.getPositionsHistory().size(); i++) {
            Position position = piece.getPositionsHistory().get(i);
            string1.append("(" + position.getCol() + ", " + position.getRow() + ")");
            if (i < piece.getPositionsHistory().size() - 1) {
                string1.append(", ");
            }
        }
        string1.append("]");
        System.out.print(string1);
        System.out.println();

    }

    /**
     * Retrieves the counter of attackers in the game.
     *
     * @return The counter of attackers.
     */
    public int getCounterAttackers() {
        return counterAttackers;
    }

    /**
     * Sets the counter of attackers to the specified value.
     *
     * @param counterAttackers The new value for the counter of attackers.
     */
    public void setCounterAttackers(int counterAttackers) {
        this.counterAttackers = counterAttackers;
    }


    /**
     * Retrieves the size of the game board.
     *
     * @return The size of the game board.
     */
    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }




    /**
     * Prints a summary of the game, including move history, kills, distances, and pieces on squares.
     */
    public void printGameSummary() {
        pieces.sort(new MovesComparator());
        if (attackerWin){
            for (ConcretePiece piece : pieces) {
                if (piece.getOwner().isPlayerOne() == p1.isPlayerOneWon()) {
                    if (piece.getPositionsHistory().size() > 1) {
                        printMoves(piece);
                    }
                }
            }

            for (ConcretePiece piece : pieces) {
                if (piece.getOwner().isPlayerOne() == !p1.isPlayerOneWon()) {
                    if (piece.getPositionsHistory().size() > 1) {
                        printMoves(piece);
                    }
                }
            }
        }
        else {
            for (ConcretePiece piece : pieces) {
                if (piece.getOwner().isPlayerOne() == !p1.isPlayerOneWon()) {
                    if (piece.getPositionsHistory().size() > 1) {
                        printMoves(piece);
                    }
                }
            }

            for (ConcretePiece piece : pieces) {
                if (piece.getOwner().isPlayerOne() == p1.isPlayerOneWon()) {
                    if (piece.getPositionsHistory().size() > 1) {
                        printMoves(piece);
                    }
                }
            }
        }




        System.out.println("***************************************************************************");

        List<Pawn> pawns = pieces.stream()
                .filter(piece -> piece instanceof Pawn)
                .map(piece -> (Pawn) piece)
                .collect(Collectors.toList());

        pawns.sort(new killsComparator());

        for (ConcretePiece piece : pawns) {
            if (((Pawn) piece).getKills() != 0) {
                System.out.println(piece.getId() + ": " + ((Pawn) piece).getKills() + " kills");
            }


        }

        System.out.println("***************************************************************************");
        pieces.sort(new distanceComparator());
        for (ConcretePiece piece : pieces) {
            if (piece.getPositionsHistory().size() > 1) {
                System.out.println(piece.getId() + ": " + piece.getDistance() + " squares");
            }
        }

        System.out.println("***************************************************************************");

        ArrayList<Position> sumPiecesOnSquare = new ArrayList<>();
        for (int i = 0; i < this.counterOfPieces.length; i++) {
            for (int j = 0; j < this.counterOfPieces[i].length; j++) {
                if (counterOfPieces[i][j].size() > 1) {
                    sumPiecesOnSquare.add(new Position(j, i));
                }
            }
        }
        sumPiecesOnSquare.sort(new yPiecesComparator());
        sumPiecesOnSquare.sort(new xPiecesComparator());
        sumPiecesOnSquare.sort(new counterPiecesComparator());
        for (int i = 0; i < sumPiecesOnSquare.size(); i++) {
            System.out.println("(" + sumPiecesOnSquare.get(i).getCol() + ", " + sumPiecesOnSquare.get(i).getRow() + ")" + counterOfPieces[sumPiecesOnSquare.get(i).getRow()][sumPiecesOnSquare.get(i).getCol()].size() + " pieces");
        }
        System.out.println("***************************************************************************");
        sumPiecesOnSquare.clear();
        for (int i = 0; i < this.counterOfPieces.length; i++) {
            for (int j = 0; j < this.counterOfPieces[i].length; j++) {
                counterOfPieces[i][j].clear();
            }
        }
//        pieces.clear();

    }

    /**
     * Comparator for comparing ConcretePiece objects based on the number of moves in their history.
     */
    private class MovesComparator implements Comparator<ConcretePiece> {
        @Override
        public int compare(ConcretePiece piece1, ConcretePiece piece2) {
            int movesComparison = Integer.compare(piece1.getPositionsHistory().size(), piece2.getPositionsHistory().size());
            if (movesComparison != 0) {
                return movesComparison;
            } else {
                return Integer.compare(piece1.getNumber(), piece2.getNumber());
            }
        }
    }

    /**
     * Comparator for comparing Pawn objects based on the number of kills and other criteria.
     */

    private class killsComparator implements Comparator<Pawn> {
        @Override
        public int compare(Pawn piece1, Pawn piece2) {
//            if (piece1.getKills() == 0 || piece2.getKills() == 0) {
//                return 0;
//            }
            int killsC = Integer.compare(piece2.getKills(), piece1.getKills());
            if (killsC != 0) {
                return killsC;
            } else {
                int idComparator = Integer.compare(piece1.getNumber(), piece2.getNumber());
                if (idComparator != 0) {
                    return idComparator;
                } else {
                    if (!p1.isPlayerOneWon()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        }
    }

    /**
     * Comparator for comparing ConcretePiece objects based on the distance traveled in their history.
     */
    private class distanceComparator implements Comparator<ConcretePiece> {
        @Override
        public int compare(ConcretePiece piece1, ConcretePiece piece2) {
            int distanceComp = Integer.compare(piece2.getDistance(), piece1.getDistance());
            if (distanceComp != 0) {
                return distanceComp;
            } else {
                int idComparator = Integer.compare(piece1.getNumber(), piece2.getNumber());
                if (idComparator != 0) {
                    return idComparator;
                } else {
                    if ((attackerWin && piece1.getOwner().isPlayerOne() )|| (defenderWin && !piece1.getOwner().isPlayerOne())) {
                        return 1;
                    }else {
                        return -1;
                    }
                }
            }
        }
    }

    /**
     * Comparator for comparing Position objects based on the count of pieces at the position.
     */

    private class counterPiecesComparator implements Comparator<Position> {
        @Override
        public int compare(Position t1, Position t2) {
            if (counterOfPieces[t1.getRow()][t1.getCol()].size() > counterOfPieces[t2.getRow()][t2.getCol()].size()) {
                return -1;
            } else if (counterOfPieces[t1.getRow()][t1.getCol()].size() < counterOfPieces[t2.getRow()][t2.getCol()].size()) {
                return 1;
            }

            return 0;
        }
    }

    private class xPiecesComparator implements Comparator<Position> {
        @Override
        public int compare(Position t1, Position t2) {
            if (t1.getCol() > t2.getCol()) {
                return 1;
            }
            else if(t1.getCol() < t2.getCol()){
                return -1;
            }
            return 0;
        }
    }

    private class yPiecesComparator implements Comparator<Position> {
        @Override
        public int compare(Position t1, Position t2) {
            if (t1.getRow() > t2.getRow()) {
                return 1;
            }
            else if(t1.getRow() < t2.getRow()){
                return -1;
            }
            return 0;
        }
    }


}
