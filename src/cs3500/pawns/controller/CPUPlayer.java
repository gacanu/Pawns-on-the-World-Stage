package cs3500.pawns.controller;

import cs3500.pawns.model.ReadonlyPawnsBoardModel;
import cs3500.pawns.model.Turn;

/** A CPU controlled player that uses a given strategy to determine its move. */
public class CPUPlayer implements Player {
  private final ReadonlyPawnsBoardModel model;
  private final Strategy strategy;
  private final Turn turn;
  // model needs to come in via a constructor
  // the controller will grab the move decided upon
  // Take in a model and decide what to do with the Move interface
  private QueensBloodController controller;
  private boolean hasController;

  /**
   * Constructor for the CPUPlayer.
   *
   * @param model model read only version
   * @param strategy that is used for the moves
   * @param turn the turn of this player
   */
  public CPUPlayer(ReadonlyPawnsBoardModel model, Strategy strategy, Turn turn) {
    if (model == null || strategy == null) {
      throw new IllegalArgumentException("Model, strategy and turn cannot be null!");
    }

    this.model = model;
    this.controller = null;
    this.strategy = strategy;
    this.hasController = false;
    this.turn = turn;
  }

  /**
   * Allows this Player to notify the given controller object when it decides on a move to use.
   *
   * @param c the controller to subscribe to.
   */
  @Override
  public void subscribe(QueensBloodController c) {
    if (c == null) {
      throw new IllegalArgumentException("Given controller cannot be null!");
    }
    this.controller = c;
    this.hasController = true;
  }

  /** Pushes a move to the subscribed-to controller. */
  @Override
  public void publish() {
    if (!hasController) {
      throw new IllegalStateException("No controller to publish to!");
    }
    controller.give(strategy.chooseMove(model));
  }

  /**
   * Notifies this player when the active turn has changed.
   *
   * @param t the turn it has changed to.
   */
  @Override
  public void nudge(Turn t) {
    if (t == null) {
      throw new IllegalArgumentException("Turn cannot be null!");
    }
    if (!hasController) {
      throw new IllegalStateException("No controller to publish to!");
    }
    if (t.equals(this.turn)) {
      publish(); // If the player is notified that it's their turn
    }
    // Else do nothing.
  }

  /**
   * Finds who's turn it is.
   *
   * @return Turn the turn
   */
  @Override
  public Turn getTurn() {
    return this.turn;
  }
}
