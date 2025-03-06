package cs3500.pawns;

import static java.lang.String.valueOf;

/**
 * Represents a number of pawns in a given cell. Has no value on its own.
 */
public class Pawns implements Cell{
    private int count;
    //INVARIANT: This is a natural
    private Player affiliation;

    public Pawns(int count, Player affiliation) {
        if(count < 0) {
            throw new IllegalArgumentException("Must have at least 0 pawns!");
        }
        this.count = count;
        this.affiliation = affiliation;
    }

    public Pawns(Player affiliation) {
        this.count = 0;
        this.affiliation = affiliation;
    }

    public String toString() {
        if(this.count == 0) {
            return "_";
        }
        return valueOf(count);
    }

    /**
     * Returns the amount of pawns in this position.
     * Cards return -1, as they have no pawns.
     *
     * @return the amt of pawns in this position.
     */
    @Override
    public int getPawns() {
        return this.count;
    }

    /**
     * Adds a pawn into this position.
     *
     * @throws IllegalArgumentException if this is a card.
     */
    @Override
    public void addPawn(Player affiliation) {
        if(this.affiliation == affiliation || this.affiliation == null || this.count != 3) {
            this.affiliation = affiliation; //Applies for neutral
            this.count++;
        }
    }

    /**
     * Returns the value at this position.
     * Pawns have no value and return 0.
     *
     * @return the value at this position.
     */
    @Override
    public int getValue() {
        return this.count;
    }

    /**
     * Returns the affiliation of this pawn.
     * @return
     */
    @Override
    public Player getAffiliation() {
        return this.affiliation;
    }
}
