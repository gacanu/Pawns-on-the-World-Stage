package cs3500.pawns.controller;

import cs3500.pawns.model.Card;
import cs3500.pawns.model.Turn;
import cs3500.pawns.model.ReadonlyPawnsBoardModel;

import java.util.List;

/** max row score implements strategy to help choose an action. */
public class MaxRowScore implements Strategy {

  /**
   * choose move either places a card or passes a turn.
   *
   * @param model the given ReadonlyPawnsBoardModel
   * @return either a passed turn or a card place
   */
  @Override
  public Move chooseMove(ReadonlyPawnsBoardModel model) {
    Turn turn = model.getTurn();
    List<Card> hand = model.getHand(turn);
    int width = model.getWidth();
    int height = model.getHeight();
    Turn enemy = turn.getOther();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        for (int index = 0; index < hand.size(); index++) {
          // Checks if the position is valid for that card,
          // that the current player is behind in that row,
          // and that it by placing it there, the player's score will increase above the enemy's
          // score.
          if (model.checkPosition(turn, index, i, j)
              && model.getRowScore(j, turn) <= model.getRowScore(j, enemy)
              && model.getRowScore(j, turn) + hand.get(index).getValue()
                  > model.getRowScore(j, enemy)) {
            return new PlaceCard(i, j, index);
          }
        }
      }
    }
    return new Pass();
  }
}
