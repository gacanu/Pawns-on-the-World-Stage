package cs3500.pawns.model;

import java.util.List;

/**
 * Behaviors for a game of Queens Blood.
 *
 * @param <C> the type of card used to play the particular implementation of the game.
 */
public interface Game {

  /**
   * starting conditions for a game of queens blood.
   *
   * @param deck1 player 1's deck.
   * @param deck2 player 2's deck.
   */
  public void startGame(List<Card> deck1, List<Card> deck2);

  /**
   * places the given card index in a given position.
   *
   * @param cardIndex the index that the card is located.
   * @param x         the column number.
   * @param y         the row number.
   */
  public void placeCardInPosition(int cardIndex, int x, int y);

  /**
   * Grabs the cell at the given position.
   *
   * @param x the column number.
   * @param y the row number.
   * @return the cell contents at the given position.
   */
  public Cell getCell(int x, int y);

  /**
   * ending conditions for a game of queens blood.
   *
   * @return true if the game is over.
   */
  public boolean isGameOver();
}
