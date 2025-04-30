package cs3500.pawns.model;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import cs3500.pawns.provider.model.PlayerColor;
import cs3500.pawns.provider.model.ProviderCard;
import cs3500.pawns.provider.model.ProviderCell;

/** Converts a customer Cell into a ProviderCell. */
public class CustomerCellToProviderCellAdapter implements ProviderCell, ProviderCard {
  private final Cell cell;

  /**
   * constructor for the customer cell to provider cell converter.
   *
   * @param cell the board cell given
   */
  public CustomerCellToProviderCellAdapter(Cell cell) {
    this.cell = cell;
  }

  /**
   * Gets the score of a cell represented as a map with a single entry. First integer key represents
   * RED score. Second integer value represents BLUE score.
   *
   * @return a map entry.
   */
  @Override
  public Map.Entry<Integer, Integer> getScore() {
    int value = cell.getValue();
    Turn affiliation = cell.getAffiliation();
    if (affiliation == Turn.PLAYER1) {
      return new AbstractMap.SimpleEntry<>(value, 0);
    } else if (affiliation == Turn.PLAYER2) {
      return new AbstractMap.SimpleEntry<>(0, value);
    } else {
      return new AbstractMap.SimpleEntry<>(0, 0);
    }
  }

  /**
   * Influences the cell to the given influencers color. If cell is empty, add an assoicated pawn
   * and claim ownership. IF cell is pawn and same color, add pawn if less than three pawns exist.
   * If cell is pawn and not same color, change ownership. If cell is card, do nothing.
   *
   * @param influencer color to represent influencer.
   */
  @Override
  public void influence(PlayerColor influencer) {
    Turn t = TurnUtils.getTurn(influencer);
    try {
      cell.addPawn(t);
    } catch (IllegalArgumentException ignored) {
    }
  }

  /**
   * Gets the number of pawns.
   *
   * @returns pawns as integer.
   */
  @Override
  public int getPawns() {
    return cell.getPawns();
  }

  /**
   * Returns a copy of this cell.
   *
   * @return a cell that is a copy.
   */
  @Override
  public ProviderCell copy() {
    return new CustomerCellToProviderCellAdapter(cell.dupe());
  }

  /**
   * Returns the playercolor of this cell, if there is a player claiming it.
   *
   * @return Playercolor if player is claiming this cell.
   */
  @Override
  public PlayerColor getPlayerColor() {
    return (cell.getAffiliation() != null) ? TurnUtils.getColor(cell.getAffiliation()) : null;
  }

  /**
   * Returns a string representation of this cell.
   *
   * @return a string of this cell.
   */
  @Override
  public String toString() {
    return cell.toString();
  }

  /**
   * Gets the name of this card.
   *
   * @return a string representing the name of the card.
   */
  @Override
  public String getName() {
    return "";
  }

  /**
   * Gets the cost of this card.
   *
   * @return an int represnting the cost of this card.
   */
  @Override
  public int getCost() {
    return 0;
  }

  /**
   * Gets the value of this card.
   *
   * @return an int representing the value of this card.
   */
  @Override
  public int getValue() {
    return cell.getValue();
  }

  /**
   * Gets the color of this card.
   *
   * @return a color representing the owner of this card.
   */
  @Override
  public PlayerColor getColor() {
    return TurnUtils.getColor(cell.getAffiliation());
  }

  /**
   * Gets the influence of a card, that is, which cells relative to the card get pawns added to
   * them.
   *
   * @return hash map of coordinate pairs (Card is at relative cell 0,0).
   */
  @Override
  public List<Map.Entry<Integer, Integer>> getInfluence() {
    return List.of();
  }
}
