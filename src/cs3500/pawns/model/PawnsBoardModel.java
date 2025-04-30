package cs3500.pawns.model;

import cs3500.pawns.controller.Notifiable;

import java.util.List;

/** interface to represent a pawns board model that extends the read only pawns model. */
public interface PawnsBoardModel extends ReadonlyPawnsBoardModel {

  /** Skips a turn. A game only ends when two players skip the same turn. */
  void passTurn();

  /**
   * starting conditions for a game of queens blood.
   *
   * @param deck1 player 1's deck.
   * @param deck2 player 2's deck.
   */
  void startGame(List<Card> deck1, List<Card> deck2, int handSize, Boolean shuffle);

  /**
   * places the given card index in a given position.
   *
   * @param cardIndex the index that the card is located.
   * @param x the column number.
   * @param y the row number.
   */
  void placeCardInPosition(int cardIndex, int x, int y);

  /** Grabs the first card from the deck and adds it to your hand. */
  void drawCard();

  /**
   * Adds a notifiable, which is given a signal with the current turn when the turn changes.
   *
   * @param n the notifiable object to notify when the turn switches.
   */
  void addNotified(Notifiable n);

  /**
   * Sends a notification to all added notifiables. If none are added, nothing happens. In our
   * implementation, this tells the CPU Controller to act, and the Player Controller to take input.
   * (The controller interface extends the notifiable interface).
   */
  void sendNotifToAll();
}
