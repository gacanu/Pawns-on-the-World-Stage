package cs3500.pawns.model;

import cs3500.pawns.controller.Notifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/** Represents a game of Queen's Blood. */
public class QueensBlood implements PawnsBoardModel {
  private final List<Notifiable> notifList;
  private final Cell[][] board;
  private final Random random;
  private final int width;
  // INVARIANT: This is an odd integer greater than 0.
  private final int height;
  private final int[] score1; // player 1's top-to-bottom list of scores.
  private final int[] score2; // player 2's top-to-bottom list of scores.
  private List<Card> deck1; // player 1's deck
  private List<Card> deck2; // player 2's deck
  private List<Card> hand1; // player 1's hand
  private List<Card> hand2; // player 2's hand
  private boolean started;
  private Turn turn;
  private boolean gameOver;
  private int skipped;
  private boolean sentGameOverMessage;

  // Increments up by one when a player passes their turn, and resets otherwise

  /**
   * a constructor to represent the start of QueensBlood.
   *
   * @param width how many columns are in this game.
   * @param height how many rows are in this game.
   * @param r random variable.
   */
  public QueensBlood(int width, int height, Random r) {
    this(width, height, r, null);
  }

  /**
   * a constructor to represent the start of QueensBlood. This constructor specifically is for
   * testing purposes.
   *
   * @param width how many columns are in this game.
   * @param height how many rows are in this game.
   * @param r random variable.
   */
  protected QueensBlood(int width, int height, Random r, Cell[][] c) {
    if (r == null) {
      throw new IllegalArgumentException("Random can't be null!");
    }

    if (width < 1 || height < 1 || width % 2 == 0 || height % 2 == 0) {
      throw new IllegalArgumentException("Width and height must be odd and greater than 1!");
    }

    if (c != null) {
      board = c;
    } else {
      board = getCells(width, height);
    }

    this.deck1 = null;
    this.deck2 = null;
    this.hand1 = null;
    this.hand2 = null;
    this.score1 = new int[height];
    this.score2 = new int[height];
    this.random = r;
    this.turn = Turn.PLAYER1;
    this.started = false;
    this.gameOver = false;
    this.skipped = 0;
    this.width = width;
    this.height = height;

    // sets both player's scores to zero
    for (int k = height - 1; k >= 0; k--) {
      this.score1[k] = 0;
      this.score2[k] = 0;
    }

    notifList = new ArrayList<>();
  }

  /**
   * gets the cells at the given width and height.
   *
   * @param width the x value
   * @param height the y value
   * @return a new cell with that width and height
   */
  private Cell[][] getCells(int width, int height) {
    Cell[][] newBoard = new Cell[width][height];
    for (int i = width - 1; i >= 0; i--) {
      for (int j = height - 1; j >= 0; j--) {
        if (i == width - 1) {
          newBoard[i][j] =
              new Pawns(1, Turn.PLAYER2); // PLAYER1 and 2 here may need to be swapped later.
        } else if (i == 0) {
          newBoard[i][j] = new Pawns(1, Turn.PLAYER1);
        } else {
          newBoard[i][j] = new Pawns(0, null);
        }
      }
    }
    return newBoard;
  }

  /**
   * gets this width.
   *
   * @return the width
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * gets this height.
   *
   * @return the height
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /** Skips a turn. A game only ends when two players skip the same turn. */
  @Override
  public void passTurn() {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    skipped++;
    if (skipped == 2) {
      this.gameOver = true;
    }
    endTurn();
  }

  /**
   * Checks how many positions on the board don't have cards in them.
   *
   * @return the number of empty spots
   */
  private int getEmptySpots() {
    int soln = 0;
    for (Cell[] a : board) {
      for (Cell c : a) {
        if (c.getPawns() != -1) {
          soln++;
        }
      }
    }
    return soln;
  }

