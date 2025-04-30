package cs3500.pawns.controller;

import cs3500.pawns.model.Turn;

/** Interface for an object that needs to be notified of a turn change. */
public interface Notifiable {

  /**
   * send notif to all notifiable when turn switches.
   *
   * @param turn the turn in the game.
   */
  void sendNotif(Turn turn);
}
