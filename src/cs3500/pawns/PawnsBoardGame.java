package cs3500.pawns;

import cs3500.pawns.controller.CPUController;
import cs3500.pawns.controller.CPUPlayer;
import cs3500.pawns.controller.ControllerAdapter;
import cs3500.pawns.controller.DeckReader;
import cs3500.pawns.controller.FillFirst;
import cs3500.pawns.controller.HumanController;
import cs3500.pawns.controller.HumanPlayer;
import cs3500.pawns.controller.MaxRowScore;
import cs3500.pawns.controller.Player;
import cs3500.pawns.controller.QueensBloodController;
import cs3500.pawns.controller.Strategy;
import cs3500.pawns.model.PawnsBoardModelToIPawnsBoardProviderAdapter;
import cs3500.pawns.model.Turn;
import cs3500.pawns.model.QueensBlood;
import cs3500.pawns.model.TurnUtils;
import cs3500.pawns.provider.model.ProviderIPawnsBoard;
import cs3500.pawns.view.PawnsBoardGUIViewToQueensBloodGUIViewAdapter;
import cs3500.pawns.view.QueensBloodGUIView;
import cs3500.pawns.view.QueensBloodView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/** allows the QueensBlood game to be played. */
public final class PawnsBoardGame {

  //  - Create parameters for the main method in PawnsBoardGame
  //    - The first and second strings are the paths to the red and blue player's decks
  //          - The third and fourth describe what kind of players red and blue are
  //          ("human", "fillfirst", "maxrowscore" are the only possible ones rn)
  // Example: docs/deck.config docs/deck.config human fillFirst

  /**
   * public main method for the QueensBlood Game.
   *
   * @param args the additional args to the main method
   */
  public static void main(String[] args) {
    if (args.length != 4) {
      throw new IllegalArgumentException("");
    }
    String redDeckPathStr = args[0];
    String blueDeckPathStr = args[1];

    String redPlayerType = args[2];
    String bluePlayerType = args[3];
    if (!redPlayerType.equalsIgnoreCase("human")
        && !redPlayerType.equalsIgnoreCase("fillfirst")
        && !redPlayerType.equalsIgnoreCase("maxrowscore")) {
      redPlayerType = "human";
    }

    if (!bluePlayerType.equalsIgnoreCase("human")
        && !bluePlayerType.equalsIgnoreCase("fillfirst")
        && !bluePlayerType.equalsIgnoreCase("maxrowscore")) {
      bluePlayerType = "human";
    }

    Path redDeckPath = Paths.get(redDeckPathStr);
    Path blueDeckPath = Paths.get(blueDeckPathStr);
    if (!Files.exists(redDeckPath)) {
      redDeckPath = Paths.get("docs/deck.config");
    }
    if (!Files.exists(blueDeckPath)) {
      blueDeckPath = Paths.get("docs/deck.config");
    }
    QueensBlood model = new QueensBlood(5, 3, new Random());
    model.startGame(
        DeckReader.readFile(Turn.PLAYER1, redDeckPath),
        DeckReader.readFile(Turn.PLAYER2, blueDeckPath),
        5,
        false);
    ProviderIPawnsBoard adapter = new PawnsBoardModelToIPawnsBoardProviderAdapter(model);

    QueensBloodView redView = new QueensBloodGUIView(model, Turn.PLAYER1);
    PawnsBoardGUIViewToQueensBloodGUIViewAdapter blueView =
        new PawnsBoardGUIViewToQueensBloodGUIViewAdapter(adapter, TurnUtils.getColor(Turn.PLAYER2));

    Player redPlayer;
    QueensBloodController redController;
    Player bluePlayer;
    QueensBloodController blueController;

    if (redPlayerType.equalsIgnoreCase("human")) {
      redPlayer = new HumanPlayer(Turn.PLAYER1);
      redController = new HumanController(model, redPlayer, redView);
    } else if (redPlayerType.equalsIgnoreCase("fillfirst")) {
      System.out.println("here!");
      Strategy strat = new FillFirst();
      redPlayer = new CPUPlayer(model, strat, Turn.PLAYER1);
      redController = new CPUController(model, redPlayer, redView);
    } else {
      Strategy strat = new MaxRowScore();
      redPlayer = new CPUPlayer(model, strat, Turn.PLAYER1);
      redController = new CPUController(model, redPlayer, redView);
    }

    if (bluePlayerType.equalsIgnoreCase("human")) {
      bluePlayer = new HumanPlayer(Turn.PLAYER2);
      blueController = new ControllerAdapter(model, blueView, bluePlayer);
    } else if (bluePlayerType.equalsIgnoreCase("fillfirst")) {
      Strategy strat = new FillFirst();
      bluePlayer = new CPUPlayer(model, strat, Turn.PLAYER2);
      blueController = new CPUController(model, bluePlayer, blueView);
    } else {
      Strategy strat = new MaxRowScore();
      bluePlayer = new CPUPlayer(model, strat, Turn.PLAYER2);
      blueController = new CPUController(model, bluePlayer, blueView);
    }

    model.addNotified(redController);
    model.addNotified(blueController);
    model.sendNotifToAll();
    redView.display();
    blueView.makeVisible();
  }
}
