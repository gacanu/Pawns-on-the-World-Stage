package cs3500.pawns.controller;

import cs3500.pawns.model.Card;
import cs3500.pawns.model.Turn;
import cs3500.pawns.model.ReadonlyPawnsBoardModel;

import java.util.List;

/** fill first implements strategy to help choose an action. */
public class FillFirst implements Strategy {

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
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        for (int index = 0; index < hand.size(); index++) {
          if (model.checkPosition(turn, index, i, j)) {
            // System.out.println(i + ", " + j);
            return new PlaceCard(i, j, index);
          }
        }
      }
    }
    return new Pass();
  }
}
