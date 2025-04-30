package cs3500.pawns.model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cs3500.pawns.provider.model.PlayerColor;
import cs3500.pawns.provider.model.ProviderCard;

/** Converts a customer Card into a Provider Card. */
public class CustomerCardToProviderCardAdapter implements ProviderCard {
  private final Card card;

  /**
   * constructor for the adapter Customer Card To Provider Card.
   *
   * @param card the card in a game of queens blood, to be adapted
   */
  public CustomerCardToProviderCardAdapter(Card card) {
    this.card = card;
  }

  /**
   * Gets the name of this card.
   *
   * @return a string representing the name of the card.
   */
  @Override
  public String getName() {
    return card.getName();
  }

  /**
   * Gets the cost of this card.
   *
   * @return an int represnting the cost of this card.
   */
  @Override
  public int getCost() {
    return card.getCost();
  }

  /**
   * Gets the value of this card.
   *
   * @return an int representing the value of this card.
   */
  @Override
  public int getValue() {
    return card.getValue();
  }

  /**
   * Gets the color of this card.
   *
   * @return a color representing the owner of this card.
   */
  @Override
  public PlayerColor getColor() {
    return TurnUtils.getColor(card.getAffiliation());
  }

  /**
   * Gets the influence of a card, that is, which cells relative to the card get pawns added to
   * them.
   *
   * @return hash map of coordinate pairs (Card is at relative cell 0,0).
   */
  @Override
  public List<Map.Entry<Integer, Integer>> getInfluence() {
    Boolean[][] influence = card.getInfluence();
    List<Map.Entry<Integer, Integer>> list = new ArrayList<>();
    for (int i = 0; i < influence.length; i++) {
      for (int j = 0; j < influence[i].length; j++) {
        if (influence[i][j] != null && influence[i][j]) {
          int x = i - 2;
          int y = j - 2;
          list.add(new AbstractMap.SimpleEntry<>(x, y));
        }
      }
    }
    return list;
  }
}
