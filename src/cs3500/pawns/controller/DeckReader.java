package cs3500.pawns.controller;

import cs3500.pawns.model.Card;
import cs3500.pawns.model.Unit;
import cs3500.pawns.model.Turn;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readString;

/** Class that reads a deck from a filepath. */
public class DeckReader {
  /**
   * Returns a deck of cards from the deck.config with the given player's affiliation.
   *
   * @param aff the affiliation of the cards.
   * @return a deck with the given player's affiliation.
   */
  public static List<Card> readFile(Turn aff, Path filePath) {
    if (aff == null || filePath == null) {
      throw new IllegalArgumentException("Affiliation and filepath cannot be null!");
    }
    String textDeck = "";
    try {
      textDeck = readString(filePath);
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File could not be found at the given path!");
    } catch (IOException e) {
      throw new IllegalStateException("Bad I/O!");
    }
    String[] arrayDeck = textDeck.split("\r\n|\r|\n");
    /*
    Example 1 2
    XXXXX
    XXIXX
    XICIX
    XXIXX
    XXXXX
    */
    List<Card> soln = new ArrayList<>();
    String name = "";
    int cost = 0;
    int value = 0;
    Boolean[][] newInf = new Boolean[5][5];
    createDeck(aff, arrayDeck, name, cost, value, newInf, soln);
    return soln;
  }

  private static void createDeck(
      Turn aff,
      String[] arrayDeck,
      String name,
      int cost,
      int value,
      Boolean[][] newInf,
      List<Card> soln) {
    for (int i = 0; i < arrayDeck.length; i++) {
      int rem = i % 6;
      if (rem == 0) { // Example 1 2
        String[] arr = arrayDeck[i].split(" ");
        if (arr.length != 3) {
          throw new IllegalArgumentException("Incorrect config syntax in your string");
        }
        name = arr[0];
        try {
          cost = Integer.valueOf(arr[1]);
          value = Integer.valueOf(arr[2]);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException(
              "Incorrect config syntax. Cost and value must be a number!");
        }
      } else { // Lines of X's and I's
        if (arrayDeck[i].length() != 5) {
          throw new IllegalArgumentException("Each line of X's and I's must be 5 long!");
        }
        newInf[rem - 1] = toBoolArray(arrayDeck[i], aff, rem);
        if (rem == 5) { // last line of x's
          Card c = new Unit(name, cost, value, newInf, aff);
          soln.add(c); // finally, adding the card.
          name = "";
          cost = 0; // resetting all data after adding it
          value = 0;
          newInf = new Boolean[5][5];
        }
      }
    }
  }

  // Converts a string like 'XXIIX' into an array; for this example, it would output
  // {false, false, true, true, false}
  // 'C' slots are treated like 'X' slots.
  private static Boolean[] toBoolArray(String s, Turn p, int rem) {
    Boolean[] soln = new Boolean[5];
    char[] a = s.toCharArray();
    if (p == Turn.PLAYER1) {
      for (int i = 0; i < 5; i++) {
        if (a[i] != 'I' && a[i] != 'X' && a[i] != 'C') {
          throw new IllegalArgumentException("Each element must be X, I, or C!");
        }
        if (a[i] == 'C' && (rem != 3 || i != 2)) {
          throw new IllegalArgumentException(
              "Character C must be in the middle of the given card!");
        }
        if (rem == 3 && i == 2 && a[i] != 'C') {
          throw new IllegalArgumentException(
              "Character C must be in the middle of the given card!");
        }
        // In the case of 'X'
        soln[i] = a[i] == 'I';
      }
    } else {
      for (int i = 0; i < 5; i++) {
        if (a[i] == 'C' && (rem != 3 || i != 2)) {
          throw new IllegalArgumentException(
              "Character C must be in the middle of the given card!");
        }
        // In the case of 'X' or 'C'
        soln[4 - i] = a[i] == 'I';
      }
    }
    return soln;
  }
}
