package cs3500.pawns.controller;

import cs3500.pawns.model.Card;
import cs3500.pawns.model.PawnsBoardModel;
import cs3500.pawns.model.QueensBlood;
import cs3500.pawns.model.Turn;
import cs3500.pawns.view.QueensBloodView;
import cs3500.pawns.view.ViewFeatures;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/** Tests for the controllers. */
public class ControllerTest {

  StringBuilder log = new StringBuilder();
  StringBuilder logPlayer1 = new StringBuilder();
  StringBuilder logPlayer2 = new StringBuilder();
  QueensBloodController mockCPUController = new MockCPUController(logPlayer1, Turn.PLAYER1);
  QueensBloodController mockPlayerController = new MockPlayerController(logPlayer2, Turn.PLAYER2);

  // As errors from the controller are 'logged' in the view,
  // Using this mock allows us to see where errors arise.
  Path path = Paths.get("docs" + File.separator + "deck.config");
  List<Card> deck1 = DeckReader.readFile(Turn.PLAYER1, path);
  List<Card> deck2 = DeckReader.readFile(Turn.PLAYER2, path);
  QueensBloodView testView = new ViewMock(log);
  PawnsBoardModel mockModel = new ControllerModelMock();
  QueensBloodController testCont =
      new HumanController(mockModel, new HumanPlayer(Turn.PLAYER2), testView);
  ViewFeatures features = (ViewFeatures) testCont; // Hrm...
  PawnsBoardModel realModel = new QueensBlood(5, 3, new Random());

  @Before
  public void setUp() {
    mockModel = new ControllerModelMock();
    mockModel.addNotified(testCont);
    realModel.addNotified(mockCPUController);
    realModel.addNotified(mockPlayerController);
    realModel.startGame(deck1, deck2, 5, false);
  }

  @Test
  public void testCorrectTurn() {
    mockModel.passTurn();
    testCont.sendNotif(Turn.PLAYER2);
    features.handleBoardClick(1, 2);
    features.handleHandClick(0);
    features.handleConfirm();
    assertEquals("It's not this player's turn yet!", log.toString());
  }

  /** Tests that controllers properly receive signals when the model's turn changes. */
  @Test
  public void testReceiveSignal() {
    realModel.passTurn();
    // Tests what the CPU controller mock receives after both starting the game and passing a turn.
    assertEquals(
        "Received notification - it's this controller's turn, this would execute a move now.\n"
            + "Received notification - but it's not this controller's turn.\n",
        logPlayer1.toString());
    // Tests what the Human controller mock receives.
    assertEquals(
        "Received notification -this controller's turn is now inactive.\n"
            + "Received notification -this controller's turn is now active.\n",
        logPlayer2.toString());
  }
}
