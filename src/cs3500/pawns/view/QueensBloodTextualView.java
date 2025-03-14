package cs3500.pawns.view;

import cs3500.pawns.model.Cell;
import cs3500.pawns.model.Player;
import cs3500.pawns.model.QueensBlood;

import java.io.IOException;

/**
 * Class for a view for the Queens Blood game. The Class describes
 * exactly how the board is printed.
 */
public class QueensBloodTextualView implements QueensBloodView {
  private final QueensBlood model;


  /**
   * QueensBloodView constructor.
   *
   * @param model the model of the Game
   */
  public QueensBloodTextualView(QueensBlood model) {
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

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    int width = model.getWidth();
    int height = model.getHeight();
    int[] score1 = model.getScores(Player.PLAYER1);
    int[] score2 = model.getScores(Player.PLAYER2);
    for(int i = 0; i < height; i++) {
      for(int j = 0; j < width; j++) {
        if(j == 0) {
          s.append(score1[i] + " ");
        }
        s.append(model.getCell(j, i).vString());
        if(j == width - 1) {
          s.append(" " + score2[i] + "\n");
        }
      }
    }
    return s.toString();
  }
}
