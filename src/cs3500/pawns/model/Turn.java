package cs3500.pawns.model;

/** This enum describes one of two 'players'. Players can be used to represent whose turn it is. */
public enum Turn {
  PLAYER1("Red"),
  PLAYER2("Blue");
  private final String color;

  /**
   * constructor to represents the Turn and the Color.
   *
   * @param color the color of the player.
   */
  Turn(String color) {
    this.color = color;
  }

  /**
   * finds the color of the Turn.
   *
   * @return the color of the player.
   */
  public String getColor() {
    return color;
  }

  /**
   * gets a different player.
   *
   * @return the opposite player of the one it was called on
   */
  public Turn getOther() {
    if (this == PLAYER1) {
      return PLAYER2;
    } else {
      return PLAYER1;
    }
  }
}
