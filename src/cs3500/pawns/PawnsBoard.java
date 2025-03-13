package cs3500.pawns;

import java.io.InputStreamReader;
import java.util.Random;

import cs3500.pawns.controller.QueensBloodTextualController;
import cs3500.pawns.model.Card;
import cs3500.pawns.model.Game;
import cs3500.pawns.model.QueensBlood;
import cs3500.pawns.view.QueensBloodTextualView;
import cs3500.pawns.view.QueensBloodView;

/**
 * allows the QueensBlood game to be played.
 */
public class PawnsBoard {
  /**
   * public main method for the Poker Game.
   * @param args     the additional args to the main method
   */
  public static void main(String[] args) {
    Readable in = new InputStreamReader(System.in);
    Appendable out = System.out;
    QueensBlood model = new QueensBlood(3, 5, new Random());
    QueensBloodTextualView view = new QueensBloodView(model);
    QueensBloodTextualController controller = new QueensBloodTextualController(in, out);
    controller.playGame(model, view, true, 5);
    // must edit playGame inputs to match needed for QueensBlood.
  }
}
