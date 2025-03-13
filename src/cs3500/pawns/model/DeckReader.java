package cs3500.pawns.model;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that reads a deck from the deck.config file located in the docs folder.
 */
public class DeckReader {
  /**
   * Returns a deck of cards from the deck.config with the given player's affiliation.
   * @param aff the affiliation of the cards.
   * @return a deck with the given player's affiliation.
   */
  public static List<Card>readFile(Player aff) {
    String path = "docs" + File.separator + "deck.config";
    File config = new File(path);
    String textDeck;
    try {
      textDeck = String.valueOf(new FileReader(config));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("deck.config not found in the path docs/");
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
    Boolean[][] newInf = new Boolean[5][5];
    List<Card> soln = new ArrayList<>();
    for(int i = 0; i < arrayDeck.length; i++) {
      String name;
      int cost;
      int value;
      if((i + 1) % 6 == 1) { //Example 1 2
        String[] arr = arrayDeck[i].split(" ");
        if(arr.length != 3) {
          throw new IllegalArgumentException("Incorrect config syntax in " + arr.toString());
        }
        name = arr[0];
        cost = Integer.valueOf(arr[1]);
        value = Integer.valueOf(arr[2]);
      }
      else {
        //TODO: fix this tragedy
      }
      if((i + 1) % 6 == 0) { //last line of x's
        //TODO: also, figure out how to properly throw errors for all the seven million things that can go wrong here
      }
    }
    return soln;
  }
}
