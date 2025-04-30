package cs3500.pawns.controller;

import java.util.Objects;

import cs3500.pawns.model.PawnsBoardModel;

/** class pass allows the action pass to occur. */
public class Pass implements Move {
  /**
   * Produces a string representation of this move.
   *
   * @return a string representation of this move.
   */
  public String toString() {
    return "Passing.";
  }

  /**
   * Plays the current move on the given model.
   *
   * @param model the model to be played upon.
   */
  @Override
  public void execute(PawnsBoardModel model) {
    try {
      model.passTurn();
    } catch (Exception e) {
      throw new RuntimeException("PassTurn move failed: " + e.getMessage());
    }
  }

  /**
   * are these two objects equal.
   *
   * @param o object to compare.
   * @return a boolean true if the objects are equal.
   */
  public boolean equals(Object o) {
    return o instanceof Pass;
  }

  /**
   * Return the hash of this object.
   *
   * @return the hash of this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this);
  }
}
