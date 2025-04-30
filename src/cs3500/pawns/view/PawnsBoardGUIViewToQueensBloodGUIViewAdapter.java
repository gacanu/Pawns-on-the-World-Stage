package cs3500.pawns.view;

import cs3500.pawns.provider.model.ProviderIPawnsBoard;
import cs3500.pawns.provider.model.PlayerColor;
import cs3500.pawns.provider.view.BasicPawnsBoardFrame;

/** an adapter for Pawns Board GUI View To Queens Blood GUI View. */
public class PawnsBoardGUIViewToQueensBloodGUIViewAdapter extends BasicPawnsBoardFrame
    implements QueensBloodView {

  /**
   * Creates a pawnsboard frame that holds all game visualization information.
   *
   * @param model to read from.
   * @param color of the player this frame represents.
   */
  public PawnsBoardGUIViewToQueensBloodGUIViewAdapter(
      ProviderIPawnsBoard model, PlayerColor color) {
    super(model, color);
  }

  /**
   * Adds the given ViewFeatures to the current view. This allows the view to communicate with the
   * controller, which implements ViewFeatures.
   *
   * @param f the ViewFeatures object to add
   */
  @Override
  public void addFeature(ViewFeatures f) {
    // The things the features interface would do is handled by the superclass.
  }

  /** Displays the current view. */
  @Override
  public void display() {
    super.makeVisible();
  }

  /** updates the panel. */
  @Override
  public void update() {
    super.refresh();
  }

  /**
   * shows the dialog.
   *
   * @param s the string to show.
   */
  @Override
  public void showDialog(String s) {
    super.displayMsg(s);
  }

  /** clears the selections. */
  @Override
  public void clearSelections() {
    // Do nothing. This is taken care of by the model in this implementation.
  }
}
