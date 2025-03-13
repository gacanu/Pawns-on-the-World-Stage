package cs3500.pawns.view;

import cs3500.pawns.model.Game;
import cs3500.pawns.model.QueensBlood;

import java.io.IOException;

/**
 * Class for a view for the Queens Blood game. The Class describes
 * exactly how the board is printed.
 */
public class QueensBloodView implements QueensBloodTextualView {
  private final QueensBlood model;


  /**
   * QueensBloodView constructor.
   *
   * @param model the model of the Game
   */
  public QueensBloodView(QueensBlood model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }

    this.model = model;
  }


  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.)
   * to the given appendable.
   *
   * @param out where to send the model information to
   * @throws IOException if the rendering fails for some reason
   */
  @Override
  public void render(Appendable out) throws IOException {
    out.append(this.toString());
  }
}
