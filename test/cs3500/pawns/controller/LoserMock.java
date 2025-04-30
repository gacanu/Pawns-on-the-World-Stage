package cs3500.pawns.controller;

import cs3500.pawns.model.Turn;

import java.io.IOException;

/** A mock with all valid positions, but Player1 is losing horribly. */
public class LoserMock extends AllValidPosnMock {

  /**
   * Constructor. Has an appendable which to append the log of actions to. Calls the super as this
   * class only changes checkPosition slightly and has no fields.
   *
   * @param log the appendable to append to.
   */
  public LoserMock(Appendable log) {
    super(log);
  }

  /**
   * Grabs the score in that row for the specified player.
   *
   * @param row the row to be grabbed.
   * @param player which player's score to grab.
   * @return the player's score.
   */
  @Override
  public int getRowScore(int row, Turn player) {
    int[] intArray;
    if (player == Turn.PLAYER1) {
      intArray = new int[] {1, 1, 1};
    } else {
      intArray = new int[] {99, 99, 99}; // Just getting absolutely demolished.
    }
    try {
      log.append(
          "Grabbed row-score ("
              + intArray[row]
              + ") "
              + player.toString()
              + " in row "
              + row
              + System.lineSeparator());
    } catch (IOException ignored) {

    }

    return intArray[row];
  }

  /**
   * gets this width.
   *
   * @return the width
   */
  @Override
  public int getWidth() {
    return 2;
  }

  /**
   * gets this height.
   *
   * @return the height
   */
  @Override
  public int getHeight() {
    return 2;
  }
}
