package cs3500.pawns.provider.strategy;

import cs3500.pawns.provider.controller.ProviderMove;
import cs3500.pawns.provider.model.ProviderPlayer;
import cs3500.pawns.provider.model.ProviderReadonlyPawnsBoard;

/** Interface for all the strategies available for Pawns Board game. */
public interface PawnsBoardStrategy {
  /**
   * Returns a move or the player passes the turn.
   *
   * @param model the game model
   * @param providerPlayer the player that will use the strategies
   * @return the move made
   */
  ProviderMove chooseMove(ProviderReadonlyPawnsBoard model, ProviderPlayer providerPlayer);
}
