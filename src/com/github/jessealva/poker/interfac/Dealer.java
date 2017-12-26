package com.github.jessealva.poker.interfac;

/**
 * Created by jesusalva on 12/26/17.
 */
public interface Dealer {
    public void shuffle();
    public void initGame(int numberOfHands);
    public void dealCards();
    public void askForDiscards();
    public void dealLastRound();
    public Hand determineWinner();
}
