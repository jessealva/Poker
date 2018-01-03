package com.github.jessealva.poker.impl;

import com.github.jessealva.poker.interfac.Card;

import java.util.Comparator;

/**
 * Created by jesusalva on 12/26/17.
 */
public class PokerCard implements Card{
    private SUIT suit;
    private VALUE value;

    public PokerCard(SUIT s, VALUE v) {
        this.suit = s;
        this.value = v;
    }

    @Override
    public SUIT getSuit() {
        return this.suit;
    }

    @Override
    public VALUE getValue() {
        return this.value;
    }

    @Override
    public int compare(Card c2) {
        return this.value.compare(c2.getValue());
    }

    @Override
    public boolean equals(Card c) {
        return this.value.equals(c.getValue()) && this.suit.equals(c.getSuit());
    }

    @Override
    public boolean isNextValue(Card c) {
        boolean b= false;
        switch(this.value.name()) {
            case "TWO":
                if(c.getValue().name().equals(VALUE.THREE.name())) { b = true; } break;
            case "THREE":
                if(c.getValue().name().equals(VALUE.FOUR.name())) { b = true; } break;
            case "FOUR":
                if(c.getValue().name().equals(VALUE.FIVE.name())) { b = true; } break;
            case "FIVE":
                if(c.getValue().name().equals(VALUE.SIX.name())) { b = true; } break;
            case "SIX":
                if(c.getValue().name().equals(VALUE.SEVEN.name())) { b = true; } break;
            case "SEVEN":
                if(c.getValue().name().equals(VALUE.EIGHT.name())) { b = true; } break;
            case "EIGHT":
                if(c.getValue().name().equals(VALUE.NINE.name())) { b = true; } break;
            case "NINE":
                if(c.getValue().name().equals(VALUE.TEN.name())) { b = true; } break;
            case "TEN":
                if(c.getValue().name().equals(VALUE.JACK.name())) { b = true; } break;
            case "JACK":
                if(c.getValue().name().equals(VALUE.QUEEN.name())) { b = true; } break;
            case "QUEEN":
                if(c.getValue().name().equals(VALUE.KING.name())) { b = true; } break;
            case "KING":
                if(c.getValue().name().equals(VALUE.ACE.name())) { b = true; } break;
            case "ACE":
            default:
                //Because nothing comes after ace
                b = false;
        }
        return b;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        switch (value) {
            case ACE: sb.append(" A "); break;
            case TWO: sb.append(" 2 "); break;
            case THREE: sb.append(" 3 "); break;
            case FOUR: sb.append(" 4 "); break;
            case FIVE: sb.append(" 5 "); break;
            case SIX: sb.append(" 6 "); break;
            case SEVEN: sb.append(" 7 "); break;
            case EIGHT: sb.append(" 8 "); break;
            case NINE: sb.append(" 9 "); break;
            case TEN: sb.append("10 "); break;
            case JACK: sb.append(" J "); break;
            case QUEEN: sb.append(" Q "); break;
            case KING: sb.append(" K "); break;
        }
        switch(suit) {
            case CLUB: sb.append("C"); break;
            case HEART: sb.append("H"); break;
            case SPADE: sb.append("S"); break;
            case DIAMOND: sb.append("D"); break;
        }
        return sb.toString();
    }

    @Override
    public Comparator<Card> getComparator() {
        return new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.compare(o2);
            }
        };
    }

    public enum SUIT {
        HEART, CLUB, DIAMOND, SPADE
    }

    public enum VALUE {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

        public int compare(VALUE v2) {
            int v = 0;
            String name1 = this.name();
            String name2 = v2.name();
            switch(name1) {
                case "TWO":
                    v = name2.equals(name1) ? 0 : -1; break;
                case "THREE":
                    v = name2.equals(TWO.name()) ? 1: -1; break;
                case "FOUR":
                    v = name2.equals(TWO.name()) || name2.equals(THREE.name()) ? 1 : -1; break;
                case "FIVE":
                    v = name2.equals(TWO.name()) || name2.equals(THREE.name()) || name2.equals(FOUR.name()) ? 1 : -1; break;
                case "SIX":
                    v = name2.equals(TWO.name()) || name2.equals(THREE.name()) || name2.equals(FOUR.name())
                            ||name2.equals(FIVE.name()) ? 1 : -1; break;
                case "SEVEN":
                    v = name2.equals(TWO.name()) || name2.equals(THREE.name()) || name2.equals(FOUR.name())
                            ||name2.equals(FIVE.name()) ||name2.equals(SIX.name()) ? 1 : -1; break;
                case "EIGHT":
                    v = name2.equals(TWO.name()) || name2.equals(THREE.name()) || name2.equals(FOUR.name())
                            ||name2.equals(FIVE.name()) || name2.equals(SIX.name()) || name2.equals(SEVEN.name()) ? 1 : -1; break;
                case "NINE":
                    v = name2.equals(TEN.name()) || name2.equals(JACK.name()) || name2.equals(QUEEN.name())
                            || name2.equals(KING.name()) || name2.equals(ACE.name()) ? -1 : 1; break;
                case "TEN":
                    v = name2.equals(JACK.name()) || name2.equals(QUEEN.name())
                            || name2.equals(KING.name()) || name2.equals(ACE.name()) ? -1 : 1; break;
                case "JACK":
                    v = name2.equals(QUEEN.name()) || name2.equals(KING.name()) || name2.equals(ACE.name()) ? -1 : 1; break;
                case "QUEEN":
                    v = name2.equals(KING.name()) || name2.equals(ACE.name()) ? -1 : 1; break;
                case "KING":
                    v = name2.equals(ACE.name()) ? -1 : 1; break;
                case "ACE":
                    v = name2.equals(ACE.name()) ? 0 : 1; break;
            }
            if(name1.equals(name2)) {
                v = 0;
            }
            return v;
        }
    }
}
