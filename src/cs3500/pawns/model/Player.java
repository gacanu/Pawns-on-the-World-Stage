package cs3500.pawns.model;

/** this enum represents player1 and player2 and their corresponding color. */
public enum Player {
  PLAYER1("Red"),
  PLAYER2("Blue");
  private final String color;

  /**
   * constructor to represents the Player and the Color.
   *
   * @param color the color of the player.
   */
  Player(String color) {
    this.color = color;
  }

  /**
   * finds the color of the Player.
   *
   * @return the color of the player.
   */
  public String getColor() {
    return color;
  }
}
