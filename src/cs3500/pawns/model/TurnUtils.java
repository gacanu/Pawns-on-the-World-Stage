package cs3500.pawns.model;

import cs3500.pawns.provider.model.PlayerColor;

/** turn utils class. */
public class TurnUtils {
  /**
   * gets the players whos turn it is.
   *
   * @param color the color of the player
   * @return the players whos turn it is
   */
  public static Turn getTurn(PlayerColor color) {
    if (color == null) {
      throw new IllegalArgumentException("No nulls!");
    }
    if (color == PlayerColor.RED) {
      return Turn.PLAYER1;
    }
    return Turn.PLAYER2;
  }

  /**
   * gets the players color from the given turn.
   *
   * @param turn the turn of the player
   * @return the players whos color it is
   */
  public static PlayerColor getColor(Turn turn) {
    if (turn == null) {
      throw new IllegalArgumentException("No nulls!");
    }
    if (turn == Turn.PLAYER1) {
      return PlayerColor.RED;
    }
    return PlayerColor.BLUE;
  }
}
