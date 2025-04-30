package cs3500.pawns.model;

import cs3500.pawns.provider.controller.ModelActions;

import cs3500.pawns.provider.model.PlayerColor;
import cs3500.pawns.provider.model.ProviderPlayer;
import cs3500.pawns.provider.model.ProviderIPawnsBoard;
import cs3500.pawns.provider.model.ProviderCell;
import cs3500.pawns.provider.model.ProviderCard;

import java.awt.Point;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** adapter for Pawns Board Model To IPawnsBoard Provider. */
public class PawnsBoardModelToIPawnsBoardProviderAdapter implements ProviderIPawnsBoard {
  private final PawnsBoardModel delegate;
  private final List<ModelActions> toNotify;
  private int redHandHighlighted = -1;
  private int blueHandHighlighted = -1;
  private Point blueBoardHighlighted = new Point(-1, -1);
  private Point redBoardHighlighted = new Point(-1, -1);

  /**
   * constructor for the adapter for Pawns Board Model To IPawnsBoard Provider.
   *
   * @param in the given model
   */
  public PawnsBoardModelToIPawnsBoardProviderAdapter(PawnsBoardModel in) {
    this.delegate = in;
    this.toNotify = new ArrayList<>();
  }

  /**
   * Makes a move on the board with the given player, using the card at a given hand indx at the
   * specified row and col.
   *
   * @param color player to make move from.
   * @param handIndex handindex to get card from.
   * @param row row to place card.
   * @param col col to place card.
   * @throws IllegalArgumentException if hand index, row, or col are incorrect.
   * @throws IllegalStateException if card can not be placed.
   */
  @Override
  public void makeMove(PlayerColor color, int handIndex, int row, int col) {
    // NOTE: This model takes in placements with their turn, instead of making a move on whatever
    // turn that is. Hmm.
    // This may cause problems later.
    if (TurnUtils.getTurn(color) == delegate.getTurn()) {
      delegate.placeCardInPosition(handIndex, row, col);
    } else {
      throw new IllegalArgumentException("Not " + color + "'s turn");
    }
  }

  /**
   * Passes a players turn.
   *
   * @param color player to pass turn for.
   */
  @Override
  public void pass(PlayerColor color) {
    if (TurnUtils.getTurn(color) == delegate.getTurn()) {
      delegate.passTurn();
    }
  }

  /**
   * Gets the current turn of the game. Returns the last player turn if game is over.
   *
   * @return the Player's color
   */
  @Override
  public PlayerColor getTurn() {
    return TurnUtils.getColor(delegate.getTurn());
  }

  // Notifies all ModelAction objects when the turn changes.
  private void onChangeTurn() {
    for (ModelActions a : this.toNotify) {
      a.handleNextTurn();
    }
  }

  /**
   * Returns the red players current hand as an array list.
   *
   * @return arraylist of cards of players current hand.
   */
  @Override
  public List<ProviderCard> getRedHand() {
    List<Card> hand = delegate.getHand(Turn.PLAYER1);
    List<ProviderCard> providerHand = new ArrayList<>();
    for (Card card : hand) {
      providerHand.add(new CustomerCardToProviderCardAdapter(card));
    }
    return providerHand;
  }

  /**
   * Returns the blue players current hand as an array list.
   *
   * @return arraylist of cards of players current hand.
   */
  @Override
  public List<ProviderCard> getBlueHand() {
    List<Card> hand = delegate.getHand(Turn.PLAYER2);
    List<ProviderCard> providerHand = new ArrayList<>();
    for (Card card : hand) {
      providerHand.add(new CustomerCardToProviderCardAdapter(card));
    }
    return providerHand;
  }

  /**
   * Returns true if the game is over, false if the game is still in progress.
   *
   * @return true or false representing game state.
   */
  @Override
  public boolean isGameOver() {
    return delegate.isGameOver();
  }

  /**
   * Checks if a move on the board with the given player, using the card at a given hand indx at the
   * specified row and col is valid.
   *
   * @param color player to make move from.
   * @param handIndex handindex to get card from.
   * @param row row to place card.
   * @param col col to place card.
   */
  @Override
  public boolean isValid(PlayerColor color, int handIndex, int row, int col) {
    return delegate.checkPosition(TurnUtils.getTurn(color), handIndex, row, col);
  }

  /**
   * Returns a copy of the board for this model.
   *
   * @return an 2D arraylist representing this board. (0 index based).
   */
  @Override
  public List<List<ProviderCell>> getBoard() {
    int width = delegate.getWidth();
    int height = delegate.getHeight();
    List<List<ProviderCell>> providerBoard = new ArrayList<>();
    for (int r = 0; r < height; r++) {
      List<ProviderCell> rowList = new ArrayList<>();
      for (int c = 0; c < width; c++) {
        rowList.add(new CustomerCellToProviderCellAdapter(delegate.getCell(c, r)));
      }
      providerBoard.add(rowList);
    }
    return providerBoard;
  }

  /**
   * Gets the scores for each row as a list of Map entries. The key represents reds score for the
   * associated row, and the value represents the blue score.
   *
   * @return an array list of map entries represent row scores.
   */
  @Override
  public List<Map.Entry<Integer, Integer>> getScores() {
    int height = delegate.getHeight();
    List<Map.Entry<Integer, Integer>> scores = new ArrayList<>();
    for (int row = 0; row < height; row++) {
      int redScore = delegate.getRowScore(row, Turn.PLAYER1);
      int blueScore = delegate.getRowScore(row, Turn.PLAYER2);
      scores.add(new AbstractMap.SimpleEntry<>(redScore, blueScore));
    }
    return scores;
  }

