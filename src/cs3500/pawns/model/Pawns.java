package cs3500.pawns.model;

import java.util.Objects;

import static java.lang.String.valueOf;

/** Represents a number of pawns in a given cell. Has no value on its own. */
public class Pawns implements Cell {
  private int count;
  // INVARIANT: This is a natural
  private Player player;

  /**
   * a constructor to represent a pawn.
   *
   * @param count the count of pawns.
   * @param player the player impacted by these pawns.
   */
  public Pawns(int count, Player player) {
    if (count < 0 || count > 3) {
      throw new IllegalArgumentException("Cannot have less than 0 or greater than 3 pawns!");
    }
    if (player == null && count != 0) {
      throw new IllegalArgumentException("For player to be null, there must be 0 pawns!");
    }
    this.count = count;
    this.player = player;
  }

  /**
   * A simplified constructor to represent a pawn, the count is set to zero. This represents an
   * empty space.
   */
  public Pawns() {
    this.count = 0;
  }

  /**
   * Returns the affiliation of this Cell.
   *
   * @return the affiliation of this Cell.
   */
  @Override
  public Player getAffiliation() {
    return this.player;
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
   * Returns the amount of pawns in this position. Cards return -1, as they have no pawns.
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
    if (affiliation == null) {
      throw new IllegalArgumentException("Player cannot be null!");
    }
    if ((this.player == affiliation || this.player == null) && this.count < 3) {
      this.count++;
    }
    this.player =
        affiliation; // When the player is not the same, the pawns are 'taken over', but none are
    // added.
  }

  /**
   * Returns the value at this position. Pawns have no value and return 0.
   *
   * @return the value at this position.
   */
  @Override
  public int getValue() {
    return 0;
  }

  /**
   * Checks that two Pawns are equal. Two pawns are equal if their count and affiliation are the
   * same.
   *
   * @param o the object to compare.
   * @return if the Pawns are equal.
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Pawns)) {
      return false;
    }
    Pawns other = (Pawns) o;
    return this.count == other.getPawns() && this.player == other.getAffiliation();
  }

  /**
   * Return the hash of this Pawns object.
   *
   * @return the hash of this Pawns object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.count, this.player);
  }

  /**
   * Returns a string representation of this pawns object.
   * @return a string representation of this pawns object.
   */
  public String toString() {
    if (this.player == null) {
      return "Blank";
    }
    return "P: " + this.count + ", " + this.player.getColor();
  }
}
