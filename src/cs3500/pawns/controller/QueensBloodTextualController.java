package cs3500.pawns.controller;

import java.util.Scanner;

import cs3500.pawns.model.Cell;
import cs3500.pawns.model.Game;
import cs3500.pawns.model.QueensBlood;
import cs3500.pawns.view.QueensBloodTextualView;

/**
 * implementation of the driver of the Queens Blood game. Reads user input to determine what move
 * the player should make and prints the current game state after each command.
 */
public class QueensBloodTextualController implements QueensBloodController {
  private final Appendable ap;
  private Scanner scan;
  private boolean q;


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
    this.scan = new Scanner(rd);
  }

  /**
   * Starts and plays the game using user input from the player.
   *
   * @param model    state of the game
   * @param view     view for the model
   * @param shuffle  true iff the deck should be shuffled.
   * @param handSize the starting hand size
   * @throws IllegalStateException    if controller is unable to successfully receive input,
   *                                  transmit output, or if the game cannot be started
   * @throws IllegalArgumentException if the model or view are null
   */
  @Override
  public <C extends Cell> void playGame(QueensBlood model, QueensBloodTextualView view, boolean shuffle,
                                        int handSize) {


  }


  // Noelle you're actually so huge for this i hate controllers
  // have this as recompense
  //
  //      //////////////////////////////////////    ________________________
  //    /////       ///////////      ///////////   /                        \
  //                ///////////      ///////////  |  never give up big dog   |
  //                 ////////         /////////    \ _______________________/
  //
  //                   /////////////////////       ////
  //                     ////////////////         /////
  //                         /////////           //// ///////
  //                                             //// \\\\\\\
  //                                             //// ///////
  //                                             //// \\\\\\\ thumbs up B)
  //                                             //// ///////



}
