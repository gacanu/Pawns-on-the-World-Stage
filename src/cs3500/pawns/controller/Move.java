package cs3500.pawns.controller;

import cs3500.pawns.model.PawnsBoardModel;

/** the move interface represents the actions a player can take. */
public interface Move {
  /**
   * Produces a string representation of this move.
   *
   * @return a string representation of this move.
   */
  String toString();

  /**
   * Plays the current move on the given model.
   *
   * @param model the model to be played upon.
   */
  void execute(PawnsBoardModel model);
}
