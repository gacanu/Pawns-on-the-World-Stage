package cs3500.pawns.provider.controller;

/**
 * An object that handles actions performed within models. This handles turn changes, and game over
 * events.
 */
public interface ModelActions {

  /** Handles changing the players turn. */
  void handleNextTurn();

  /** Handles game over notifications. */
  void handleGameOver();
}
