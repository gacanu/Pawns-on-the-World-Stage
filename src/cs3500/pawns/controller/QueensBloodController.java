package cs3500.pawns.controller;

/**
 * Driver of the Queens Blood game. Reads user input to determine what move the player should make
 * and prints the current game state after each command.
 */
public interface QueensBloodController extends Notifiable {
  /**
   * places the given card index at the given x-y coords.
   *
   * @param x x coord
   * @param y y coord
   * @param cardIdx card index
   */
  void placeCard(int x, int y, int cardIdx);

  /** passes the players turn. */
  void pass();

  /**
   * gives the controller a specific move.
   *
   * @param m move given
   */
  void give(Move m);
}
