package cs3500.pawns.controller;

import cs3500.pawns.view.QueensBloodView;
import cs3500.pawns.view.ViewFeatures;

/** A mock for the view. */
public class ViewMock implements QueensBloodView {

  private final StringBuilder out;

  /**
   * Constructor for ViewMock.
   *
   * @param out output of type stringbuilder
   */
  public ViewMock(StringBuilder out) {
    this.out = out;
  }

  /**
   * Adds the given ViewFeatures to the current view. This allows the view to communicate with the
   * controller, which implements ViewFeatures.
   *
   * @param f the ViewFeatures object to add
   */
  @Override
  public void addFeature(ViewFeatures f) {
    // not needed
  }

  /** Displays the current view. */
  @Override
  public void display() {
    // not needed
  }

  @Override
  public void update() {
    // not needed
  }

  @Override
  public void showDialog(String s) {
    out.append(s);
  }

  @Override
  public void clearSelections() {
    // not needed
  }
}
