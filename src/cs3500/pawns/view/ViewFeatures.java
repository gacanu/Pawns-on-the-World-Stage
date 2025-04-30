package cs3500.pawns.view;

/**
 * Rewrite this later, but this is basically things the controller does that the view wants to adapt
 * to. This will be implemented by the controller and subscribed to in the view.
 */
public interface ViewFeatures {
  /**
   * handles the board clicks.
   *
   * @param x the x the user clicked
   * @param y the y the user clicked
   */
  void handleBoardClick(int x, int y);

  /**
   * handles the hand clicks.
   *
   * @param index the index the user clicked
   */
  void handleHandClick(int index);

  /** handles the confirmations of moves. */
  void handleConfirm();

  /** handles the passing of turns. */
  void handlePass();
}
