package cs3500.pawns.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static cs3500.pawns.controller.DeckReader.readFile;
import static java.nio.file.Paths.get;
import static org.junit.Assert.assertThrows;

/** Tests methods in the class DeckReader. */
public class DeckReaderTest {
  Boolean[][] arr = {
    {false, false, false, false, false},
    {false, false, false, false, false},
    {false, true, false, true, false},
    {false, false, false, false, false},
    {false, false, false, false, false}
  };
  Boolean[][] oneSided = {
    {false, false, false, false, false},
    {false, false, false, false, false},
    {true, true, false, false, false},
    {false, false, false, false, false},
    {false, false, false, false, false}
  };
  Boolean[][] mirrored = {
    {false, false, false, false, false},
    {false, false, false, false, false},
    {false, false, false, true, true},
    {false, false, false, false, false},
    {false, false, false, false, false}
  };

  File file1 = new File("docs" + File.separator + "test");
  File file2 = new File("docs" + File.separator + "test2");
  File file3 = new File("docs" + File.separator + "test3");
  Card bee = new Unit("Bee", 1, 1, arr, Turn.PLAYER1);
  Card cavalry = new Unit("Cavalry", 2, 1, oneSided, Turn.PLAYER1);
  Card flippedCavalry = new Unit("Cavalry", 2, 1, mirrored, Turn.PLAYER2);

  private void writeToFile(String message, File file) {
    FileWriter fWrite = null;
    try {
      fWrite = new FileWriter(file.getPath());
      fWrite.write(message);
      fWrite.close();
    } catch (IOException e) {
      throw new RuntimeException("Bad I/O!");
    }
  }

  /** Deletes test files after testing. */
  @After
  public void breakDown() {
    file1.delete();
    file2.delete();
    file3.delete();
  }

  /** Sets up files prior to testing. */
  @Before
  public void setUp() {
    file1 = new File("docs" + File.separator + "test");
    writeToFile("Bee 1 1\n" + "XXXXX\n" + "XXXXX\n" + "XICIX\n" + "XXXXX\n" + "XXXXX", file1);
    file2 = new File("docs" + File.separator + "test2");
    writeToFile("Cavalry 2 1\n" + "XXXXX\n" + "XXXXX\n" + "IICXX\n" + "XXXXX\n" + "XXXXX", file2);
  }

  /** Tests the method readFile in the class DeckReader. */
  @Test
  public void testReadDeck() {
    // File is read normally
    Assert.assertEquals(readFile(Turn.PLAYER1, get(file1.getPath())).get(0), bee);
    // When made into a card, PLAYER2 units (blue) are flipped horizontally upon construction.
    Assert.assertEquals(readFile(Turn.PLAYER1, get(file2.getPath())).get(0), cavalry);
    Assert.assertEquals(readFile(Turn.PLAYER2, get(file2.getPath())).get(0), flippedCavalry);
    //        //When the 'C' is in the incorrect position, an error is thrown.
    //        assertThrows(IllegalArgumentException.class,
    //                () -> readFile(Turn.PLAYER1, Paths.get(file3.getPath())).get(0));
  }

  /** Tests incorrect file construction in the method readFile. */
  @Test
  public void testIncorrectConstruction() {
    // Checks that placing the 'C' character outside the center will cause the program to fail
    boolean failed = false;
    try {
      file3 = new File("docs" + File.separator + "test3");
      writeToFile("Cavalry 2 1\n" + "XXXXX\n" + "XXXXX\n" + "IIXXX\n" + "XXXXX\n" + "XXXXX", file3);
      readFile(Turn.PLAYER1, get(file3.getPath()));
    } catch (IllegalArgumentException e) {
      failed = true;
    }
    Assert.assertTrue(failed);
  }

  /** Also tests incorrect file construction in the method readFile. */
  @Test
  public void testIncorrectConstruction2() {
    // Checks that placing the 'C' character outside the center will cause the program to fail
    boolean failed = false;
    try {
      file3 = new File("docs" + File.separator + "test3");
      writeToFile("Cavalry 2 1\n" + "XXXXX\n" + "XXCXX\n" + "IIXXX\n" + "XXXXX\n" + "XXXXX", file3);
      readFile(Turn.PLAYER1, get(file3.getPath()));
    } catch (IllegalArgumentException e) {
      failed = true;
    }
    Assert.assertTrue(failed);
  }

  /** Also tests incorrect file construction in the method readFile. */
  @Test
  public void testIncorrectConstruction3() {
    file3 = new File("docs" + File.separator + "test3");
    writeToFile("Cavalry 2 1\n" + "XXXXX\n" + "XXXXX\n" + "IICX\n" + "XXXXX\n" + "XXXXX", file3);
    assertThrows(
        IllegalArgumentException.class, () -> readFile(Turn.PLAYER1, get(file3.getPath())));
    writeToFile("Cavalry 2 1\n" + "XXXXX\n" + "XXXXX\n" + "IICXXX\n" + "XXXXX\n" + "XXXXX", file3);
    assertThrows(
        IllegalArgumentException.class, () -> readFile(Turn.PLAYER1, get(file3.getPath())));
    assertThrows(IllegalArgumentException.class, () -> readFile(null, get(file2.getPath())));
    assertThrows(IllegalArgumentException.class, () -> readFile(Turn.PLAYER1, null));
    writeToFile("Cavalry 3 2 1\n" + "XXXXX\n" + "XXXXX\n" + "IICXX\n" + "XXXXX\n" + "XXXXX", file3);
    assertThrows(
        IllegalArgumentException.class, () -> readFile(Turn.PLAYER1, get(file3.getPath())));
    writeToFile("Cavalry 2 a\n" + "XXXXX\n" + "XXXXX\n" + "IICXXX\n" + "XXXXX\n" + "XXXXX", file3);
    assertThrows(
        IllegalArgumentException.class, () -> readFile(Turn.PLAYER1, get(file3.getPath())));
  }
}
