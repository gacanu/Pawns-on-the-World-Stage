package cs3500.pawns.view;

import cs3500.pawns.model.Card;
import cs3500.pawns.model.DeckReader;
import cs3500.pawns.model.Player;
import cs3500.pawns.model.QueensBlood;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * tests the methods in QueensBloosTextualView.
 */
public class QueensBloodTextualViewTest {
  Random r = new Random(1);
  QueensBlood qbWide = new QueensBlood(5, 3, r);
  QueensBloodTextualView qbWideView = new QueensBloodTextualView(qbWide);
  QueensBlood qbTall = new QueensBlood(3, 5, r);
  QueensBloodTextualView qbTallView = new QueensBloodTextualView(qbTall);
  Path path = Paths.get("docs/deck.config");
  List<Card> deck1 = DeckReader.readFile(Player.PLAYER1, path);
  List<Card> deck2 = DeckReader.readFile(Player.PLAYER2, path);

  @Before
  public void setUp() {
    qbWide = new QueensBlood(5, 3, r);
    qbTall = new QueensBlood(3, 5, r);
    qbWide.startGame(new ArrayList<>(deck1), new ArrayList<>(deck2), 5, false);
    qbTall.startGame(new ArrayList<>(deck1), new ArrayList<>(deck2), 5, false);
    qbWideView = new QueensBloodTextualView(qbWide);
    qbTallView = new QueensBloodTextualView(qbTall);
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
       * <p>Depending on which class implements the character sequence {@code csq}, the entire
       * sequence may not be appended. For instance, if {@code csq} is a {@link CharBuffer} then the
       * subsequence to append is defined by the buffer's position and limit.
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
       * <p>An invocation of this method of the form {@code out.append(csq, start, end)} when {@code
       * csq} is not {@code null}, behaves in exactly the same way as the invocation
       *
       * <pre>
       *     out.append(csq.subSequence(start, end)) </pre>
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
    qbWide.placeCardInPosition(0, 0, 0);
    qbWideView.render(out);
    assertEquals("1 R1__1 0\n" + "0 1___1 0\n" + "0 1___1 0\n", out.toString());
    // Testing that render can throw an IOException.
    assertThrows(IOException.class, () -> qbWideView.render(fail));
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
        "0 1_1 0\n" + "0 1_1 0\n"
                + "0 1_1 0\n" + "0 1_1 0\n" + "0 1_1 0\n", qbTallView.toString());
  }
}
