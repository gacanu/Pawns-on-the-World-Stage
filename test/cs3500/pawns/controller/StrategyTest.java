package cs3500.pawns.controller;

import cs3500.pawns.model.PawnsBoardModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** tests for the strategy interface. */
public class StrategyTest {
  Appendable log = new StringBuilder();
  PawnsBoardModel model = new StrategyMock(log);
  PawnsBoardModel validPosnModel = new ValidPosnMock(log);
  PawnsBoardModel allValidPosnModel = new AllValidPosnMock(log);
  PawnsBoardModel loserModel = new LoserMock(log);
  Strategy fillFirst = new FillFirst();
  Strategy maxRowScore = new MaxRowScore();
  Move pass = new Pass();

  @Before
  public void setUp() {
    log = new StringBuilder();
    model = new StrategyMock(log);
    validPosnModel = new ValidPosnMock(log);
    allValidPosnModel = new AllValidPosnMock(log);
    loserModel = new LoserMock(log);
    fillFirst = new FillFirst();
    pass = new Pass();
  }

  /** tests fill first, fill first could not find an available space, and passed. */
  @Test // Hrm.
  public void testFillFirst() {
    assertEquals(pass, fillFirst.chooseMove(model));
    assertEquals(
        "Checked what turn it was.\n"
            + "Got hand of player PLAYER1\n"
            + "Checked position (0, 0) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (0, 0) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (0, 1) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (0, 1) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (0, 2) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (0, 2) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (1, 0) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (1, 0) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (1, 1) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (1, 1) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (1, 2) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (1, 2) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (2, 0) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (2, 0) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (2, 1) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (2, 1) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (2, 2) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (2, 2) was valid trying to place card index 1 - It was invalid.\n",
        log.toString().replace(System.lineSeparator(), "\n"));
  }

  /** another test for fill first, fill first found an available space and placed there. */
  @Test
  public void testFillFirst2() {
    assertEquals(new PlaceCard(1, 1, 0), fillFirst.chooseMove(validPosnModel));
    assertEquals(
        "Checked what turn it was.\n"
            + "Got hand of player PLAYER1\n"
            + "Checked position (0, 0) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (0, 0) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (0, 1) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (0, 1) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (0, 2) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (0, 2) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (1, 0) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (1, 0) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (1, 1) was valid trying to place card index 0 - It was valid.\n",
        log.toString().replace(System.lineSeparator(), "\n"));
  }

  /**
   * another test for fill first, unlike maxRowScore, fillFirst will place in the first possible
   * valid position,demonstrated by the board with only valid positions.
   */
  @Test
  public void testFillFirst3() {
    assertEquals(new PlaceCard(0, 0, 0), fillFirst.chooseMove(allValidPosnModel));
    assertEquals(
        "Checked what turn it was.\n"
            + "Got hand of player PLAYER1\n"
            + "Checked position (0, 0) was valid trying to place card index 0 - It was valid.\n",
        log.toString().replace(System.lineSeparator(), "\n"));
  }

  /**
   * test for max row score Places a card in a position after realizing that the card can be placed
   * and will improve that row-score above the enemy's row score.
   */
  @Test
  public void testMaxRowScore() {
    assertEquals(new PlaceCard(0, 1, 1), maxRowScore.chooseMove(allValidPosnModel));
    assertEquals(
        "Checked what turn it was.\n"
            + "Got hand of player PLAYER1\n"
            + "Checked position (0, 0) was valid trying to place card index 0 - It was valid.\n"
            + "Grabbed row-score (5) PLAYER1 in row 0\n"
            + "Grabbed row-score (1) PLAYER2 in row 0\n"
            + "Checked position (0, 0) was valid trying to place card index 1 - It was valid.\n"
            + "Grabbed row-score (5) PLAYER1 in row 0\n"
            + "Grabbed row-score (1) PLAYER2 in row 0\n"
            + "Checked position (0, 1) was valid trying to place card index 0 - It was valid.\n"
            + "Grabbed row-score (1) PLAYER1 in row 1\n"
            + "Grabbed row-score (2) PLAYER2 in row 1\n"
            + "Grabbed row-score (1) PLAYER1 in row 1\n"
            + "Grabbed row-score (2) PLAYER2 in row 1\n"
            + "Checked position (0, 1) was valid trying to place card index 1 - It was valid.\n"
            + "Grabbed row-score (1) PLAYER1 in row 1\n"
            + "Grabbed row-score (2) PLAYER2 in row 1\n"
            + "Grabbed row-score (1) PLAYER1 in row 1\n"
            + "Grabbed row-score (2) PLAYER2 in row 1\n",
        log.toString().replace(System.lineSeparator(), "\n"));
  }

