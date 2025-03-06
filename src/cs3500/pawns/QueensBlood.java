package cs3500.pawns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QueensBlood {

    private final Cell[][] board;
    //Deck for player 1.
    private List<Card> deck1;
    //Deck for player 2.
    private List<Card> deck2;

    private List<Card> hand1;
    private List<Card> hand2;
    //List of scores for player 1. Goes top-to-bottom.
    private int[] score1;
    //List of scores for player 2.
    private int[] score2;
    private boolean started;
    private final Random random;
    //What turn it is
    private Player turn;

    public QueensBlood(int width, int height, Random r) {
        if(r == null) {
            throw new IllegalArgumentException("Random can't be null!");
        }
        if(width < 1 || height < 1 || width % 2 == 0 || height % 2 == 0) {
            throw new IllegalArgumentException("Width and height must be odd and greater than 1!");
        }
        this.board = new Cell[width][height];
        for(int i = width - 1; i >= 0; i--) {
            for(int j = height - 1; j >= 0; j--) {
                //TODO: Figure out how to start each player out with a row of 1 pawn
                this.board[i][j] = new Pawns(null);
            }
        }
        this.started = false; //I figured i'd just keep the system from pokerPolygons
        this.random = r;
        this.deck1 = null;
        this.deck2 = null;
        this.score1 = new int[height];
        this.score2 = new int[height];
        this.hand1 = null;
        this.hand2 = null;
        this.turn = Player.PLAYER1;
        for(int k = height - 1; k >= 0; k--) {
            this.score1[k] = 0; //Setting the scores to zero
            this.score2[k] = 0;
        }
    }

    public void startGame(List<Card> deck1, List<Card> deck2) {
        //TODO: Check if decks can fill the board
        //TODO: Create default decks?? Figure out how deck text importing works or whatever
        if(deck1 == null || deck2 == null || deck1.isEmpty() || deck2.isEmpty()) {
            throw new IllegalArgumentException("Decks can't be null or empty!");
        }
        this.started = true;
        this.deck1 = deck1;
        this.deck2 = deck2;
        //TODO: grab some cards for the hand
        this.hand1 = new ArrayList<>();
        this.hand2 = new ArrayList<>();
    }

    //This is a little messy, sorry :-(
    public void placeCardInPosition(int cardIndex, int x, int y) {
        Card toPlace; //The card's affiliation is a property of the card, so this shouldn't be an issue.
        try { //Grabs the card from the correct hand
            if(this.turn == Player.PLAYER1) {
                toPlace = hand1.remove(cardIndex);
            }
            else {
                toPlace = hand2.remove(cardIndex);
            }
        }
        catch(IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Illegal hand index!");
        }
        try { //Places the card in the correct position
            if(this.board[x][y].getPawns() != -1) {
                this.board[x][y] = toPlace;
                applyInfluence(toPlace.getInfluence());
            }
            else { throw new IllegalArgumentException("There's already a card there, dingus!"); }
        }
        catch(IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Illegal board indices!");
        }
    }

    /**
     * Places pawns around the placed card per it's area of influence.
     * @param inf the area of influence, grabbed from the card.
     */
    private void applyInfluence(Boolean[][] inf) {
        //TODO: code this lol ;-]
    }

    /**
     * Grabs the cell at the given position.
     * @param x
     * @param y
     * @return
     */
    public Cell getCell(int x, int y) {
        try {
            return this.board[x][y];
        }
        catch(IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Illegal board indices");
        }
    }


}
