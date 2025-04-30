package cs3500.pawns.view;

import cs3500.pawns.model.PawnsBoardModel;
import cs3500.pawns.model.Turn;

import java.io.IOException;

/**
 * Class for a view for the Queens Blood game. The Class describes exactly how the board is printed.
 */
public class QueensBloodTextualView implements QueensBloodView {
  private final Appendable appendable;
  private final PawnsBoardModel model;

  /**
   * QueensBloodView constructor.
   *
   * @param model the model of the Game
   */
  public QueensBloodTextualView(PawnsBoardModel model, Appendable appendable) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    this.model = model;
    this.appendable = appendable;
  }

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.) to the given appendable.
   *
   * @param out where to send the model information to
   * @throws IOException if the rendering fails for some reason
   */
  // Legacy method, called from display.
  public void render(Appendable out) throws IOException {
    if (out == null) {
      throw new IllegalArgumentException("Out cannot be null!");
    }
    out.append(this.toString());
  }

  /**
   * Returns a string representing the game state of the model.
   *
   * @return a string representing the model.
   */
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    int width = model.getWidth();
    int height = model.getHeight();
    int[] score1 = model.getScores(Turn.PLAYER1);
    int[] score2 = model.getScores(Turn.PLAYER2);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (j == 0) {
          s.append(score1[i] + " ");
        }
        s.append(model.getCell(j, i).vString());
        if (j == width - 1) {
          s.append(" " + score2[i] + "\n");
        }
      }
    }
    return s.toString();
  }

  /**
   * Adds the given ViewFeatures to the current view. This allows the view to communicate with the
   * controller, which implements ViewFeatures.
   *
   * @param f the ViewFeatures object to add
   */
  @Override
  public void addFeature(ViewFeatures f) {
    // This view doesn't need to communicate with controllers.
  }

  /** Displays the current view. */
  @Override
  public void display() {
    try {
      render(this.appendable);
    } catch (IOException e) {
      throw new IllegalStateException("Bad I/O!");
    }
  }

  /** updates the panel. */
  @Override
  public void update() {
    // Not needed
  }

  /**
   * shows the dialog.
   *
   * @param s the string to show.
   */
  @Override
  public void showDialog(String s) {
    try {
      appendable.append(s);
    } catch (IOException e) {
      throw new IllegalArgumentException("");
    }
  }

  /** clears the selections. */
  @Override
  public void clearSelections() {
    // This is a GUI-only method.
  }
}
