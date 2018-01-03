package com.github.jessealva.poker.interfac;

import java.util.Comparator;

/**
 * Created by jesusalva on 12/26/17.
 */
public interface Hand {
    public void receiveCard(Card card);
    public Card[] show();
    public Card[] discard(Rules rules);
    public int cardsNeeded();
    public boolean allSameSuit();
    public Comparator<Card> comparator();
    public boolean isStraight();
}
