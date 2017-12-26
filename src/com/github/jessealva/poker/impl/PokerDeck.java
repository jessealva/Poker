package com.github.jessealva.poker.impl;

import com.github.jessealva.poker.interfac.Card;
import com.github.jessealva.poker.interfac.Deck;

import java.util.Random;

/**
 * Created by jesusalva on 12/26/17.
 */
public class PokerDeck implements Deck{
    private static final int DECK_SIZE = 52;
    private final int SHUFFLE_SWAPS = 10000;
    private static Deck deck = new PokerDeck();
    private Card[] cards = new PokerCard[DECK_SIZE];
    private int deckIdx = cards.length -1;


    private PokerDeck() {
        PokerCard.SUIT[] s = PokerCard.SUIT.values();
        PokerCard.VALUE[] v = PokerCard.VALUE.values();
        //for(int i = 0 ; i < DECK_SIZE ; ++i) {
        int idx = 0;
            for(int j = 0 ; j < s.length; ++j) {
                for(int k = 0 ; k < v.length; ++k) {
                    //
                    cards[idx++] = new PokerCard(s[j], v[k]);
                }
            }
        //}
    }

    public static Deck instance() {
        return deck;
    }

    public void shuffle(){
        Random r = new Random(System.currentTimeMillis());
        for(int i = 0 ; i < SHUFFLE_SWAPS; ++i)  {
            //TODO maybe use the IntStream mechanism to test out java 8 features.
            int idx1 = r.nextInt(DECK_SIZE);
            int idx2 = r.nextInt(DECK_SIZE);
            Card tmp = cards[idx2];
            cards[idx2] = cards[idx1];
            cards[idx1] = tmp;
        }
    }

    public Card giveCard() {
        assert(deckIdx >=0);
        return cards[deckIdx--];
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append('{');
        for(int i = 0 ; i < cards.length ; ++i) {
            sb.append(cards[i]);
            sb.append(", ");
        }
        sb.append('}');
        return sb.toString();
    }
}