  /**
   * starting conditions for a game of queens blood.
   *
   * @param deck1 player 1's deck.
   * @param deck2 player 2's deck.
   */
  @Override
  public void startGame(List<Card> deck1, List<Card> deck2, int handSize, Boolean shuffle) {
    if (this.started) {
      throw new IllegalStateException("You've already started this game!");
    }
    if (deck1 == null || deck2 == null || deck1.isEmpty() || deck2.isEmpty()) {
      throw new IllegalArgumentException("Decks can't be null or empty!");
    }

    if (deck1.contains(null) || deck2.contains(null)) {
      throw new IllegalArgumentException("Decks cannot contain a null value!");
    }

    if (deck1.size() < getEmptySpots()) {
      throw new IllegalArgumentException("Deck 1 does not have enough cards to fill the board!");
    }

    if (deck2.size() < getEmptySpots()) {
      throw new IllegalArgumentException("Deck 2 does not have enough cards to fill the board!");
    }

    if (handSize > (deck1.size() / 3)) {
      throw new IllegalArgumentException(
          "Hand size cannot be greater than a third"
              + "of the size of either deck - deck 1 is too small.");
    }

    if (handSize > (deck2.size() / 3)) {
      throw new IllegalArgumentException(
          "Hand size cannot be greater than a third"
              + "of the size of either deck - deck 2 is too small.");
    }

    if (handSize <= 0) {
      throw new IllegalArgumentException("Handsize must be at least 1!");
    }

    if (checkTriplicates(deck1)) {
      throw new IllegalArgumentException("Deck 1 has triplicates! That's not allowed!");
    }

    if (checkTriplicates(deck2)) {
      throw new IllegalArgumentException("Deck 2 has triplicates! That's not allowed!");
    }

    this.started = true;
    this.deck1 = deck1;
    this.deck2 = deck2;
    if (shuffle) {
      Collections.shuffle(this.deck1, this.random); // Should both decks shuffle the same way?
      Collections.shuffle(
          this.deck2,
          this.random); // As in should two identical decks get shuffled exactly the same
    }

    // probably not >_>
    this.hand1 = new ArrayList<>();
    this.hand2 = new ArrayList<>();
    for (int i = 0; i < handSize; i++) {
      this.hand1.add(this.deck1.remove(0)); // Populating each hand with cards from each deck
      this.hand2.add(this.deck2.remove(0));
    }
    sendNotifToAll();
  }

  /**
   * check if there's three of a card.
   *
   * @param deck given deck of cards
   * @return true if there are three of any card
   */
  private boolean checkTriplicates(List<Card> deck) {
    Map<Card, Integer> amts = new HashMap<>();
    for (Card c : deck) {
      int num = 0;
      if (amts.get(c) != null) {
        num = amts.get(c); // how many of this item exist in the deck so far?
      }
      if (num == 2) {
        return true; // There are 3 of this item
      }
      amts.put(c, num + 1); // We've got that item, increment the count by one
    }
    return false;
  }

  // The coordinates of the
  // [0, 0] [1, 0] [2, 0]
  // [0, 1] [1, 1] [2, 1]
  // [0, 2] [1, 2] [2, 2]

  /**
   * places the given card index in a given position.
   *
   * @param cardIndex the index that the card is located.
   * @param x the column number.
   * @param y the row number.
   */
  @Override
  public void placeCardInPosition(int cardIndex, int x, int y) {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }

    Card toPlace;

