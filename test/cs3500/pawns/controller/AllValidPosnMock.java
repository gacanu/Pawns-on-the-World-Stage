package cs3500.pawns.controller;

import cs3500.pawns.model.Turn;

import java.io.IOException;

/** A mock board with all positions marked as valid. */
public class AllValidPosnMock extends StrategyMock {
  /**
   * Constructor. Has an appendable which to append the log of actions to. Calls the super as this
   * class only changes checkPosition slightly and has no fields.
   *
   * @param log the appendable to append to.
   */
  public AllValidPosnMock(Appendable log) {
    super(log);
  }

  /**
   * Checks the position is valid. All positions are valid, according to this mock.
   *
   * @param player which player to check.
   * @param cardIdx the index that the card is located.
   * @param x the column number.
   * @param y the row number.
   * @return true. All positions, no matter what, are valid.
   */
  @Override
  public boolean checkPosition(Turn player, int cardIdx, int x, int y) {
    try {
      log.append(
          "Checked position ("
              + x
              + ", "
              + y
              + ") was valid trying to place card index "
              + cardIdx
              + " - ");
    } catch (IOException ignored) {
    }
    try {
      log.append("It was valid." + System.lineSeparator());
    } catch (IOException ignored) {
    }
    return true;
  }
}
