package cs3500.pawns;

import java.io.InputStreamReader;
import java.util.Random;

import cs3500.pawns.controller.QueensBloodTextualController;
import cs3500.pawns.model.DeckReader;
import cs3500.pawns.model.Player;
import cs3500.pawns.model.QueensBlood;
import cs3500.pawns.view.QueensBloodView;
import cs3500.pawns.view.QueensBloodTextualView;

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
    QueensBlood model = new QueensBlood(5, 3, new Random());
    model.startGame(DeckReader.readFile(Player.PLAYER1), DeckReader.readFile(Player.PLAYER2), 2, false);
    QueensBloodView view = new QueensBloodTextualView(model);
    QueensBloodTextualController controller = new QueensBloodTextualController(in, out);
    controller.playGame(model, view, true, 5);
    // must edit playGame inputs to match needed for QueensBlood.
    System.out.println(view.toString());
  }
}