    try {
      if (this.turn == Turn.PLAYER1) {
        toPlace = hand1.remove(cardIndex);
      } else {
        toPlace = hand2.remove(cardIndex);
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Illegal hand index!");
    }

    try {
      if (this.board[x][y].getPawns() != -1
          && this.board[x][y].getAffiliation() == this.turn
          && toPlace.getCost() <= this.board[x][y].getPawns()) {
        this.board[x][y] = toPlace;
        applyInfluence(toPlace.getInfluence(), x, y);
        drawCard();
        updateScores();
      } else {
        if (this.board[x][y].getAffiliation() != this.turn) {
          throw new IllegalArgumentException("Those aren't your pawns!");
        }
        if (toPlace.getCost() > this.board[x][y].getPawns()) {
          throw new IllegalArgumentException("You don't have enough pawns to do that!");
        }
        throw new IllegalArgumentException("There's already a card there!");
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Illegal board indices!");
    }
    skipped = 0;
    endTurn();
  }

  /**
   * Checks if the given position and hand index is valid.
   *
   * @param player the player's hand to check.
   * @param cardIdx the card in the player's hand to check.
   * @param x the x position on the board to check.
   * @param y the y position on the board to check.
   * @return if the given position and hand index is valid.
   */
  @Override
  public boolean checkPosition(Turn player, int cardIdx, int x, int y) {
    try {
      if (!(getCell(x, y).getAffiliation().equals(player))) {
        throw new IllegalArgumentException(":-)");
      }
      checkHand(player, cardIdx, x, y);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * gets a players card from their hand.
   *
   * @param player the player
   * @param cardIdx the card index
   */
  private void checkHand(Turn player, int cardIdx, int x, int y) {
    try {
      Card inQuestion;
      if (player == Turn.PLAYER1) {
        inQuestion = this.hand1.get(cardIdx);
      } else {
        inQuestion = this.hand2.get(cardIdx);
      }
      if (inQuestion.getCost() > getCell(x, y).getPawns()) {
        throw new IllegalArgumentException();
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException(":-D");
    }
  }

  /** ends the players turn, called by passTurn as well. */
  private void endTurn() {
    if (this.turn == Turn.PLAYER1) {
      this.turn = Turn.PLAYER2;
    } else {
      this.turn = Turn.PLAYER1;
    }
    sendNotifToAll();
  }

  /** updates the current score. */
  private void updateScores() {
    for (int i = 0; i < this.height; i++) {
      int val1 = 0;
      int val2 = 0;
      for (Cell[] a : board) {
        if (a[i].getAffiliation() == Turn.PLAYER1) {
          val1 += a[i].getValue();
        } else {
          val2 += a[i].getValue();
        }
      }
      score1[i] = val1;
      score2[i] = val2;
    }
  }

  /**
   * Places pawns around the placed card per it's area of influence.
   *
   * @param inf the area of influence, grabbed from the card.
   */
  private void applyInfluence(Boolean[][] inf, int x, int y) {
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (inf[j][i]) {
          // Hmm
          placePawn(x + i - 2, y + j - 2);
        }
      }
    }
  }

  /** Grabs the first card from the deck and adds it to your hand. */
  @Override
  public void drawCard() {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    try {
      if (this.turn == Turn.PLAYER1) {
        hand1.add(deck1.remove(0));
      } else {
        hand2.add(deck2.remove(0));
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException(
          this.turn.getColor() + "'s deck has no cards and cannot draw!");
    }
  }

  /**
   * places a pawn into this position.
   *
   * @param x the column number.
   * @param y the row number.
   */
  private void placePawn(int x, int y) {
    try {
      this.board[x][y].addPawn(this.turn);
    } catch (IndexOutOfBoundsException ignored) {
      // Do nothing here; it's not catastrophic if a pawn tries to get placed out of bounds
    }
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
    try {
      return this.board[x][y];
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Illegal board indices!");
    }
  }

  /**
   * ending conditions for a game of queens blood.
   *
   * @return true if the game is over.
   */
  @Override
  public boolean isGameOver() {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't even started yet!");
    }
    return this.gameOver;
  }

  /**
   * Returns a copy of the given Turn's hand.
   *
   * @param player which player's deck to get.
   * @return the given Turn's hand.
   */
  @Override
  public List<Card> getHand(Turn player) {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    if (player == null) {
      throw new IllegalArgumentException("Given player cannot be null!");
    }
    List<Card> newHand = new ArrayList<>();
    List<Card> source;
    if (player == Turn.PLAYER1) {
      source = this.hand1;
    } else {
      source = this.hand2;
    }
    for (Card c : source) {
      newHand.add((Card) c.dupe());
    }
    return newHand;
  }

  /**
   * Returns the array of scores for a given player.
   *
   * @param player whose scores to return.
   * @return that player's scores.
   */
  @Override
  public int[] getScores(Turn player) {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }

    if (player == null) {
      throw new IllegalArgumentException("Given player cannot be null!");
    }

    if (player == Turn.PLAYER1) {
      return this.score1.clone();
    }
    return this.score2.clone();
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
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }

    if (player == null) {
      throw new IllegalArgumentException("Turn can't be null!");
    }

    int sc1 = 0;
    int sc2 = 0;
    for (int i = 0; i < this.height; i++) {
      if (score1[i] > score2[i]) {
        sc1 += score1[i];
      }
      if (score1[i] < score2[i]) {
        sc2 += score2[i];
      }
      // When neither win, neither are counted.
    }

    if (player == Turn.PLAYER1) {
      return sc1;
    }
    return sc2;
  }