  /**
   * Returns the players current scores. Key represents Red player, value represents blue player.
   *
   * @return Key value pair representing score.
   */
  @Override
  public Map.Entry<Integer, Integer> getScore() {
    int redTotal = delegate.getTotalScore(Turn.PLAYER1);
    int blueTotal = delegate.getTotalScore(Turn.PLAYER2);
    return new AbstractMap.SimpleEntry<>(redTotal, blueTotal);
  }

  /**
   * Gets the width of this model's board.
   *
   * @return an int representing the width of this board.
   */
  @Override
  public int getWidth() {
    return delegate.getWidth();
  }

  /**
   * Returns the height of this model's board.
   *
   * @return an int representing the height of this models board.
   */
  @Override
  public int getHeight() {
    return delegate.getHeight();
  }

  /**
   * Returns the copy of the cell at the given coordinate.
   *
   * @param r integer
   * @param c integer
   * @return a copy of the cell.
   */
  @Override
  public ProviderCell getCell(int r, int c) {
    return new CustomerCellToProviderCellAdapter(delegate.getCell(r, c));
  }

  /**
   * Gets the hand that is currently highlighted given a specific player.
   *
   * @param color of player to assoicate with highlight.
   * @return an int representing the card index, -1 if no card is highlighted.
   */
  @Override
  public int getHandHighlighted(PlayerColor color) {
    if (color == null) {
      throw new IllegalArgumentException("No nulls!");
    }
    if (color == PlayerColor.RED) {
      return this.redHandHighlighted;
    }
    return this.blueHandHighlighted;
  }

  /**
   * Sets the current hand to be highlighted. Only effects view and control of model.
   *
   * @param handHighlighted index to highlight.
   * @param color to highlight for.
   * @return an int representing the new index.
   */
  @Override
  public int setHandHighlighted(int handHighlighted, PlayerColor color) {
    if (color == null) {
      throw new IllegalArgumentException("No nulls!");
    }
    if (color == PlayerColor.RED) {
      if (this.redHandHighlighted == handHighlighted) {
        return this.redHandHighlighted = -1;
      }
      return this.redHandHighlighted = handHighlighted;
    }
    if (this.blueHandHighlighted == handHighlighted) {
      return this.blueHandHighlighted = -1;
    }
    return this.blueHandHighlighted = handHighlighted;
  }

  /**
   * Gets the current cell that is highlighted.
   *
   * @param color of player to get the highlighted cell for.
   * @return a point representing the row, col of the cell that has been highlighted.
   */
  @Override
  public Point getCellHighlighted(PlayerColor color) {
    if (color == null) {
      throw new IllegalArgumentException("No nulls!");
    }
    if (color == PlayerColor.RED) {
      return this.redBoardHighlighted;
    }
    return this.blueBoardHighlighted;
  }

  /**
   * Sets a certain cell as highlighted.
   *
   * @param point to highlight based on row, and col.
   * @param color to highlight cell for.
   * @return a point representing the new cell that is highlighted, with row, col.
   */
  @Override
  public Point setCellHighlighted(Point point, PlayerColor color) {
    if (point == null || color == null) {
      throw new IllegalArgumentException("No null values!");
    }
    if (color == PlayerColor.RED) {
      if (point == this.redBoardHighlighted) {
        return this.redBoardHighlighted = new Point(-1, -1);
      }
      return this.redBoardHighlighted = point;
    }
    if (point == this.blueBoardHighlighted) {
      return this.blueBoardHighlighted = new Point(-1, -1);
    }
    return this.blueBoardHighlighted = point;
  }

  /**
   * Returns an immutable copy of the board.
   *
   * @return a copy of the board
   */
  @Override
  public ProviderIPawnsBoard copy() {
    return new PawnsBoardModelToIPawnsBoardProviderAdapter(delegate);
  }

  /**
   * Gets a copy of the desired player color.
   *
   * @param color to select from.
   * @return a copy of the player.
   */
  @Override
  public ProviderPlayer getPlayer(PlayerColor color) {
    // if (color == PlayerColor.BLUE) {
    // ProviderPlayer blue = new PlayerBlue();
    // return blue;
    // }
    // if (color == PlayerColor.RED) {
    // ProviderPlayer red = new PlayerRed();
    // return red;
    // }
    // else {
    // throw new IllegalArgumentException("Invalid color given");
    // }

    return null;
  }

  /**
   * Adds a model action listener to the notification list to be notified when turns change.
   *
   * @param listener to send notification to.
   */
  @Override
  public void addTurnListener(ModelActions listener) {
    this.toNotify.add(listener);
  }

  /**
   * Adds an action listener to the gnotification list to be notified when game is over.
   *
   * @param listener to send notification to.
   */
  @Override
  public void addGameOverListener(ModelActions listener) {
    addTurnListener(listener);
  }

  /** Sets the game to active, allowing players to make moves. */
  @Override
  public void startGame() { // i got it :-D
    // delegate.startGame(this.deckA, this.deckB, this.handSize, this.shuffle);
  }

  /**
   * Sets the deck contents for this game, associated with a player.
   *
   * @param deckContents to associate with a player.
   * @param color color to associate contents with.
   */
  @Override
  public void setDeckContents(String deckContents, PlayerColor color) {
    //
    // hrm............. this is probably fine not to mess with
    // we already set the hand contents in the constructor
  }
}
