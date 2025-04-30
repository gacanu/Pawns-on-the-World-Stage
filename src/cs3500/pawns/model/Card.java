package cs3500.pawns.model;

/**
 * An interface that represents a playable card with a name, cost, value, and influence grid. Can
 * work as an element of the deck or the board.
 */
public interface Card extends Cell {
  /**
   * Returns the influence of this card.
   *
   * @return the influence of this card.
   */
  Boolean[][] getInfluence();

  /**
   * Returns the name of this card.
   *
   * @return the name of this card.
   */
  String getName();

  /**
   * Returns the cost of this card.
   *
   * @return the cost of this card.
   */
  int getCost();

  /**
   * Checks if the given object is equal to this card.
   *
   * @param o to represent an object
   * @return if this given object is equal to this card
   */
  boolean equals(Object o);

  /**
   * the hash code of the given card.
   *
   * @return the hash code of the given card
   */
  int hashCode();
}