  /**
   * Returns a player's score for that row-score.
   *
   * @param indx the card index
   * @param player the player to gather the row score for
   * @returna player's score for that row-score
   */
  public int rowScore(int indx, Turn player) {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }

    if (0 > indx || indx > height) {
      throw new IllegalArgumentException("given row out of bounds");
    }

    if (player == null) {
      throw new IllegalArgumentException("Turn can't be null!");
    }

    int rowTotal = 0;

    for (int i = 0; i <= this.width; i++) {
      if (board[i][indx].getAffiliation() == player) {
        rowTotal += board[i][indx].getValue();
      }
    }

    return rowTotal;
  }

  /**
   * which player owns the card or pawns in a cell at a given coordinate.
   *
   * @param w the width/x value
   * @param h the height/y value
   * @return the affiliation of the cell
   */
  public Turn ownership(int w, int h) {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    try {
      return board[w][h].getAffiliation();
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Index out of bounds!");
    }
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
    if (player == null) {
      throw new IllegalArgumentException("Turn can't be null!");
    }
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    if (player == Turn.PLAYER1) {
      return this.score1[row];
    } else {
      return this.score2[row];
    }
  }

  /**
   * returns the cell at the given board coordinates.
   *
   * @param x the x value
   * @param y the y value
   * @return the cell on the board
   */
  public int getValueAt(int x, int y) {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    try {
      return board[x][y].getValue();
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Index out of bounds!");
    }
  }

  /**
   * Returns which player won, if any. Returns null on a tie.
   *
   * @return which player won.
   */
  public Turn whoWon() {
    int p1score = getTotalScore(Turn.PLAYER1);
    int p2score = getTotalScore(Turn.PLAYER2);

    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    if (!this.isGameOver()) {
      throw new IllegalStateException("The game hasn't ended yet!");
    }
    if (p1score == p2score) {
      return null;
    }
    if (p1score > p2score) {
      return Turn.PLAYER1;
    }
    return Turn.PLAYER2;
  }

  /**
   * Creates a copy of this game's board, without aliasing it or its contents.
   *
   * @return a copy of this game's board.
   */
  @Override
  public Cell[][] createBoardCopy() {
    Cell[][] board = new Cell[this.width][this.height];
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        board[i][j] = this.board[i][j].dupe();
      }
    }
    return board;
  }

  /**
   * Adds a notifiable, which is given a signal with the current turn when the turn changes.
   *
   * @param n the notifiable object to notify when the turn switches.
   */
  public void addNotified(Notifiable n) {
    if (n == null) {
      throw new IllegalArgumentException("Notifiable cannot be null.");
    }

    notifList.add(n);
  }

  // notifList
  // turn

  /**
   * Sends a notification to all added notifiables. If none are added, nothing happens. In our
   * implementation, this tells the CPU Controller to act, and the Player Controller to take input.
   * (The controller interface extends the notifiable interface).
   */
  public void sendNotifToAll() {
    for (Notifiable n : notifList) {
      n.sendNotif(this.turn);
    }
  }
}
