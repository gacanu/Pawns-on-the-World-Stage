package cs3500.pawns.controller;

import cs3500.pawns.model.ReadonlyPawnsBoardModel;

/** helps choose an action. */
public interface Strategy {
  /**
   * choose move either places a card or passes a turn.
   *
   * @param model the given ReadonlyPawnsBoardModel.
   * @return either a passed turn or a card place.
   */
  Move chooseMove(ReadonlyPawnsBoardModel model);
}
