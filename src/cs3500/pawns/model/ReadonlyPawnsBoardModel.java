package cs3500.pawns.model;

import java.util.List;

/** this interface represents the read only pawns board model. */
public interface ReadonlyPawnsBoardModel {
  /**
   * gets this width.
   *
   * @return the width
   */
  int getWidth();

  /**
   * gets this height.
   *
   * @return the height
   */
  int getHeight();

  /**
   * places the given card index in a given position.
   *
   * @param cardIdx the index that the card is located.
   * @param x the column number.
   * @param y the row number.
   */
  boolean checkPosition(Turn player, int cardIdx, int x, int y);

  /**
   * Grabs the cell at the given position.
   *
   * @param x the column number.
   * @param y the row number.
   * @return the cell contents at the given position.
   */
  Cell getCell(int x, int y);

  /**
   * ending conditions for a game of queens blood.
   *
   * @return true if the game is over.
   */
  boolean isGameOver();

  /**
   * Returns a copy of the given Turn's hand.
   *
   * @param player which player's deck to get.
   * @return the given Turn's hand.
   */
  List<Card> getHand(Turn player);

  /**
   * Returns the array of scores for a given player.
   *
   * @param player whose scores to return.
   * @return that player's scores.
   */
  int[] getScores(Turn player);

  /**
   * Return what turn it is.
   *
   * @return whose turn it is.
   */
  Turn getTurn();

  /**
   * Get the total score. For each row, scores are only counted if they are greater than the
   * opponent's score on that same row.
   *
   * @param player whose score to return.
   * @return that player's total score.
   */
  int getTotalScore(Turn player);

  /**
   * creates a copy of the board.
   *
   * @return a board copy
   */
  Cell[][] createBoardCopy();

  /**
   * Returns the game's winner.
   *
   * @return the game's winner, if there is any.
   */
  Turn whoWon();

  /**
   * which player owns the card or pawns in a cell at a given coordinate.
   *
   * @param x the given x coordinate
   * @param y the given y coordinate
   * @return the player who owns this spot
   */
  Turn ownership(int x, int y);

  /**
   * Grabs the score in that row for the specified player.
   *
   * @param row the row to be grabbed.
   * @param player which player's score to grab.
   * @return the player's score.
   */
  int getRowScore(int row, Turn player);
}
