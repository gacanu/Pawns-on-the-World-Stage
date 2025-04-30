package cs3500.pawns.controller;

import java.util.Objects;

import cs3500.pawns.model.PawnsBoardModel;

/** class place card allows the action place card to occur. */
public class PlaceCard implements Move {
  private final int x;
  private final int y;
  private final int cardIndex;

  /**
   * constructor for place card.
   *
   * @param x the x represents the row
   * @param y the y represents the col
   * @param cardIndex the index of the card in the hand to place
   */
  public PlaceCard(int x, int y, int cardIndex) {
    this.x = x;
    this.y = y;
    this.cardIndex = cardIndex;
  }

  /**
   * gets the x value from the given place card.
   *
   * @return cards x value.
   */
  public int getX() {
    return x;
  }

  /**
   * gets the y value from the given place card.
   *
   * @return cards y value.
   */
  public int getY() {
    return y;
  }

  /**
   * gets the card index from the given place card.
   *
   * @return the card index.
   */
  public int getCardIndex() {
    return cardIndex;
  }

  /**
   * Produces a string representation of this move.
   *
   * @return a string representation of this move.
   */
  public String toString() {
    return "Placing card index " + cardIndex + " in position (" + x + "," + y + ")";
  }

  /**
   * Plays the current move on the given model.
   *
   * @param model the model to be played upon.
   */
  @Override
  public void execute(PawnsBoardModel model) {
    try {
      model.placeCardInPosition(cardIndex, x, y);
    } catch (Exception e) {
      throw new RuntimeException("PlaceCard move failed: " + e.getMessage());
    }
  }

  /**
   * are these two objects equal.
   *
   * @param o object to compare.
   * @return a boolean true if the objects are equal.
   */
  public boolean equals(Object o) {
    if (!(o instanceof PlaceCard)) {
      return false;
    }
    PlaceCard other = (PlaceCard) o;
    return other.getX() == this.x
        && other.getY() == this.y
        && other.getCardIndex() == this.cardIndex;
  }

  /**
   * Return the hash of this object.
   *
   * @return the hash of this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y, this.cardIndex);
  }
}
