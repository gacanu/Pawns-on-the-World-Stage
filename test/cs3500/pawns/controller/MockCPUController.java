package cs3500.pawns.controller;

import cs3500.pawns.model.Turn;

/** A mock for the CPU controller. */
public class MockCPUController implements QueensBloodController {

  private final Turn turn;
  StringBuilder out;

  public MockCPUController(StringBuilder out, Turn t) {
    this.out = out;
    this.turn = t;
  }

  @Override
  public void placeCard(int x, int y, int cardIdx) {
    out.append("Placed card index " + cardIdx + "in coordinate (" + x + "," + y + ")\n");
  }

  @Override
  public void pass() {
    out.append("Passed.");
  }

  @Override
  public void give(Move m) {
    out.append("Player gave this controller the move " + m.toString());
  }

  @Override
  public void sendNotif(Turn turn) {
    out.append("Received notification - ");
    if (turn.equals(this.turn)) {
      out.append("it's this controller's turn, this would execute a move now.\n");
    } else {
      out.append("but it's not this controller's turn.\n");
    }
  }
}
