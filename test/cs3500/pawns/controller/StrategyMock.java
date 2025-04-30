package cs3500.pawns.controller;

import cs3500.pawns.model.Card;
import cs3500.pawns.model.Cell;
import cs3500.pawns.model.PawnsBoardModel;
import cs3500.pawns.model.Turn;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** A mock for testing strategies. Appends actions to the log given upon construction. */
public class StrategyMock implements PawnsBoardModel {
  // Needs protection for the subclasses of this class.
  protected Appendable log;

  /**
   * Constructs a strategyMock with a log to check up on actions taken.
   *
   * @param log the log to append to.
   */
  public StrategyMock(Appendable log) {
    this.log = log;
  }

  /** skips a turn. A game only ends when two players skip the same turn. */
  @Override
  public void passTurn() {
    // Not needed for this mock.
  }

  /**
   * starting conditions for a game of queens blood.
   *
   * @param deck1 player 1's deck.
   * @param deck2 player 2's deck.
   */
  @Override
  public void startGame(List<Card> deck1, List<Card> deck2, int handSize, Boolean shuffle) {
    // Not needed for this mock.
  }

  /**
   * places the given card index in a given position.
   *
   * @param cardIndex the index that the card is located.
   * @param x the column number.
   * @param y the row number.
   */
  @Override
  public void placeCardInPosition(int cardIndex, int x, int y) {
    // Not needed for this mock.
  }

  /** Grabs the first card from the deck and adds it to your hand. */
  @Override
  public void drawCard() {
    // Not needed for this mock.
  }

  /**
   * Adds a notifiable, which is given a signal with the current turn when the turn changes.
   *
   * @param n the notifiable object to notify when the turn switches.
   */
  @Override
  public void addNotified(Notifiable n) {
    // not needed
  }

  /**
   * Sends a notification to all added notifiables. If none are added, nothing happens. In our
   * implementation, this tells the CPU Controller to act, and the Player Controller to take input.
   * (The controller interface extends the notifiable interface).
   */
  @Override
  public void sendNotifToAll() {
    // not needed
  }

  /**
   * gets this width.
   *
   * @return the width
   */
  @Override
  public int getWidth() {
    return 3;
  }

  /**
   * gets this height.
   *
   * @return the height
   */
  @Override
  public int getHeight() {
    return 3;
  }

  /**
   * places the given card index in a given position.
   *
   * @param cardIdx the index that the card is located.
   * @param x the column number.
   * @param y the row number.
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
      log.append("It was invalid." + System.lineSeparator());
      return false;
    } catch (IOException ignored) {
    }
    return false;
  }

  /**
   * Grabs the cell at the given position.
   *
   * @param x the column number.
   * @param y the row number.
   * @return the cell contents at the given position.
   */
  @Override
  public Cell getCell(int x, int y) {
    return null;
  }

  /**
   * ending conditions for a game of queens blood.
   *
   * @return true if the game is over.
   */
  @Override
  public boolean isGameOver() {
    return false;
  }

  /**
   * Returns a copy of the given Turn's hand.
   *
   * @param player which player's deck to get.
   * @return the given Turn's hand.
   */
  @Override
  public List<Card> getHand(Turn player) {
    try {
      log.append("Got hand of player " + player.toString() + System.lineSeparator());
    } catch (IOException ignored) {

    }
    List<Card> deck = DeckReader.readFile(Turn.PLAYER1, Path.of("docs/deck.config"));
    List<Card> hand = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      hand.add(deck.get(i));
    }
    return hand; // I probably don't need to do this, but better safe than sorry.
  }

  /**
   * Returns the array of scores for a given player.
   *
   * @param player whose scores to return.
   * @return that player's scores.
   */
  @Override
  public int[] getScores(Turn player) {
    try {
      log.append("Checked scores for " + player.toString() + System.lineSeparator());
    } catch (IOException ignored) {

    }
    if (player == Turn.PLAYER1) {
      return new int[] {5, 5, 1, 5, 5};
    }
    return new int[] {0, 0, 1, 0, 0}; // This is when we do a little lying.
  }

  /**
   * Return what turn it is.
   *
   * @return whose turn it is.
   */
  @Override
  public Turn getTurn() {
    try {
      log.append("Checked what turn it was." + System.lineSeparator());
    } catch (IOException ignored) {

    }
    return Turn.PLAYER1;
  }

  /**
   * Get the total score. For each row, scores are only counted if they are greater than the
   * opponent's score on that same row.
   *
   * @param player whose score to return.
   * @return that player's total score.
   */
  @Override
  public int getTotalScore(Turn player) {
    return 0;
  }

  /**
   * creates a copy of the board.
   *
   * @return a board copy
   */
  @Override
  public Cell[][] createBoardCopy() {
    try {
      log.append("Created a copy of the board." + System.lineSeparator());
    } catch (IOException ignored) {
    }
    return null;
  }

  /**
   * Returns the game's winner.
   *
   * @return the game's winner, if there is any.
   */
  @Override
  public Turn whoWon() {
    return null;
  }

  /**
   * which player owns the card or pawns in a cell at a given coordinate.
   *
   * @param x the given x coordinate
   * @param y the given y coordinate
   * @return the player who owns this spot
   */
  @Override
  public Turn ownership(int x, int y) {
    return null;
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
      intArray = new int[] {5, 1, 5};
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
