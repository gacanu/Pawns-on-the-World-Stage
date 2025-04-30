package cs3500.pawns.controller;

import cs3500.pawns.model.Turn;

/** A representation of a human player in the game. */
public class HumanPlayer implements Player {
  private final Turn turn;

  /**
   * human player constructor.
   *
   * @param t whos turn it is
   */
  public HumanPlayer(Turn t) {
    this.turn = t;
  }

  /**
   * Allows this Player to notify the given controller object when it decides on a move to use.
   *
   * @param c the controller to subscribe to.
   */
  @Override
  public void subscribe(QueensBloodController c) {
    // This method isnt needed for a player character
  }

  /** Pushes a move to the subscribed-to controller. */
  @Override
  public void publish() {
    // This method isnt needed for a player character
  }

  /**
   * Notifies this player when the active turn has changed.
   *
   * @param t the turn it has changed to.
   */
  @Override
  public void nudge(Turn t) {
    // This method isnt needed for a player character
  }

  /**
   * who turn is it.
   *
   * @return the turn it is
   */
  @Override
  public Turn getTurn() {
    return this.turn;
  }
  // I don't really know if the player actually DOES anything.
}
