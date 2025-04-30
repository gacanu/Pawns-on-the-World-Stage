package cs3500.pawns.view;

import cs3500.pawns.model.ReadonlyPawnsBoardModel;
import cs3500.pawns.model.Turn;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A QueensBlood view rendered to a Frame. Connects to a controller through its addViewFeatures
 * method. Requires a ReadonlyPawnsBoardModel.
 */
public class QueensBloodGUIView extends JFrame implements QueensBloodView {
  private final BoardPanel boardPanel;
  private final HandPanel handPanel;

  /**
   * queens blood gui view constructor.
   *
   * @param model takes in a read only pawns board model
   */
  public QueensBloodGUIView(ReadonlyPawnsBoardModel model, Turn player) {
    this.boardPanel = new BoardPanel(model);
    this.handPanel = new HandPanel(model, player);
  }

  /**
   * shows the dialog.
   *
   * @param s the string to show.
   */
  @Override
  public void showDialog(String s) {
    JOptionPane.showMessageDialog(this, s);
  }

  /** clears the selections. */
  @Override
  public void clearSelections() {
    this.boardPanel.clearSelections();
    this.handPanel.clearSelections();
  }

  /**
   * add feature adds the given feature's into the queens blood gui view.
   *
   * @param f given feature to implement
   */
  @Override
  public void addFeature(ViewFeatures f) {
    this.boardPanel.addFeature(f);
    this.handPanel.addFeature(f);
    this.addKeyListener(
        new KeyListener() {

          /**
           * Handles a key being typed.
           *
           * @param e the event to be processed
           */
          @Override
          public void keyTyped(KeyEvent e) {
            // a potential key typed option
          }

          /**
           * Handles a key being pressed.
           *
           * @param e the event to be processed
           */
          @Override
          public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
              case KeyEvent.VK_M:
                f.handleConfirm();
                return;
              case KeyEvent.VK_P:
                f.handlePass();
                return;
              default:
            }
          }

          /**
           * Handles a key being released.
           *
           * @param e the event to be processed
           */
          @Override
          public void keyReleased(KeyEvent e) {
            // a potential key release option
          }
        });
  }

  /** displays the board and hand panels. */
  @Override
  public void display() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setMinimumSize(new Dimension(800, 800));
    GridLayout layout = new GridLayout(2, 1);
    this.setLayout(layout);
    this.add(this.boardPanel);
    this.add(this.handPanel);
    this.setVisible(true);
  }

  /** updates the panel. */
  @Override
  public void update() {
    repaint();
    this.handPanel.repaint();
    this.boardPanel.repaint();
  }
}
