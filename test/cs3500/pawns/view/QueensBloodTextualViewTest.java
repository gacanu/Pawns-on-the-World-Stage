package cs3500.pawns.view;

import cs3500.pawns.model.Card;
import cs3500.pawns.controller.DeckReader;
import cs3500.pawns.model.PawnsBoardModel;
import cs3500.pawns.model.Turn;
import cs3500.pawns.model.QueensBlood;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/** tests the methods in QueensBloodTextualView. */
public class QueensBloodTextualViewTest {
  Appendable out = new StringBuilder();
  Random r = new Random(1);
  PawnsBoardModel qbWide = new QueensBlood(5, 3, r);
  QueensBloodView qbWideView = new QueensBloodTextualView(qbWide, out);
  PawnsBoardModel qbTall = new QueensBlood(3, 5, r);
  QueensBloodView qbTallView = new QueensBloodTextualView(qbTall, out);
  Path path = Paths.get("docs/deck.config");
  List<Card> deck1 = DeckReader.readFile(Turn.PLAYER1, path);
  List<Card> deck2 = DeckReader.readFile(Turn.PLAYER2, path);

  @Before
  public void setUp() {
    qbWide = new QueensBlood(5, 3, r);
    qbTall = new QueensBlood(3, 5, r);
    qbWide.startGame(new ArrayList<>(deck1), new ArrayList<>(deck2), 5, false);
    qbTall.startGame(new ArrayList<>(deck1), new ArrayList<>(deck2), 5, false);
    qbWideView = new QueensBloodTextualView(qbWide, out);
    qbTallView = new QueensBloodTextualView(qbTall, out);
  }

  /**
   * Tests the method render() in the class QueensBloodTextualView.
   *
   * @throws IOException if input fails somehow.
   */
  @Test
  public void render() throws IOException {
    class FailingAppendable implements Appendable {

      /**
       * Appends the specified character sequence to this {@code Appendable}.
       *
       * @param csq The character sequence to append. If {@code csq} is {@code null}, then the four
       *     characters {@code "null"} are appended to this Appendable.
       * @return A reference to this {@code Appendable}
       * @throws IOException If an I/O error occurs
       */
      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException(";-0");
      }

      /**
       * Appends a subsequence of the specified character sequence to this {@code Appendable}.
       *
       * @param csq The character sequence from which a subsequence will be appended. If {@code csq}
       *     is {@code null}, then characters will be appended as if {@code csq} contained the four
       *     characters {@code "null"}.
       * @param start The index of the first character in the subsequence
       * @param end The index of the character following the last character in the subsequence
       * @return A reference to this {@code Appendable}
       */
      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException(";-0");
      }

      /**
       * Appends the specified character to this {@code Appendable}.
       *
       * @param c The character to append
       * @return A reference to this {@code Appendable}
       * @throws IOException If an I/O error occurs
       */
      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException(";-0");
      }
    }

    FailingAppendable fail = new FailingAppendable();
    Appendable out = new StringBuilder();
    qbWideView = new QueensBloodTextualView(qbWide, out);
    qbWide.placeCardInPosition(0, 0, 0);
    qbWideView.display();
    assertEquals("1 R1__1 0\n" + "0 1___1 0\n" + "0 1___1 0\n", out.toString());
    // Testing that render can throw an IllegalStateException from the failing appendable.
    qbWideView = new QueensBloodTextualView(qbWide, fail);
    assertThrows(IllegalStateException.class, () -> qbWideView.display());
  }

  /** test the method to string in the QueensBloodTextualViewTest. */
  @Test
  public void testToString() {
    // Testing that it updates after being played
    assertEquals("0 1___1 0\n" + "0 1___1 0\n" + "0 1___1 0\n", qbWideView.toString());
    qbWide.placeCardInPosition(0, 0, 0);
    assertEquals("1 R1__1 0\n" + "0 1___1 0\n" + "0 1___1 0\n", qbWideView.toString());
    qbWide.passTurn();
    qbWide.placeCardInPosition(0, 0, 1);
    assertEquals("1 R1__1 0\n" + "2 R1__1 0\n" + "0 2___1 0\n", qbWideView.toString());
    qbWide.placeCardInPosition(0, 4, 0);

    assertEquals("1 R1_1B 1\n" + "2 R1__1 0\n" + "0 2___1 0\n", qbWideView.toString());
    assertEquals(
        "0 1_1 0\n" + "0 1_1 0\n" + "0 1_1 0\n" + "0 1_1 0\n" + "0 1_1 0\n", qbTallView.toString());
  }
}
