package cs3500.pawns.controller;

import cs3500.pawns.model.Cell;
import cs3500.pawns.model.QueensBlood;
import cs3500.pawns.model.Turn;
import cs3500.pawns.view.ViewFeatures;
import cs3500.pawns.view.QueensBloodView;

/** a controller stub to help play the game. */
public class StubController implements QueensBloodController, ViewFeatures {
  QueensBloodView view;

  /**
   * the stub controller constructor.
   *
   * @param view the given queens blood view.
   */
  public StubController(QueensBloodView view) {
    this.view = view;
    this.view.addFeature(this);
  }

  /**
   * Starts and plays the game using user input from the player.
   *
   * @param model state of the game
   * @param view view for the model
   * @param shuffle true iff the deck should be shuffled.
   * @param handSize the starting hand size
   * @throws IllegalStateException if controller is unable to successfully receive input, transmit
   *     output, or if the game cannot be started
   * @throws IllegalArgumentException if the model or view are null
   */
  public <C extends Cell> void playGame(
      QueensBlood model, QueensBloodView view, boolean shuffle, int handSize) {
    System.out.println("Started game!");
  }

  /**
   * handles the board clicks.
   *
   * @param x the x the user clicked
   * @param y the y the user clicked
   */
  @Override
  public void handleBoardClick(int x, int y) {
    System.out.println("User clicked at board coordinate (" + x + "," + y + ")");
  }

  /**
   * handles the hand clicks.
   *
   * @param index the index the user clicked
   */
  @Override
  public void handleHandClick(int index) {
    System.out.println("User clicked at hand index " + index);
  }

  /** handles the confirmations of moves. */
  @Override
  public void handleConfirm() {
    System.out.println("User confirmed move!");
  }

  /** handles the passing of turns. */
  @Override
  public void handlePass() {
    System.out.println("User passed turn!");
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
    // Unneeded Stub method
  }

  /** passes the players turn. */
  @Override
  public void pass() {
    // Unneeded Stub method
  }

  /**
   * gives the controller a specific move.
   *
   * @param m move given
   */
  @Override
  public void give(Move m) {
    // Unneeded Stub method
  }

  /**
   * send notif to all notifiable when turn switches.
   *
   * @param turn the turn in the game.
   */
  @Override
  public void sendNotif(Turn turn) {
    // Unneeded Stub method
  }
}
