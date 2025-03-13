package cs3500.pawns.view;

import java.io.IOException;

/**
 * Interface for a view for the Queens Blood game.
 */
public interface QueensBloodTextualView {

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.)
   * to the given appendable.
   *
   * @param out where to send the model information to
   * @throws IOException if the rendering fails for some reason
   */
  void render(Appendable out) throws IOException;
}
