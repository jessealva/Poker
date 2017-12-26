package com.github.jessealva.poker.impl;

import com.github.jessealva.poker.interfac.Card;

/**
 * Created by jesusalva on 12/26/17.
 */
public class PokerCard implements Card{
    public enum SUIT {
        HEART, CLUB, DIAMOND, SPADE
    }

    public enum VALUE {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    private SUIT suit;
    private VALUE value;

    public PokerCard(SUIT s, VALUE v) {
        this.suit = s;
        this.value = v;
    }

    public String toString() {
        return "{suit: " + suit + ", value: " + value + " }";
    }
}
