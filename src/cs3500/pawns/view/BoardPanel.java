package cs3500.pawns.view;

import cs3500.pawns.model.Cell;
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

/** the panel that represents the board. */
public class BoardPanel extends JPanel {

  private final ReadonlyPawnsBoardModel model;
  private int selectedX = -1;
  private int selectedY = -1;

  /**
   * the constructor for the hand panel.
   *
   * @param model it's working with
   */
  BoardPanel(ReadonlyPawnsBoardModel model) {
    this.model = model;
  }

  protected void clearSelections() {
    this.selectedX = -1;
    this.selectedY = -1;
    this.repaint();
  }

  /**
   * the feature that is being implemented.
   *
   * @param f the given feature
   */
  public void addFeature(ViewFeatures f) {
    // System.out.println("gaming");
    this.addMouseListener(
        new MouseListener() {
          /**
           * Processes a mouseclick.
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
    int subdivisionY = 1000 / model.getHeight();
    Point2D unitPoint =
        new Point((int) (newPoint.getX() / subdivisionX), (int) (newPoint.getY() / subdivisionY));
    if (!((selectedX == unitPoint.getX()) && (selectedY == unitPoint.getY()))) {
      f.handleBoardClick((int) unitPoint.getX(), (int) unitPoint.getY());
      selectedX = (int) unitPoint.getX();
      selectedY = (int) unitPoint.getY();
      repaint();
    } else {
      selectedX = -1;
      selectedY = -1;
      repaint();
    }
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
    drawGrid(g2d);
  }

  /**
   * draws the grid onto the given graphic.
   *
   * @param g the graphic
   */
  private void drawGrid(Graphics g) {
    int width = model.getWidth();
    int height = model.getHeight();
    Font f = new Font("font", Font.PLAIN, 30);
    g.setFont(f);
    int cellWidth = 1000 / width;
    int cellHeight = 1000 / height;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        // This bit draws the colors + text for each cell
        drawCell(model.getCell(i, j), i, j, cellWidth, cellHeight, g);
        // This bit below draws the grid afterwards
        g.setColor(Color.BLACK);
        g.drawRect(cellWidth * i, cellHeight * j, cellWidth, cellHeight);
        // This bit gives coordinates
        g.drawString(i + ", " + j, cellWidth * i + 10, cellHeight * j + 30);
      }
    }
  }

  /**
   * draws the cells onto the given graphic.
   *
   * @param g the graphic
   */
  private void drawCell(Cell c, int i, int j, int cellWidth, int cellHeight, Graphics g) {
    Color color;
    if (c.getAffiliation() != null) {
      switch (c.getAffiliation()) {
        case PLAYER1:
          color = Color.RED;
          break;
        case PLAYER2:
          color = Color.BLUE;
          break;
        default:
          color = Color.WHITE;
      }
    } else {
      color = Color.GRAY;
    }
    if (i == selectedX && j == selectedY) {
      color = Color.GREEN;
      // System.out.println("fjfjjf");
    }
    // System.out.println(color);
    g.setColor(color);
    g.fillRect(cellWidth * i, cellHeight * j, cellWidth, cellHeight);
    g.setColor(Color.BLACK);
    g.drawString(c.toString(), cellWidth * i + 50, cellHeight * j + 100);
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
   * Gives the preferred logical size for this AffineTransform - 1000 x 1000. Basically, everything
   * in this view will scale from 1 to 1000.
   *
   * @return the preferred logical size for this AffineTransform.
   */
  private Dimension getPreferredLogicalSize() {
    return new Dimension(1000, 1000);
  }

  /**
   * Scales the board to the desired size.
   *
   * @return an affinetransform.
   */
  private AffineTransform transformLogicalToBoard() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getBoardIndexes();
    // ret.translate(getWidth() / 2., getHeight() / 2.);
    ret.scale((double) getWidth() / model.getWidth(), (double) getHeight() / model.getHeight());
    // ret.scale(1, -1);
    return ret;
  }

  /**
   * Gets the boards indexes.
   *
   * @return a new Dimension having the models width and height.
   */
  private Dimension getBoardIndexes() {
    return new Dimension(model.getWidth(), model.getHeight());
  }
}
