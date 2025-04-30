package cs3500.pawns.view;

/** Interface for a view for the Queens Blood game. */
public interface QueensBloodView {

  /**
   * Adds the given ViewFeatures to the current view. This allows the view to communicate with the
   * controller, which implements ViewFeatures.
   *
   * @param f the ViewFeatures object to add
   */
  void addFeature(ViewFeatures f);

  /** Displays the current view. */
  void display();

  /** updates the panel. */
  void update();

  /**
   * shows the dialog.
   *
   * @param s the string to show.
   */
  void showDialog(String s);

  /** clears the selections. */
  void clearSelections();
}
