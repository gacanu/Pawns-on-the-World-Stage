package cs3500.pawns.view;

import cs3500.pawns.model.Card;
import cs3500.pawns.model.Turn;
import cs3500.pawns.model.ReadonlyPawnsBoardModel;

import javax.swing.JPanel;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/** the panel that represents the players hand. */
public class HandPanel extends JPanel {
  private final ReadonlyPawnsBoardModel model;
  private final Turn turn;
  private int selectedX = -1;

  /**
   * the constructor for the hand panel.
   *
   * @param model it's working with
   */
  HandPanel(ReadonlyPawnsBoardModel model, Turn turn) {
    this.turn = turn;
    this.model = model;
  }

  /**
   * the feature that is being implemented.
   *
   * @param f the given feature
   */
  public void addFeature(ViewFeatures f) {
    this.addMouseListener(
        new MouseListener() {
          /**
           * Processes a mouse click.
           *
           * @param e the event to be processed
           */
          @Override
          public void mouseClicked(MouseEvent e) {
            handleMouseClick(e, f);
          }

          /**
           * Processes a mouse press.
           *
           * @param e the event to be processed
           */
          @Override
          public void mousePressed(MouseEvent e) {
            // a potential mouse pressed option
          }

          /**
           * Processes a mouse release.
           *
           * @param e the event to be processed
           */
          @Override
          public void mouseReleased(MouseEvent e) {
            // a potential mouse released option
          }

          /**
           * Processes a mouse enter.
           *
           * @param e the event to be processed
           */
          @Override
          public void mouseEntered(MouseEvent e) {
            // a potential mouse entered option
          }

          /**
           * Processes a mouse exit.
           *
           * @param e the event to be processed
           */
          @Override
          public void mouseExited(MouseEvent e) {
            // a potential mouse exited option
          }
        });
  }

  /**
   * handles the mouse event along with the given feature.
   *
   * @param e mouse event to grab point from
   * @param f feature that will handle the given mouse click
   */
  private void handleMouseClick(MouseEvent e, ViewFeatures f) {
    Point2D point = e.getPoint();
    Point2D newPoint =
        new Point(
            (int) (point.getX() / getWidth() * 1000), (int) (point.getY() / getHeight() * 1000));

    int subdivisionX = 1000 / model.getWidth();

    Point2D unitPoint = new Point((int) (newPoint.getX() / subdivisionX), (int) (newPoint.getY()));

    if (selectedX == unitPoint.getX()) {
      selectedX = -1;
      repaint();
    } else {
      f.handleHandClick((int) unitPoint.getX());
      selectedX = (int) unitPoint.getX();
      repaint();
    }
  }

  /** updates the board. */
  public void updateBoard() {
    // FIXME: Actually do something here

  }

  /**
   * update the hand given the turn.
   *
   * @param p what turn it's on
   */
  public void updateHand(Turn p) {
    // FIXME: Show the hand of the specified player in the view.

  }

  /**
   * paints the given graphic component.
   *
   * @param g the <code>Graphics</code> object to protect
   */
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.transform(transformLogicalToPhysical());
    this.setMinimumSize(new Dimension(800, 400));
    drawHand(g2d);
  }

  /**
   * draws the hand onto the given graphic.
   *
   * @param g the graphic
   */
  private void drawHand(Graphics g) {
    Turn p = this.turn;
    int width = model.getWidth();
    int handsize = model.getHand(p).size();

    Font f = new Font("font", Font.PLAIN, 30);
    g.setFont(f);

    int cellWidth = 1000 / width;

    ArrayList<Card> cardArray = new ArrayList<>(model.getHand(p));

    for (int i = 0; i < handsize; i++) {

      drawCard(cardArray.get(i), i, cellWidth, g);
    }
  }

  /**
   * draws the cards onto the given graphic.
   *
   * @param g the graphic
   */
  private void drawCard(Card c, int i, int cellWidth, Graphics g) {
    Color color;

    if (c.getAffiliation() == Turn.PLAYER1) {
      color = Color.RED;
    } else {
      color = Color.BLUE;
    }
    if (i == selectedX) {
      color = Color.GREEN;
    }

    g.setColor(color);
    g.fillRect(cellWidth * i, 0, cellWidth, 1000);
    g.setColor(Color.BLACK);
    g.drawString(c.getName(), 200 * i, 150);
    g.drawString("V: " + c.getValue(), 200 * i, 200);
    g.drawString("C: " + c.getCost(), cellWidth * i, 250);
    drawString(g, infToString(c.getInfluence()), cellWidth * i, 300);
    g.setColor(Color.BLACK);
    g.drawRect(cellWidth * i, 0, cellWidth, 1000);
  }

  /**
   * draws the strings onto the given graphic.
   *
   * @param g the graphic
   */
  void drawString(Graphics g, String text, int x, int y) {
    for (String line : text.split(System.lineSeparator())) {
      g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
  }

  private String infToString(Boolean[][] boolArray) {
    StringBuilder s = new StringBuilder();
    for (int a = 0; a < 5; a++) {
      for (int b = 0; b < 5; b++) {
        if (a == 2 && b == 2) {
          s.append("C");
          continue;
        }
        if (boolArray[a][b]) {
          s.append("I");
        } else {
          s.append("X");
        }
      }
      s.append(System.lineSeparator());
    }
    return s.toString();
  }

  /**
   * Transforms the drawing window such that it scales by the preferred logical size.
   *
   * @return The necessary transformation
   */
  private AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    // ret.translate(getWidth() / 2., getHeight() / 2.);
    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());
    // ret.scale(1, -1);
    return ret;
  }

  /**
   * gets the preferred size of 1000 by 1000.
   *
   * @return a new siz dimension
   */
  private Dimension getPreferredLogicalSize() {
    return new Dimension(1000, 1000);
  }

  /** clears the selections. */
  protected void clearSelections() {
    this.selectedX = -1;
  }
}
