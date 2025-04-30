package cs3500.pawns;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import cs3500.pawns.controller.DeckReader;
import cs3500.pawns.model.Turn;
import cs3500.pawns.model.QueensBlood;
import cs3500.pawns.view.QueensBloodTextualView;

/** allows the QueensBlood game to be played. */
public class PawnsBoard {
  /**
   * public main method for the QueensBlood Game.
   *
   * @param args the additional args to the main method
   */
  public static void main(String[] args) {
    Readable in = new InputStreamReader(System.in);
    Appendable out = System.out;
    QueensBlood model = new QueensBlood(5, 3, new Random());
    Path path = Paths.get("docs" + File.separator + "deck.config");
    model.startGame(
        DeckReader.readFile(Turn.PLAYER1, path), DeckReader.readFile(Turn.PLAYER2, path), 5, false);
    QueensBloodTextualView view = new QueensBloodTextualView(model, out);
    // QueensBloodTextualController controller = new QueensBloodTextualController(in, out);
    // controller.playGame(model, view, true, 5);
    playDemo(model, view);
  }

  /**
   * plays the demo game.
   *
   * @param model the given QueensBlood model
   * @param view the given QueensBloodTextualView
   */
  private static void playDemo(QueensBlood model, QueensBloodTextualView view) {
    model.placeCardInPosition(0, 0, 0); // And so begins the slog //red
    System.out.println(view);
    model.placeCardInPosition(0, 4, 0);
    System.out.println(view);
    model.placeCardInPosition(0, 0, 1); // red
    System.out.println(view);
    model.placeCardInPosition(0, 4, 1);
    System.out.println(view);
    model.placeCardInPosition(0, 0, 2); // red
    System.out.println(view);
    model.placeCardInPosition(2, 3, 1);
    System.out.println(view);
    model.placeCardInPosition(2, 2, 2); // red
    System.out.println(view);
    model.placeCardInPosition(0, 3, 0);
    System.out.println(view);
    model.placeCardInPosition(0, 4, 2); // red
    System.out.println(view);
    model.placeCardInPosition(0, 1, 1);
    System.out.println(view);
    model.placeCardInPosition(0, 2, 0);
    System.out.println(view);
    model.placeCardInPosition(0, 3, 2);
    System.out.println(view);
    model.placeCardInPosition(0, 1, 0);
    System.out.println(view);
    model.placeCardInPosition(0, 1, 2);
    System.out.println(view);
    model.placeCardInPosition(0, 2, 1);
    System.out.println(view);
    model.passTurn();
    System.out.println(view);
    model.passTurn();
    System.out.println(view); // Game over!
  }
}
