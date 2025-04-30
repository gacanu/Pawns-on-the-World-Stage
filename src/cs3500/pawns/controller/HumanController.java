package cs3500.pawns.controller;

import cs3500.pawns.model.PawnsBoardModel;
import cs3500.pawns.model.Turn;
import cs3500.pawns.view.QueensBloodView;
import cs3500.pawns.view.ViewFeatures;

/** Controller for the human players that handles user inputs and updates the game based on that. */
public class HumanController extends CPUController implements ViewFeatures, QueensBloodController {

  private final Player player;
  private boolean passing = false;
  private boolean active = false;
  private int x = -1;
  private int y = -1;
  private int index = -1;

  /**
   * Constructor for HumanController.
   *
   * @param model the main game model
   * @param player the human player for this controller
   * @param view shows the game and its updates as well
   */
  public HumanController(PawnsBoardModel model, Player player, QueensBloodView view) {
    super(model, player, view);
    this.player = player;
    view.addFeature(this);
  }

  /**
   * handles the board clicks.
   *
   * @param x the x the user clicked
   * @param y the y the user clicked
   */
  @Override
  public void handleBoardClick(int x, int y) {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("x and y must be > 0!");
    }
    this.x = x;
    this.y = y;
    // System.out.println(x + "," + y);
  }

  /**
   * handles the hand clicks.
   *
   * @param index the index the user clicked
   */
  @Override
  public void handleHandClick(int index) {
    this.index = index;
  }

  /** handles the confirmations of moves. */
  @Override
  public void handleConfirm() {
    if (!active) {
      view.showDialog("It's not this player's turn yet!");
      return;
    }
    if (this.x == -1 || this.y == -1) {
      view.showDialog("Select a coordinate on the board first!");
      return;
    }
    if (this.index == -1) {
      view.showDialog("Select a card in your hand first!");
      return;
    }
    if (!model.checkPosition(player.getTurn(), this.index, this.x, this.y)) {
      view.showDialog("Invalid placement!");
      return;
    }
    if (this.x != -1 && this.y != -1 && this.index != -1 && active) {
      if (passing) {
        super.pass();
        resetParams();
        return;
      }
      super.placeCard(this.x, this.y, this.index);
      view.update();
      resetParams();
    }
  }

  private void resetParams() {
    this.x = -1;
    this.y = -1;
    this.index = -1;
    this.passing = false;
  }

  /** handles the passing of turns. */
  @Override
  public void handlePass() {
    this.passing = !this.passing;
  }

  /**
   * send notif to all notifiable when turn switches.
   *
   * @param turn the turn in the game.
   */
  @Override
  public void sendNotif(Turn turn) {
    view.clearSelections();
    this.active = super.getTurn();
    view.update();
    if (this.active) {
      view.showDialog("It's " + super.model.getTurn().getColor() + "'s turn!");
    }
    if (super.model.isGameOver()) {
      Turn winner = super.model.whoWon();
      if (winner == null) {
        view.showDialog(
            "Game over! It's a tie at " + super.model.getTotalScore(Turn.PLAYER1) + " points!");
        return;
      }
      view.showDialog(
          "Game over! "
              + winner.getColor()
              + " won with "
              + super.model.getTotalScore(winner)
              + " points!");
      return;
    }
  }
}
