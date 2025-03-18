package cs3500.pawns.model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/** Tests methods in the class QueensBlood. */
public class QueensBloodTest {
  Random r = new Random(4);
  Boolean[][] gridBoard;
  List<Card> deck1;
  List<Card> deck2;
  QueensBlood qb1;
  QueensBlood qbWide;
  QueensBlood qbSmall;
  QueensBlood qb2;
  QueensBlood qb3;
  Path path;
  Boolean[][] arr = {
    {false, false, false, false, false},
    {false, false, false, false, false},
    {false, true, false, true, false},
    {false, false, false, false, false},
    {false, false, false, false, false}
  };
  Card bee = new Card("Bee", 1, 1, arr, Player.PLAYER1);

  private Boolean[][] generateGrid(Random r) {
    Boolean[][] soln = new Boolean[5][5];
    for (Boolean[] a : soln) {
      for (int i = 0; i < 5; i++) {
        a[i] = r.nextBoolean();
      }
    }

    return soln;
  }

  /** Sets up test methods prior to testing. */
  @Before
  public void setUp() {
    qb1 = new QueensBlood(3, 5, this.r);
    qbWide = new QueensBlood(5, 3, this.r);
    qbSmall = new QueensBlood(5, 3, this.r);
    qb2 = new QueensBlood(9, 15, this.r);
    qb3 = new QueensBlood(15, 25, this.r);
    gridBoard = generateGrid(r);
    path = Paths.get("docs" + File.separator + "deck.config");
    deck1 = DeckReader.readFile(Player.PLAYER1, path);
    deck2 = DeckReader.readFile(Player.PLAYER2, path);
    qbWide.startGame(new ArrayList<>(deck1), new ArrayList<>(deck2), 5, false);
    qb1.startGame(new ArrayList<>(deck1), new ArrayList<>(deck2), 2, false);
    deck1 = DeckReader.readFile(Player.PLAYER1, path);
    deck2 = DeckReader.readFile(Player.PLAYER2, path);
  }

  /** Tests valid construction of QueensBlood objects. */
  @Test
  public void testConstruction() {
    // if either width or/and height are below zero throws
    assertThrows(IllegalArgumentException.class, () -> new QueensBlood(0, 5, r));
    assertThrows(IllegalArgumentException.class, () -> new QueensBlood(3, 0, r));
    assertThrows(IllegalArgumentException.class, () -> new QueensBlood(0, 0, r));
    // if random is null throws
    assertThrows(IllegalArgumentException.class, () -> new QueensBlood(3, 5, null));
    // if width or/and height are even throws
    assertThrows(IllegalArgumentException.class, () -> new QueensBlood(4, 7, r));
    assertThrows(IllegalArgumentException.class, () -> new QueensBlood(3, 6, r));
    assertThrows(IllegalArgumentException.class, () -> new QueensBlood(4, 8, r));
  }

  /** Tests the method getWidth(). */
  @Test
  public void getWidth() {
    assertEquals(3, qb1.getWidth());
    assertEquals(9, qb2.getWidth());
    assertEquals(15, qb3.getWidth());
  }

  /** Tests the method getHeight(). */
  @Test
  public void getHeight() {
    assertEquals(5, qb1.getHeight());
    assertEquals(15, qb2.getHeight());
    assertEquals(25, qb3.getHeight());
  }

  /** Tests the method passTurn(). */
  @Test
  public void passTurn() {
    assertEquals(Player.PLAYER1, qb1.getTurn());
    qb1.passTurn();
    assertEquals(Player.PLAYER2, qb1.getTurn());
    qb1.passTurn();
    assertEquals(Player.PLAYER1, qb1.getTurn());
    assertEquals(Player.PLAYER1, qb1.getTurn());
    qb1.passTurn();
    assertEquals(Player.PLAYER2, qb1.getTurn());
  }

