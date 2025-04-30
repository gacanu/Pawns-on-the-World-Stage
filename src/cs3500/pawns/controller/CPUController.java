package cs3500.pawns.controller;

import cs3500.pawns.model.PawnsBoardModel;
import cs3500.pawns.model.Turn;
import cs3500.pawns.view.QueensBloodView;

/**
 * Controller for the CPU plays that handles all of the. actions of the CPU and send notifications
 * to update the game.
 */
public class CPUController implements QueensBloodController {
  protected final PawnsBoardModel model;
  protected final QueensBloodView view;
  private final Player player;
  private final Turn turn;
  private boolean done = false;

  /**
   * Constructor for the CPUController Class.
   *
   * @param model the game model to work with
   * @param player the CPU player with thsi controller
   * @param view the view for game updates
   */
  public CPUController(PawnsBoardModel model, Player player, QueensBloodView view) {
    if (model == null || player == null || view == null) {
      throw new IllegalArgumentException("PawnsBoardModel, Player cannot be null");
    }
    this.view = view;
    this.model = model;
    this.player = player;
    this.turn = player.getTurn();
    this.player.subscribe(this);
  }

  /**
   * places the given card index at the given x-y coords.
   *
   * @param x x coord
   * @param y y coord
   * @param cardIdx card index
   */
  @Override
  public void placeCard(int x, int y, int cardIdx) {
    // System.out.println("card placed");
    try {
      model.placeCardInPosition(cardIdx, x, y);
    } catch (IllegalArgumentException e) {
      view.showDialog(e.getMessage());
    }
  }

  /**
   * is it their turn.
   *
   * @return true if its their turn
   */
  protected boolean getTurn() {
    // System.out.println(this.turn + " " +  model.getTurn());
    return this.turn.equals(model.getTurn());
  }

  /** passes the players turn. */
  @Override
  public void pass() {
    if (getTurn()) {
      try {
        model.passTurn();
      } catch (IllegalArgumentException e) {
        view.showDialog(e.getMessage());
      }
    }
  }

  /**
   * gives the controller a specific move.
   *
   * @param m move given
   */
  @Override
  public void give(Move m) {
    if (getTurn()) {
      try {
        m.execute(model);
      } catch (IllegalArgumentException e) {
        view.showDialog(e.getMessage());
      }
    }
  }

  /**
   * sends the notification, checks who's turn it is, would send notification, if the game is over
   * will the announce winner.
   *
   * @param t the turn in the game.
   */
  @Override
  public void sendNotif(Turn t) {
    System.out.println(t);
    if (model.isGameOver() && !done) {
      Turn winner = model.whoWon();
      view.showDialog(
          "Game over! "
              + winner.getColor()
              + " won with "
              + model.getTotalScore(winner)
              + " points!");
      done = true;
      return;
    }
    if (model.isGameOver() && done) {
      return;
    }
    if (getTurn()) {
      this.player.nudge(model.getTurn());
    }
    view.clearSelections();
    view.update();
  }
}
