package cs3500.pawns.model;

import static java.lang.String.valueOf;

/**
 * Represents a number of pawns in a given cell. Has no value on its own.
 */
public class Pawns implements Cell {
  private int count;
  //INVARIANT: This is a natural
  private Player player;

  /**
   * a constructor to represent a pawn.
   *
   * @param count  the count of pawns.
   * @param player the player impacted by these pawns.
   */
  public Pawns(int count, Player player) {
    if (count < 0) {
      throw new IllegalArgumentException("Cannot have less than 0 pawns!");
    }

    this.count = count;
    this.player = player;
  }

  @Override
  public Player getAffiliation() {
    return this.player;
  }


  /**
   * a simplified constructor to represent a pawn, the count is set to zero.
   *
   * @param player the player impacted by these pawns.
   */
  public Pawns(Player player) {
    this.count = 0;
    this.player = player;
  }

  /**
   * converts to a String format for visualization.
   *
   * @return the count in a string format.
   */
  @Override
  public String vString() {
    if (this.count == 0) {
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
    if ((this.player == affiliation || this.player == null) && this.count < 3) {
      this.player = affiliation; //Applies for spaces with no pawns, which have a null player
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
}