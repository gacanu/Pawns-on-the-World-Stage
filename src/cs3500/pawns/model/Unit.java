package cs3500.pawns.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * A card representing a unit in a game of Queen's Blood. Cards can be represented both on the deck
 * and on the board, so keep that in mind.
 */
public class Unit implements Card {
  private final String name;
  private final int cost;
  private final int value;
  private final Boolean[][] influence;
  private final Turn player;

  /**
   * to represent the Unit constructor.
   *
   * @param name the name of this card.
   * @param cost the cost of this card.
   * @param value the value at this position.
   * @param influence the influence of this card.
   * @param player the player who owns this card.
   */
  public Unit(String name, int cost, int value, Boolean[][] influence, Turn player) {
    if (name.isEmpty() || name == null) {
      throw new IllegalArgumentException("Name cannot be empty or null!");
    }

    if (cost <= 0 || value <= 0 || cost > 3 || value > 3) {
      throw new IllegalArgumentException(
          "Cost and value must be at least 1, and no greater than 3!");
    }

    if (influence == null) {
      throw new IllegalArgumentException("Influence cannot be null!");
    }

    if (influence.length != 5) {
      throw new IllegalArgumentException("Influence must be a 5x5 boolean array!");
    }

    for (Boolean[] a : influence) {
      if (a.length != 5) {
        throw new IllegalArgumentException("Influence must be a 5x5 boolean array!");
      }
    }

    for (Boolean[] b : influence) {
      for (Boolean c : b) {
        if (c == null) {
          throw new IllegalArgumentException("No member of the influence grid can be null!");
        }
      }
    }

    this.name = name;
    this.cost = cost;
    this.value = value;
    this.influence = influence;
    this.player = player;
  }

  /**
   * converts to a String format. Goes into GUI Menus.
   *
   * @return the players color as a string.
   */
  @Override
  public String toString() {
    return this.name + ", V: " + this.value; // TODO: Show influence grid
  }

  /**
   * converts to a String format for visualization.
   *
   * @return the players color as a string.
   */
  @Override
  public String vString() {
    return player.getColor().substring(0, 1);
  }

  /**
   * Returns the amount of pawns in this position. Cards return -1, as they have no pawns.
   *
   * @return the amt of pawns in this position.
   */
  @Override
  public int getPawns() {
    return -1;
  }

  /**
   * Adds a pawn into this position.
   *
   * @throws IllegalArgumentException if this is a card.
   */
  @Override
  public void addPawn(Turn player) {
    // throw new IllegalArgumentException("This is a card, there are no pawns here!");
    // Change this later, maybe.
  }

  /**
   * Returns the value at this position. Pawns have no value and return 0.
   *
   * @return the value at this position.
   */
  @Override
  public int getValue() {
    return this.value;
  }

  /**
   * Returns the name of this card.
   *
   * @return the name of this card.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the cost of this card.
   *
   * @return the cost of this card.
   */
  public int getCost() {
    return this.cost;
  }

  /**
   * Returns the influence of this card.
   *
   * @return the influence of this card.
   */
  public Boolean[][] getInfluence() {
    return this.influence.clone();
  }

  /**
   * Return the affiliation of this card.
   *
   * @return the affiliation of this card.
   */
  public Turn getAffiliation() {
    return this.player;
  }

  /**
   * Return a duplicate of this card, without aliasing it.
   *
   * @return a duplicate of this card.
   */
  @Override
  public Cell dupe() {
    return new Unit(this.name, this.cost, this.value, this.influence.clone(), this.player);
  }

  /**
   * Checks if the given object is equal to this card.
   *
   * @param o to represent an object
   * @return if this given object is equal to this card
   */
  public boolean equals(Object o) {
    if (!(o instanceof Card)) {
      return false;
    }
    Card other = (Card) o;
    return Arrays.deepEquals(this.influence, other.getInfluence())
        && Objects.equals(this.name, other.getName())
        && this.value == other.getValue()
        && this.cost == other.getCost()
        && this.player == other.getAffiliation();
  }

  /**
   * the hash code of the given card.
   *
   * @return the hash code of the given card
   */
  public int hashCode() {
    return Objects.hash(
        this.name, this.cost, this.value, Arrays.deepHashCode(this.influence), this.player);
  }
}
