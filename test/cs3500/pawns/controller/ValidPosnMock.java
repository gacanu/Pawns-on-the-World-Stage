package cs3500.pawns.controller;

import cs3500.pawns.model.Turn;

import java.io.IOException;

/** A mock for testing valid. Appends actions to the log given upon construction. */
public class ValidPosnMock extends StrategyMock {
  public ValidPosnMock(Appendable log) {
    super(log);
  }

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
    if (x == 1 && y == 1) {
      try {
        log.append("It was valid." + System.lineSeparator());
        return true;
      } catch (IOException ignored) {
      }
    }
    try {
      log.append("It was invalid." + System.lineSeparator());
    } catch (IOException ignored) {
    }
    return false;
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
      intArray = new int[] {5, 5, 5};
    } else {
      intArray = new int[] {1, 2, 1};
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
}
