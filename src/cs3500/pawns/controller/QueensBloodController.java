package cs3500.pawns.controller;

import cs3500.pawns.model.Cell;
import cs3500.pawns.model.QueensBlood;
import cs3500.pawns.view.QueensBloodView;

/**
 * Driver of the Queens Blood game. Reads user input to determine what move the player should make
 * and prints the current game state after each command.
 */
public interface QueensBloodController {
  /**
   * Starts and plays the game using user input from the player.
   *
   * @param model state of the game
   * @param view view for the model
   * @param shuffle true iff the deck should be shuffled.
   * @param handSize the starting hand size
   * @param <C> the type of the Card used in the model
   * @throws IllegalStateException if controller is unable to successfully receive input, transmit
   *     output, or if the game cannot be started
   * @throws IllegalArgumentException if the model or view are null
   */
  <C extends Cell> void playGame(
      QueensBlood model, QueensBloodView view, boolean shuffle, int handSize);
}