  /** Tests the method startGame(). */
  @Test
  public void startGame() {
    // haha bees!
    List<Card> tripleDeck =
        Arrays.asList(
            bee, bee, bee, bee, bee, bee, bee, bee, bee, bee, bee, bee, bee, bee, bee, bee, bee,
            bee, bee, bee);
    // Checking that startGame disallows triplicates
    assertThrows(
        IllegalArgumentException.class,
        () -> qbSmall.startGame(tripleDeck, new ArrayList<>(deck2), 5, false));
    assertThrows(
        IllegalArgumentException.class,
        () -> qbSmall.startGame(new ArrayList<>(deck2), tripleDeck, 5, false));
    // Checking that startGame needs handsize under a third of the given deck(s)
    assertThrows(
        IllegalArgumentException.class,
        () -> qbSmall.startGame(new ArrayList<>(deck1), new ArrayList<>(deck2), 50, false));
    assertThrows(
        IllegalArgumentException.class,
        () -> qbSmall.startGame(new ArrayList<>(deck1), new ArrayList<>(deck2), -1, false));
    // Checking that startGame needs non-null hands
    assertThrows(
        IllegalArgumentException.class,
        () -> qbSmall.startGame(null, new ArrayList<>(deck2), 3, false));
    assertThrows(
        IllegalArgumentException.class,
        () -> qbSmall.startGame(new ArrayList<>(deck2), null, 3, false));
    assertThrows(IllegalArgumentException.class, () -> qbSmall.startGame(null, null, 3, false));
    // Checking that you can't start a game twice
    qbSmall.startGame(new ArrayList<>(deck1), new ArrayList<>(deck2), 3, false);
    assertThrows(
        IllegalStateException.class,
        () -> qbSmall.startGame(new ArrayList<>(deck1), new ArrayList<>(deck2), 3, false));
  }

  /** Tests the method placeCardInPosition(). */
  @Test
  public void placeCardInPosition() {
    // Throws
    assertThrows(IllegalArgumentException.class, () -> qbWide.placeCardInPosition(-1, 0, 0));
    assertThrows(IllegalArgumentException.class, () -> qbWide.placeCardInPosition(40, 0, 0));
    assertThrows(IllegalArgumentException.class, () -> qbWide.placeCardInPosition(0, -1, 0));
    assertThrows(IllegalArgumentException.class, () -> qbWide.placeCardInPosition(0, 30, 0));
    assertThrows(IllegalArgumentException.class, () -> qbWide.placeCardInPosition(0, 0, -1));
    assertThrows(IllegalArgumentException.class, () -> qbWide.placeCardInPosition(0, 0, 25));
    // If there's not enough pawns in that position to place this card
    assertThrows(IllegalArgumentException.class, () -> qbWide.placeCardInPosition(0, 1, 0));
    // Shows that the board can properly update before and after a card gets placed.
    assertEquals(new Pawns(1, Player.PLAYER1), qb1.getCell(0, 0));
    qb1.placeCardInPosition(0, 0, 0);
    assertEquals(bee, qb1.getCell(0, 0));
    // Shows that influence changes before and after a card gets placed.
    setUp();
    assertEquals(new Pawns(), qbWide.getCell(1, 2));
    //    assertThrows(IllegalArgumentException.class,
    //            () -> qbWide.placeCardInPosition(0, 1, 2));
    qbWide.placeCardInPosition(0, 0, 2);
    qbWide.passTurn();
    assertEquals(bee, qbWide.getCell(0, 2));
    assertEquals(new Pawns(1, Player.PLAYER1), qbWide.getCell(1, 2));
    qbWide.placeCardInPosition(0, 1, 2);
  }

  //  @Test
  //  public void checkPosition() {}
  //
  //  @Test
  //  public void drawCard() {}
  //
  //  @Test
  //  public void getCell() {}

  /** Tests the method isGameOver(). */
  @Test
  public void isGameOver() {
    assertThrows(IllegalStateException.class, () -> qbSmall.isGameOver());
    assertFalse(qb1.isGameOver());
    qb1.passTurn();
    qb1.passTurn();
    assertTrue(qb1.isGameOver());
  }

  //  @Test
  //  public void getHand() {}

  /** Tests the method getScores(). */
  @Test
  public void getScores() { // :( yellow lines >:-(
    assertThrows(IllegalStateException.class, () -> qbSmall.getScores(Player.PLAYER1));
    assertThrows(IllegalArgumentException.class, () -> qb1.getScores(null));
    Integer[] zeroes = {0, 0, 0, 0, 0};

    assertEquals(Arrays.toString(zeroes), Arrays.toString(qb1.getScores(Player.PLAYER1)));
    assertEquals(Arrays.toString(zeroes), Arrays.toString(qb1.getScores(Player.PLAYER2)));
  }

  /** Tests the method getTurn(). */
  @Test
  public void getTurn() {
    assertEquals(Player.PLAYER1, qb1.getTurn());
    qb1.passTurn();
    assertEquals(Player.PLAYER2, qb1.getTurn());
    qb1.passTurn();
    assertEquals(Player.PLAYER1, qb1.getTurn());
    assertEquals(Player.PLAYER1, qb1.getTurn());
    qb1.passTurn();
    assertEquals(Player.PLAYER2, qb1.getTurn());
  }

  /** Tests the method getTotalScore(). */
  @Test
  public void getTotalScore() {
    assertEquals(0, qb1.getTotalScore(Player.PLAYER1));
    assertEquals(0, qb1.getTotalScore(Player.PLAYER2));
  }
}
