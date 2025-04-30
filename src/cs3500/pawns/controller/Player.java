package cs3500.pawns.controller;

import cs3500.pawns.model.Turn;

/**
 * Represents a player in the game where the methods defined. let the player subscribe to a
 * controller or handles some turn functions.
 */
public interface Player {
  /**
   * Allows this Player to notify the given controller object when it decides on a move to use.
   *
   * @param c the controller to subscribe to.
   */
  void subscribe(QueensBloodController c);

  /** Pushes a move to the subscribed-to controller. */
  void publish();

  /**
   * Notifies this player when the active turn has changed.
   *
   * @param t the turn it has changed to.
   */
  void nudge(Turn t);

  /**
   * Finds who's turn it is.
   *
   * @return Turn the turn
   */
  Turn getTurn();
}
