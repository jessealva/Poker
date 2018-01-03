package com.github.jessealva.poker.impl;

import com.github.jessealva.poker.interfac.Card;
import com.github.jessealva.poker.interfac.Hand;
import com.github.jessealva.poker.interfac.Rules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Comparator;

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

    public Card[] discard(Rules rules) {
        Card[] discard = rules.toDrop(this);
        Card[] tmp = new Card[hand.length];
        int tmpIdx = 0;

        for(int i = 0 ; i < hand.length; ++i) {
            boolean beingDiscarded = false;
            for(int j= 0; j < discard.length; ++j) {
                if(hand[i].equals(discard[j])) {
                    beingDiscarded = true;
                }
            }
            if(!beingDiscarded) {
                tmp[tmpIdx++] = hand[i];
            }
        }
        hand = tmp;
        cardIdx = tmpIdx;

        return discard;
    }

    public int cardsNeeded() {
        return hand.length - cardIdx;
    }

    @Override
    public boolean allSameSuit() {
        PokerCard.SUIT s = hand[0].getSuit();
        for(int i = 1; i < hand.length ; ++i) {
            if(hand[i].getSuit() != s) {
                return false;
            }
        }
        return true;
    }

    private final Comparator<Card> c = (o1, o2) -> o1.compare(o2);
    @Override
    public Comparator<Card> comparator() {
        return c;
    }

    @Override
    public boolean isStraight() {
        Arrays.sort(hand, comparator());
        for(int i = 0 ; i < (hand.length-1); ++i) {
            if(!hand[i].isNextValue(hand[i+1])) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for(int i = 0 ; i < hand.length; ++i) {
            sb.append(hand[i]);
            if(i != (hand.length -1)) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }


    private void printCards(Card[] c) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < c.length; ++i) {
            sb.append(c[i]);
            sb.append(", ");
        }
        sb.append('\n');
        System.out.println(sb.toString());
    }
}
