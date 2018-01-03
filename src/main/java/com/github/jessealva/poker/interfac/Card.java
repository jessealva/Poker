package com.github.jessealva.poker.interfac;

import com.github.jessealva.poker.impl.PokerCard;

import java.util.Comparator;

/**
 * Created by jesusalva on 12/26/17.
 */
public interface Card {
    public PokerCard.SUIT getSuit();
    public PokerCard.VALUE getValue();
    public int compare(Card c2);
    public boolean isNextValue(Card c);
    public boolean equals(Card c);
    public String toString();
    public Comparator<Card> getComparator();

}
