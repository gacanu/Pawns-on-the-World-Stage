package cs3500.pawns;

public interface Cell {

    /**
     * Returns the amount of pawns in this position.
     * Cards return -1, as they have no pawns.
     * @return the amt of pawns in this position.
     */
    public int getPawns();

    /**
     * Adds a pawn into this position.
     * @throws IllegalArgumentException if this is a card.
     */
    public void addPawn(Player affiliation);

    /**
     * Returns the value at this position.
     * Pawns have no value and return 0.
     * @return the value at this position.
     */
    public int getValue();

    public Player getAffiliation();

    public String toString();

}
