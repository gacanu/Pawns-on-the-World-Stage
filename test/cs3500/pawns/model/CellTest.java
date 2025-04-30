package cs3500.pawns.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

/** Tests for methods on the Interface Cell. Encompasses both Unit and Pawns. */
public class CellTest {
  Random testRand = new Random(1);
  Boolean[][] cavalryGrid = generateGrid(testRand);
  Cell card1 = new Unit("Cavalry", 1, 1, cavalryGrid.clone(), Turn.PLAYER1);
  Cell card2 = new Unit("Cavalry", 1, 1, cavalryGrid.clone(), Turn.PLAYER1);
  Cell emptySpace = new Pawns();
  Cell onePawn = new Pawns(1, Turn.PLAYER1);
  Cell twoPawn = new Pawns(2, Turn.PLAYER2);
  Cell threePawn = new Pawns(3, Turn.PLAYER1);

  private Boolean[][] generateGrid(Random r) {
    Boolean[][] soln = new Boolean[5][5];
    for (Boolean[] a : soln) {
      for (int i = 0; i < 5; i++) {
        a[i] = r.nextBoolean();
      }
    }
    return soln;
  }

  /** Sets up testing methods prior to execution. */
  @Before
  public void setUp() {
    // System.out.println(Arrays.deepToString(generateGrid(new Random())));
    twoPawn = new Pawns(2, Turn.PLAYER2);
    threePawn = new Pawns(3, Turn.PLAYER1);
  }

  /** Tests construction of objects in the classes Unit and Cell. */
  @Test
  public void testConstruction() {
    // Testing with illegal inputs.
    assertThrows(IllegalArgumentException.class, () -> new Pawns(-1, Turn.PLAYER1));
    assertThrows(IllegalArgumentException.class, () -> new Pawns(4, Turn.PLAYER2));
    assertThrows(IllegalArgumentException.class, () -> new Pawns(2, null));
    assertThrows(
        IllegalArgumentException.class,
        () -> new Unit("Cavalry", -1, 1, generateGrid(testRand), Turn.PLAYER1));
    assertThrows(
        IllegalArgumentException.class,
        () -> new Unit("Cavalry", 4, 1, generateGrid(testRand), Turn.PLAYER1));
    assertThrows(
        IllegalArgumentException.class,
        () -> new Unit("Cavalry", 1, 4, generateGrid(testRand), Turn.PLAYER1));
    assertThrows(
        IllegalArgumentException.class,
        () -> new Unit("Cavalry", 1, -1, generateGrid(testRand), Turn.PLAYER1));
  }

  /** Tests the method AddPawn() in the interface Cell. */
  @Test
  public void testAddPawn() {

    // USING ADDAWN ON PAWNS
    // Testing that pawns get added correctly
    assertEquals(2, twoPawn.getPawns());
    twoPawn.addPawn(Turn.PLAYER2);
    assertEquals(3, twoPawn.getPawns());
    // Adding pawns past the limit (3) will add no pawns
    twoPawn.addPawn(Turn.PLAYER2);
    assertEquals(3, twoPawn.getPawns());
    // Adding pawns from a different player will change the player, not the pawn count
    assertEquals(1, onePawn.getPawns());
    assertEquals(Turn.PLAYER1, onePawn.getAffiliation());
    onePawn.addPawn(Turn.PLAYER2);
    assertEquals(1, onePawn.getPawns());
    assertEquals(Turn.PLAYER2, onePawn.getAffiliation());
    // Giving a null player gives an exception
    assertThrows(IllegalArgumentException.class, () -> onePawn.addPawn(null));
    // Adding pawns on an empty space will set its affiliation and pawn count
    assertEquals(0, emptySpace.getPawns());
    assertNull(emptySpace.getAffiliation());
    emptySpace.addPawn(Turn.PLAYER2);
    assertEquals(1, emptySpace.getPawns());
    assertEquals(Turn.PLAYER2, emptySpace.getAffiliation());

    // USING ADDPAWN ON CARDS
    // addPawn does nothing to cards. it doesn't even change it's affiliation!
    assertEquals(card1, card2);
    assertEquals(Turn.PLAYER1, card1.getAffiliation());
    card1.addPawn(Turn.PLAYER2);
    assertEquals(card1, card2);
    assertEquals(Turn.PLAYER1, card1.getAffiliation());
  }

  /** Tests the method Equals() in the interface Cell. */
  @Test
  public void testEquals() {
    // These cards have the same name, cost, value, influence grid, and player.
    // They are the same!
    card1 = new Unit("Cavalry", 1, 1, cavalryGrid.clone(), Turn.PLAYER1);
    card2 = new Unit("Cavalry", 1, 1, cavalryGrid.clone(), Turn.PLAYER1);
    assertEquals(card1, card2);
    // Differing name
    Unit card3 = new Unit("Wasp", 1, 2, cavalryGrid.clone(), Turn.PLAYER1);
    Unit card4 = new Unit("Bee", 1, 2, cavalryGrid.clone(), Turn.PLAYER1);
    assertNotEquals(card3, card4);
    // Differing cost
    Unit card5 = new Unit("Bee", 2, 2, cavalryGrid.clone(), Turn.PLAYER1);
    Unit card6 = new Unit("Bee", 1, 2, cavalryGrid.clone(), Turn.PLAYER1);
    assertNotEquals(card5, card6);
    // Differing value
    Unit card7 = new Unit("Bee", 1, 1, cavalryGrid.clone(), Turn.PLAYER1);
    Unit card8 = new Unit("Bee", 1, 2, cavalryGrid.clone(), Turn.PLAYER1);
    assertNotEquals(card7, card8);
    // Differing influence grid
    Unit card9 = new Unit("Bee", 2, 2, cavalryGrid.clone(), Turn.PLAYER1);
    Unit card10 = new Unit("Bee", 1, 2, generateGrid(new Random(3)), Turn.PLAYER1);
    assertNotEquals(card9, card10);
    // Differing affiliation
    Unit card11 = new Unit("Bee", 1, 2, cavalryGrid.clone(), Turn.PLAYER2);
    Unit card12 = new Unit("Bee", 1, 2, cavalryGrid.clone(), Turn.PLAYER1);
    assertNotEquals(card11, card12);
  }
}
