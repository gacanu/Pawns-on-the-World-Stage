package cs3500.pawns.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

import cs3500.pawns.model.Cell;
import cs3500.pawns.model.Player;
import cs3500.pawns.model.QueensBlood;
import cs3500.pawns.view.QueensBloodView;

import static cs3500.pawns.model.DeckReader.readFile;

/**
 * implementation of the driver of the Queens Blood game. Reads user input to determine what move
 * the player should make and prints the current game state after each command.
 */
public class QueensBloodTextualController implements QueensBloodController {
  private final Appendable ap;
  private Scanner scan;
  private boolean q;
  private boolean p;

  /**
   * constructor for QueensBloodTextualController.
   *
   * @param rd a readable to take in information
   * @param ap a appendable to put out information
   */
  public QueensBloodTextualController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }

    this.ap = ap;
    this.q = false;
    this.p = false;
    this.scan = new Scanner(rd);
  }

  /**
   * Starts and plays the game using user input from the player.
   *
   * @param model state of the game
   * @param view view for the model
   * @param shuffle true iff the deck should be shuffled.
   * @param handSize the starting hand size
   * @throws IllegalStateException if controller is unable to successfully receive input, transmit
   *     output, or if the game cannot be started
   * @throws IllegalArgumentException if the model or view are null
   */
  @Override
  public <C extends Cell> void playGame(
      QueensBlood model, QueensBloodView view, boolean shuffle, int handSize) {

    if (model == null || view == null) {
      throw new IllegalArgumentException("Model and view cannot be null!");
    }

    try {
      Path path = Paths.get("docs" + File.separator + "deck.config");
      model.startGame(
          readFile(Player.PLAYER1, path), readFile(Player.PLAYER2, path), handSize, shuffle);

      while (!model.isGameOver() && !q) {
        view.render(ap);
        ap.append("\n");
        ap.append(
            "Score: "
                + model.getTotalScore(Player.PLAYER1)
                + model.getTotalScore(Player.PLAYER2)
                + "\n");
        input(model);
      }

      if (model.isGameOver()) {
        view.render(ap);
        ap.append("\n");
        ap.append(
            "\nGame over. Score: "
                + model.getTotalScore(Player.PLAYER1)
                + model.getTotalScore(Player.PLAYER2)
                + "\n");
      }

      if (q) {
        ap.append("Game quit!\n");
        ap.append("State of game when quit:\n");
        view.render(ap);
        ap.append("\n");
        ap.append(
            "Score: "
                + model.getTotalScore(Player.PLAYER1)
                + model.getTotalScore(Player.PLAYER2)
                + "\n");
      }
    }

    // Break up
    catch (IOException e) {
      throw new IllegalStateException(
          "the controller is unable to successfully receive input, "
              + "transmit output, or the game cannot be started");
    }

    // TODO: We need to set up more controller methods if we want to use this for testing;
    // this isn't required for submission but will probably help
    // PrintGame()
    //  Remember to list each player's hand, maybe depending on the turn.
    // HandleInput() - Command pattern shouldn't be that bad; we don't need to debug this
    // super hard, just make it good enough to play a game
    //  Commands: Place x y z, Pass, Quit
  }

  private <C extends Cell> void input(QueensBlood model) throws IOException {
    try {
      if (scan.hasNext()) {
        String cmd = scan.next();
        switch (cmd) {
          case "place":
            model.placeCardInPosition(this.readInt() - 1, this.readInt() - 1, this.readInt() - 1);
            return;
          case "Q":
          case "q":
            q = true;
            return;
          case "pass":
            p = true;
            return;
          default:
            ap.append("\nInvalid move. Play again. \n");
        }
      }
    } catch (IllegalStateException | IllegalArgumentException e) {
      ap.append("\n Invalid Move. Play again. \n");
    } catch (QuitException e) {
      ap.append("\n");
    }
  }

  /**
   * reads the next value to grab the next int.
   *
   * @param <C> extents the cell class
   * @return the next integer value
   * @throws QuitException quit exception
   */
  private <C extends Cell> int readInt() throws QuitException {
    while (true) {
      try {
        int i = scan.nextInt();

        if (i > 0) {
          return i;
        }

      } catch (InputMismatchException e) {
        if (scan.next().equals("q") || scan.next().equals("Q")) {
          q = true;
          throw new QuitException();
        }
      }
    }
  }
}
