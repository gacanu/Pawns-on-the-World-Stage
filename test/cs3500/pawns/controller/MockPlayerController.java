package cs3500.pawns.controller;

import cs3500.pawns.model.Turn;
import cs3500.pawns.view.ViewFeatures;

/** A mock for the human controller. */
public class MockPlayerController extends MockCPUController
    implements ViewFeatures, QueensBloodController {

  private final Turn turn;

  public MockPlayerController(StringBuilder out, Turn t) {
    super(out, t);
    this.turn = t;
  }

  /**
   * handles the board clicks.
   *
   * @param x the x the user clicked
   * @param y the y the user clicked
   */
  @Override
  public void handleBoardClick(int x, int y) {
    // not needed
  }

  /**
   * handles the hand clicks.
   *
   * @param index the index the user clicked
   */
  @Override
  public void handleHandClick(int index) {
    // not needed
  }

  /** handles the confirmations of moves. */
  @Override
  public void handleConfirm() {
    // not needed
  }

  /** handles the passing of turns. */
  @Override
  public void handlePass() {
    // not needed
  }

  @Override
  public void sendNotif(Turn t) {
    out.append("Received notification -");
    if (t.equals(this.turn)) {
      out.append("this controller's turn is now active.\n");
    } else {
      out.append("this controller's turn is now inactive.\n");
    }
  }
}
