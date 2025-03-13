package cs3500.pawns.model;

/**
 * a unit represented in a game of Queen's Blood.
 */
public interface Cell {

  /**
   * converts to a String format.
   *
   * @return the object converted to a string.
   */
  public String toString();

  /**
   * converts to a String format for visualization.
   *
   * @return the object converted to a string.
   */
  public String vString();

  /**
   * Returns the amount of pawns in this position.
   * Cards return -1, as they have no pawns.
   *
   * @return the amt of pawns in this position.
   */
  public int getPawns();

  /**
   * Adds a pawn into this position.
   *
   * @throws IllegalArgumentException if this is a card.
   */
  public void addPawn(Player affiliation);

  /**
   * Returns the value at this position.
   * Pawns have no value and return 0.
   *
   * @return the value at this position.
   */
  public int getValue();

  Player getAffiliation();
}