package cs3500.pawns.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Represents a game of Queen's Blood.
 */
public class QueensBlood {
  private final Cell[][] board;
  private List<Card> deck1; // player 1's deck
  private List<Card> deck2; // player 2's deck
  private List<Card> hand1; // player 1's deck
  private List<Card> hand2; // player 2's deck
  private int[] score1; // player 1's top-to-bottom list of scores.
  private int[] score2; // player 2's top-to-bottom list of scores.
  private boolean started;
  private final Random random;
  private Player turn;
  private boolean gameOver;
  private int skipped; //Increments up by one when a player passes their turn, and resets otherwise
  //Tells the game when to end

  /**
   * a constructor to represent the start of QueensBlood.
   *
   * @param width  how many columns are in this game.
   * @param height how many rows are in this game.
   * @param r      random variable.
   */
  public QueensBlood(int width, int height, Random r) {
    if (r == null) {
      throw new IllegalArgumentException("Random can't be null!");
    }

    if (width < 1 || height < 1 || width % 2 == 0 || height % 2 == 0) {
      throw new IllegalArgumentException("Width and height must be odd and greater than 1!");
    }

    this.board = new Cell[width][height];

    for (int i = width - 1; i >= 0; i--) {
      for (int j = height - 1; j >= 0; j--) {
        if(i == width - 1) {
          this.board[i][j] = new Pawns(1, Player.PLAYER1); //PLAYER1 and 2 here may need to be swapped later.
        }
        else if(i == 0) {
          this.board[i][j] = new Pawns(1, Player.PLAYER2);
        }
        else {
          this.board[i][j] = new Pawns(0, null);
        }
      }
    }

    this.deck1 = null;
    this.deck2 = null;
    this.hand1 = null;
    this.hand2 = null;
    this.score1 = new int[height];
    this.score2 = new int[height];
    this.random = r;
    this.turn = Player.PLAYER1;
    this.started = false;
    this.gameOver = false;
    this.skipped = 0;

    // sets both player's scores to zero
    for (int k = height - 1; k >= 0; k--) {
      this.score1[k] = 0;
      this.score2[k] = 0;
    }
  }

  /**
   * Skips a turn. A game only ends when two players skip the same turn.
   */
  public void passTurn() {
    if(!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    skipped ++;
    if(skipped == 2) {
      this.gameOver = true;
    }
    endTurn();
  }


  //Checks how many positions on the board don't have cards in them
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
  public void startGame(List<Card> deck1, List<Card> deck2, int handSize, Boolean shuffle) {
    if(this.started) {
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
    //TODO: check if either deck contains triplicates
    this.started = true;
    this.deck1 = deck1;
    this.deck2 = deck2;
    if(shuffle) {
      Collections.shuffle(this.deck1, this.random); //Should both decks shuffle the same way?
      Collections.shuffle(this.deck2, this.random); //As in should two identical decks get shuffled exactly the same
    }
    this.hand1 = new ArrayList<>();
    this.hand2 = new ArrayList<>();
  }

  // This is probably how this should be setup
  // [0, 0] [1, 0] [2, 0]
  // [0, 1] [1, 1] [2, 1]
  // [0, 2] [1, 2] [2, 2]

  /**
   * places the given card index in a given position.
   *
   * @param cardIndex the index that the card is located.
   * @param x         the column number.
   * @param y         the row number.
   */
  public void placeCardInPosition(int cardIndex, int x, int y) {
    if(!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    Card toPlace;

    try {
      if (this.turn == Player.PLAYER1) {
        toPlace = hand1.remove(cardIndex);
      } else {
        toPlace = hand2.remove(cardIndex);
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Illegal hand index!");
    }

    try {
      if(this.board[x][y].getPawns() != -1 && this.board[x][y].getAffiliation() == this.turn) {
        this.board[x][y] = toPlace;
        applyInfluence(toPlace.getInfluence(), x, y);
      } else {
        throw new IllegalArgumentException("There's already a card there!");
      }
    }

    catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Illegal board indices!");
    }
    skipped = 0;
    endTurn();
  }

  //Called by passTurn as well.
  private void endTurn() {
    if (this.turn == Player.PLAYER1) {
      this.turn = Player.PLAYER2;
    } else {
      this.turn = Player.PLAYER1;
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
        if (inf[i][j]) {
          //Hmm
          placePawn(x + i - 2, y + j - 2);
        }
      }
    }
  }


  /**
   * Grabs the first card from the deck and adds it to your hand.
   */
  public void drawCard() {
    if(!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    try {
      if (this.turn == Player.PLAYER1) {
        hand1.add(deck1.remove(0));
      } else {
        hand2.add(deck2.remove(0));
      }
    }
    catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException(this.turn.getColor() + "'s deck has no cards and cannot draw!");
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
    }
    catch (IndexOutOfBoundsException ignored) {
      //Do nothing here; it's not catastrophic if a pawn tries to get placed out of bounds
    }
  }

  /**
   * Grabs the cell at the given position.
   *
   * @param x the column number.
   * @param y the row number.
   * @return the cell contents at the given position.
   */
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
  public boolean isGameOver() {
    if (!this.started) {
      throw new IllegalArgumentException("The game hasn't even started yet!");
    }
    return this.gameOver;
  }

  /**
   * Returns a copy of the given Player's hand.
   * @param player which player's deck to get.
   * @return the given Player's hand.
   */
  public List<Card> getHand(Player player) {
    if(!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    if(player == Player.PLAYER1) {
      return new ArrayList<>(this.hand1);
    }
    return new ArrayList<>(this.hand2);
  }



}












