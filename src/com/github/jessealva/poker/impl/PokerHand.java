package com.github.jessealva.poker.impl;

import com.github.jessealva.poker.interfac.Card;
import com.github.jessealva.poker.interfac.Hand;

/**
 * Created by jesusalva on 12/26/17.
 */
public class PokerHand implements Hand {
    private Card[] hand;
    //cardIdx will always point to the next empty slot in the hand array.
    private int cardIdx;

    public PokerHand(int handSize) {
        hand = new Card[handSize];
        cardIdx = 0;
    }

    public void receiveCard(Card card) {
        assert(cardIdx < hand.length);
        hand[cardIdx++] = card;
    }

    public Card[] show() {
        return hand;
    }

    public Card[] discard() {
        //TODO implement some algorithm for determining best possible hand and discarding cards that don't get used by
        // best possible hand.
        Card c = hand[--cardIdx];
        hand[cardIdx] = null;
        return new Card[]{c};
    }

    public int cardsNeeded() {
        return hand.length - cardIdx;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for(int i = 0 ; i < hand.length; ++i) {
            sb.append(hand[i]);
            sb.append(", ");
        }
        sb.append('}');
        return sb.toString();
    }
}