  /**
   * another test for max row score, where row scores are all higher for this player, so this
   * passes, unable to improve that score.
   */
  @Test
  public void testMaxRowScore2() {
    // Row scores are all higher for this player, so this passes, unable to improve that score.
    assertEquals(new Pass(), maxRowScore.chooseMove(validPosnModel));
    assertEquals(
        "Checked what turn it was.\n"
            + "Got hand of player PLAYER1\n"
            + "Checked position (0, 0) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (0, 0) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (0, 1) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (0, 1) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (0, 2) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (0, 2) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (1, 0) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (1, 0) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (1, 1) was valid trying to place card index 0 - It was valid.\n"
            + "Grabbed row-score (5) PLAYER1 in row 1\n"
            + "Grabbed row-score (2) PLAYER2 in row 1\n"
            + "Checked position (1, 1) was valid trying to place card index 1 - It was valid.\n"
            + "Grabbed row-score (5) PLAYER1 in row 1\n"
            + "Grabbed row-score (2) PLAYER2 in row 1\n"
            + "Checked position (1, 2) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (1, 2) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (2, 0) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (2, 0) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (2, 1) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (2, 1) was valid trying to place card index 1 - It was invalid.\n"
            + "Checked position (2, 2) was valid trying to place card index 0 - It was invalid.\n"
            + "Checked position (2, 2) was valid trying to place card index 1 - It was invalid.\n",
        log.toString().replace(System.lineSeparator(), "\n"));
  }

  /**
   * another test for max row score, where row scores are all higher for this player, so this
   * passes, unable to improve that score.
   */
  @Test
  public void testMaxRowScore3() {
    // Row scores are all higher for this player, so this passes, unable to improve that score.
    assertEquals(new Pass(), maxRowScore.chooseMove(loserModel));
    assertEquals(
        "Checked what turn it was.\n"
            + "Got hand of player PLAYER1\n"
            + "Checked position (0, 0) was valid trying to place card index 0 - It was valid.\n"
            + "Grabbed row-score (1) PLAYER1 in row 0\n"
            + "Grabbed row-score (99) PLAYER2 in row 0\n"
            + "Grabbed row-score (1) PLAYER1 in row 0\n"
            + "Grabbed row-score (99) PLAYER2 in row 0\n"
            + "Checked position (0, 0) was valid trying to place card index 1 - It was valid.\n"
            + "Grabbed row-score (1) PLAYER1 in row 0\n"
            + "Grabbed row-score (99) PLAYER2 in row 0\n"
            + "Grabbed row-score (1) PLAYER1 in row 0\n"
            + "Grabbed row-score (99) PLAYER2 in row 0\n"
            + "Checked position (0, 1) was valid trying to place card index 0 - It was valid.\n"
            + "Grabbed row-score (1) PLAYER1 in row 1\n"
            + "Grabbed row-score (99) PLAYER2 in row 1\n"
            + "Grabbed row-score (1) PLAYER1 in row 1\n"
            + "Grabbed row-score (99) PLAYER2 in row 1\n"
            + "Checked position (0, 1) was valid trying to place card index 1 - It was valid.\n"
            + "Grabbed row-score (1) PLAYER1 in row 1\n"
            + "Grabbed row-score (99) PLAYER2 in row 1\n"
            + "Grabbed row-score (1) PLAYER1 in row 1\n"
            + "Grabbed row-score (99) PLAYER2 in row 1\n"
            + "Checked position (1, 0) was valid trying to place card index 0 - It was valid.\n"
            + "Grabbed row-score (1) PLAYER1 in row 0\n"
            + "Grabbed row-score (99) PLAYER2 in row 0\n"
            + "Grabbed row-score (1) PLAYER1 in row 0\n"
            + "Grabbed row-score (99) PLAYER2 in row 0\n"
            + "Checked position (1, 0) was valid trying to place card index 1 - It was valid.\n"
            + "Grabbed row-score (1) PLAYER1 in row 0\n"
            + "Grabbed row-score (99) PLAYER2 in row 0\n"
            + "Grabbed row-score (1) PLAYER1 in row 0\n"
            + "Grabbed row-score (99) PLAYER2 in row 0\n"
            + "Checked position (1, 1) was valid trying to place card index 0 - It was valid.\n"
            + "Grabbed row-score (1) PLAYER1 in row 1\n"
            + "Grabbed row-score (99) PLAYER2 in row 1\n"
            + "Grabbed row-score (1) PLAYER1 in row 1\n"
            + "Grabbed row-score (99) PLAYER2 in row 1\n"
            + "Checked position (1, 1) was valid trying to place card index 1 - It was valid.\n"
            + "Grabbed row-score (1) PLAYER1 in row 1\n"
            + "Grabbed row-score (99) PLAYER2 in row 1\n"
            + "Grabbed row-score (1) PLAYER1 in row 1\n"
            + "Grabbed row-score (99) PLAYER2 in row 1\n",
        log.toString().replace(System.lineSeparator(), "\n"));
  }
}
