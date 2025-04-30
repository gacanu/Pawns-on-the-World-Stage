package cs3500.pawns.controller;

import cs3500.pawns.model.PawnsBoardModel;
import cs3500.pawns.model.PawnsBoardModelToIPawnsBoardProviderAdapter;
import cs3500.pawns.model.TurnUtils;
import cs3500.pawns.provider.model.ProviderIPawnsBoard;
import cs3500.pawns.provider.model.PlayerColor;
import cs3500.pawns.provider.controller.PlayerActionListener;
import cs3500.pawns.provider.controller.ModelActions;
import cs3500.pawns.provider.view.PawnsBoardGuiView;
import cs3500.pawns.view.PawnsBoardGUIViewToQueensBloodGUIViewAdapter;
import cs3500.pawns.model.Turn;
import java.awt.Point;

/** controller adapter to allow the provider code and our code to work together. */
public class ControllerAdapter extends HumanController
    implements PlayerActionListener, ModelActions, QueensBloodController {
  private final ProviderIPawnsBoard model;
  private final PawnsBoardGuiView view;
  private final Turn turn;

  /**
   * controller adapter constructor.
   *
   * @param model the given model
   * @param view the given view
   * @param player the given player
   */
  public ControllerAdapter(PawnsBoardModel model, PawnsBoardGuiView view, Player player) {
    super(
        model,
        player,
        new PawnsBoardGUIViewToQueensBloodGUIViewAdapter(
            new PawnsBoardModelToIPawnsBoardProviderAdapter(model),
            TurnUtils.getColor(player.getTurn())));
    this.turn = player.getTurn();
    this.model = new PawnsBoardModelToIPawnsBoardProviderAdapter(model);
    this.view = view;
    this.view.addClickListener(this);
  }

  /** handles the confirmations of moves. */
  @Override
  public void handleConfirm() {
    // Not needed for this controller adapter.
  }

  /**
   * Handles the user clicking on a specific cell in the board. Row and Col are model cords. Zero
   * index based.
   *
   * @param row the row of the clicked cell.
   * @param col the column of the clicked cell.
   */
  @Override
  public void handleCellClick(int row, int col, PlayerColor color) {
    super.handleBoardClick(row, col);
    model.setCellHighlighted(new Point(row, col), color);
    view.refresh();
  }

  /**
   * Handels a user clicking on a card in the players hand. Zero index based.
   *
   * @param col to click on.
   * @param color of view that was clicked on.
   */
  @Override
  public void handleHandClick(int col, PlayerColor color) {
    super.handleHandClick(col);
    model.setHandHighlighted(col, color);
    view.refresh();
    // color should just be taken care of
  }

  /**
   * Handles confirming a move from a player. Press "M" to confirm move.
   *
   * @param color of player to confirm move for.
   */
  @Override
  public void confirmMove(PlayerColor color) {
    super.handleConfirm();
  }

  /**
   * Handles passing a move from a player. Press "P" to pass.
   *
   * @param color of player to confirm pass.
   */
  @Override
  public void confirmPass(PlayerColor color) {
    if (TurnUtils.getTurn(model.getTurn()).equals(this.turn)) {
      super.handlePass();
    }
  }

  /**
   * Posts a message to this listener.
   *
   * @param color of player to associate message to.
   * @param message to display.
   */
  @Override
  public void displayMessage(PlayerColor color, String message) {
    // This isn't called by anything in the provider view, model, or otherwise.
    // Frankly, I'm not sure why this is here.
  }

  /** Handles changing the players turn. */
  @Override
  public void handleNextTurn() {
    // Not needed for this controller.
    // Actions performed by our model override the actions performed by theirs, so we don't need
    // this one.
  }

  /** Handles game over notifications. */
  @Override
  public void handleGameOver() {
    // Handled by our controller signals.
  }
}
