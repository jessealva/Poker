package com.github.jessealva.poker.interfac;

/**
 * Created by jesusalva on 12/26/17.
 */
public interface Hand {
    public void receiveCard(Card card);
    public Card[] show();
    public Card[] discard();
    public int cardsNeeded();
}
