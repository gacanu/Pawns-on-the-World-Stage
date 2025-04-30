package cs3500.pawns.controller;

import cs3500.pawns.model.Card;
import cs3500.pawns.model.Cell;
import cs3500.pawns.model.PawnsBoardModel;
import cs3500.pawns.model.Turn;

import java.util.ArrayList;
import java.util.List;

/** ControllerModelMock Tests. */
public class ControllerModelMock implements PawnsBoardModel {

  private final List<Notifiable> notifList = new ArrayList<>();
  private Turn turn;

  public ControllerModelMock() {
    this.turn = Turn.PLAYER1;
  }

  /** Skips a turn. A game only ends when two players skip the same turn. */
  @Override
  public void passTurn() {

    this.turn = this.turn.getOther();
  }

  /**
   * starting conditions for a game of queens blood.
   *
   * @param deck1 player 1's deck.
   * @param deck2 player 2's deck.
   * @param handSize handsize of the player
   * @param shuffle shuffling the deck
   */
  @Override
  public void startGame(List<Card> deck1, List<Card> deck2, int handSize, Boolean shuffle) {
    // startgame method
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
    this.turn = turn.getOther();
  }

  /** Grabs the first card from the deck and adds it to your hand. */
  @Override
  public void drawCard() {
    // drawCard method
  }

  /**
   * Adds a notifiable, which is given a signal with the current turn when the turn changes.
   *
   * @param n the notifiable object to notify when the turn switches.
   */
  @Override
  public void addNotified(Notifiable n) {
    notifList.add(n);
  }

  /**
   * Sends a notification to all added notifiables. If none are added, nothing happens. In our
   * implementation, this tells the CPU Controller to act, and the Player Controller to take input.
   * (The controller interface extends the notifiable interface).
   */
  @Override
  public void sendNotifToAll() {
    for (Notifiable n : notifList) {
      n.sendNotif(this.turn);
    }
  }

  /**
   * gets this width.
   *
   * @return the width
   */
  @Override
  public int getWidth() {
    return 0;
  }

  /**
   * gets this height.
   *
   * @return the height
   */
  @Override
  public int getHeight() {
    return 0;
  }

  /**
   * places the given card index in a given position.
   *
   * @param player the current player
   * @param cardIdx the index that the card is located.
   * @param x the column number.
   * @param y the row number.
   */
  @Override
  public boolean checkPosition(Turn player, int cardIdx, int x, int y) {
    return x == 0 && y == 0;
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
    return List.of();
  }

  /**
   * Returns the array of scores for a given player.
   *
   * @param player whose scores to return.
   * @return that player's scores.
   */
  @Override
  public int[] getScores(Turn player) {
    return new int[0];
  }

  /**
   * Return what turn it is.
   *
   * @return whose turn it is.
   */
  @Override
  public Turn getTurn() {
    return this.turn;
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
    return new Cell[0][];
  }

  /**
   * Returns the game's winner.
   *
   * @return the game's winner, if there is any.
   */
  @Override
  public Turn whoWon() {
    return this.turn;
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
    return 0;
  }
}
