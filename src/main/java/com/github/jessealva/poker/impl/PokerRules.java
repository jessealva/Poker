package com.github.jessealva.poker.impl;

import com.github.jessealva.poker.interfac.Card;
import com.github.jessealva.poker.interfac.Hand;
import com.github.jessealva.poker.interfac.Rules;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jesusalva on 1/2/18.
 */
public class PokerRules implements Rules {

    private Map<PokerCard.VALUE, Integer> getValueCounts(Hand hand) {
        Card[] cards = hand.show();
        Map<PokerCard.VALUE, Integer> tmp = new HashMap<>();
        for(int i = 0 ; i < cards.length; ++i) {
            PokerCard.VALUE key = cards[i].getValue();
            if(tmp.containsKey(key)) {
                tmp.put(key, tmp.get(key) + 1);
            } else {
                tmp.putIfAbsent(key, 1);
            }
        }
        return tmp;
    }

    private boolean isFlush(Hand hand) {
        return hand.allSameSuit();
    }

    @Override

    /**
     * Hand types:
     *
     *
     * straight flush
     * four of a kind
     * full house
     * flush
     * straight
     * three of a kind
     * two pair
     * one pair
     * high card
     *
     */
    public Hand findWinningHand(Hand[] hands) {
        //WinningHands[] wh = new WinningHands[hands.length];
        Map<Hand, WinningHands> results = new HashMap<>(hands.length);
        for(int i = 0 ; i < hands.length ; ++i) {
            results.put(hands[i], WinningHands.getHighestHand(hands[i]));
        }

        WinningHands highest = Collections.max(results.values(), WinningHands.getComparator());

        List<Hand> wHands = new ArrayList<>(hands.length);
        for(Hand key : results.keySet()) {
            if(results.get(key).equals(highest)) {
                wHands.add(key);
            }
        }
        return wHands.get(0);
    }

    @Override
    public Card[] toDrop(Hand hand) {
        WinningHands wh = WinningHands.getHighestHand(hand);
        Card[] cards = hand.show();
        Card[] drop = null;
        Map<PokerCard.VALUE, Integer> helper = getValueCounts(hand);
        switch (wh) {
            case SFLUSH: drop = new Card[0]; break; //don't drop anything.
            case FOUR_OF_A_KIND:
                PokerCard.VALUE bad = null;
                for(PokerCard.VALUE key : helper.keySet()){
                    if(helper.get(key) == 1) {
                        bad = key;
                    }
                }
                for(int i = 0 ; i < cards.length; ++i) {
                    if(cards[i].getValue().equals(bad)) {
                        drop = new Card[]{cards[i]};
                        break;
                    }
                }
                break;
            case FULL_HOUSE: drop = new Card[0]; break; //don't drop anything.
            case FLUSH: drop = new Card[0]; break; //don't drop anything.
            case STRAIGHT: drop = new Card[0]; break; //don't drop anything.
            case THREE_OF_A_KIND:
                PokerCard.VALUE good = null;
                for(PokerCard.VALUE key : helper.keySet()) {
                    if(helper.get(key) == 3){ good = key; }
                }
                drop = new Card[2];
                int dropI = 0;
                for(Card c : cards) {
                    if(!c.getValue().equals(good)) { drop[dropI++] = c; }
                }
                break;
            case TWO_PAIR:
                bad = null;
                drop = new Card[1];
                for(PokerCard.VALUE key : helper.keySet()) {
                    if (helper.get(key) == 1) { bad = key; }
                }
                for(Card c : cards) {
                    if (c.getValue().equals(bad)) {
                        drop[0] = c;
                        break;
                    }
                }
                break;
            case ONE_PAIR:
                good = null;
                drop = new Card[3];
                for(PokerCard.VALUE key : helper.keySet()) {
                    if (helper.get(key) == 2) { good = key; break; }
                }
                dropI = 0;
                for(Card c : cards) {
                    if (!c.getValue().equals(good)) {
                        drop[dropI++] = c;
                    }
                }
                break;
            case HIGH_CARD:
                Card highest = WinningHands.getHighCard(hand);
                drop = new Card[cards.length-1];
                int dropIdx = 0;
                for(int i = 0 ; i < cards.length; ++i) {
                    if(!highest.equals(cards[i])) {
                        drop[dropIdx++] = cards[i];
                    }
                }
                break;
        }
        return drop;
    }

    @Override
    public String getWinningHand(Hand hand) {
        return WinningHands.getHighestHand(hand).name();
    }

    enum WinningHands {
        SFLUSH, FOUR_OF_A_KIND, FULL_HOUSE, FLUSH, STRAIGHT, THREE_OF_A_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD;

        public static Comparator<WinningHands> getComparator() {
            return new Comparator<WinningHands>() {
                @Override
                public int compare(WinningHands o1, WinningHands o2) {
                    String n1 = o1.name();
                    String n2 = o2.name();
                    if(o1.name().equals(n2)) {
                        return 0;
                    }
                    int v = 0;
                    switch(o1.name()) {
                        case "SFLUSH":
                            v = n2.equals(n1) ? 0 : 1; break;
                        case "FOUR_OF_A_KIND":
                            v = n2.equals(SFLUSH.name()) ? -1 : 1; break;
                        case "FULL_HOUSE":
                            v = n2.equals(SFLUSH.name()) || n2.equals(FOUR_OF_A_KIND.name()) ? -1 : 1; break;
                        case "FLUSH":
                            v = n2.equals(SFLUSH.name()) || n2.equals(FOUR_OF_A_KIND.name())
                                    || n2.equals(FULL_HOUSE.name()) ? -1 : 1; break;
                        case "STRAIGHT":
                            v = n2.equals(SFLUSH.name()) || n2.equals(FOUR_OF_A_KIND.name())
                                    || n2.equals(FULL_HOUSE.name()) || n2.equals(FLUSH.name()) ? -1 : 1; break;
                        case "THREE_OF_A_KIND":
                            v = n2.equals(HIGH_CARD.name()) || n2.equals(ONE_PAIR.name())
                                    || n2.equals(TWO_PAIR.name()) ? 1 : -1; break;
                        case "TWO_PAIR":
                            v = n2.equals(HIGH_CARD.name()) || n2.equals(ONE_PAIR.name()) ? 1 : -1; break;
                        case "ONE_PAIR":
                            v = n2.equals(HIGH_CARD.name()) ? 1 : -1; break;
                        case "HIGH_CARD":
                        default:
                            v = n2.equals(HIGH_CARD.name()) ? 0 : -1;
                            break;
                    }
                    return v;
                }
            };
        }

        public static WinningHands getHighestHand(Hand h) {
            if(isStraightFlush(h)) {
                return SFLUSH;
            } else if(isFourOfKind(h)) {
                return FOUR_OF_A_KIND;
            } else if(isFullHouse(h)) {
                return FULL_HOUSE;
            } else if(isFlush(h)) {
                return FLUSH;
            } else if(isStraight(h)) {
                return STRAIGHT;
            } else if(isThreeOfKind(h)) {
                return THREE_OF_A_KIND;
            } else if(isTwoPair(h)) {
                return TWO_PAIR;
            } else if(isOnePair(h)) {
                return ONE_PAIR;
            } else {
                return HIGH_CARD;
            }
        }

        private static Map<PokerCard.VALUE, Integer> getCardMap(Hand h) {
            Card[] cards = h.show();
            Map<PokerCard.VALUE, Integer> tmp = new HashMap<>(cards.length);
            for(int i =0 ; i < cards.length ; ++i) {
                Integer v = tmp.putIfAbsent(cards[i].getValue(), 1);
                if(v != null) {
                    tmp.put(cards[i].getValue(), v+1);
                }
            }
            return tmp;
        }

        public static boolean isStraightFlush(Hand h) {
            if(h.allSameSuit() && h.isStraight()) {
                return true;
            }
            return false;
        }

        public static boolean isFourOfKind(Hand h) {
            Map<PokerCard.VALUE, Integer> tmp = getCardMap(h);
            return Collections.max(tmp.values()) == 4;
        }

        public static boolean isFullHouse(Hand h) {
            Map<PokerCard.VALUE, Integer> tmp = getCardMap(h);
            int keyCount = tmp.keySet().size();
            if(keyCount == 2) {
                Integer[] values = tmp.values().toArray(new Integer[]{});
                if(values[0] == 3 || values[0] == 2) {
                    return true;
                }
            }

            return false;
        }

        public static boolean isFlush(Hand h) {
            return h.allSameSuit();
        }

        public static boolean isStraight(Hand h) {
            return h.isStraight();
        }

        public static boolean isThreeOfKind(Hand h) {
            Map<PokerCard.VALUE, Integer> tmp = getCardMap(h);
            return Collections.max(tmp.values()) == 3;
        }

        public static boolean isTwoPair(Hand h) {
            int pairs = getPairCount(getCardMap(h));
            return pairs == 2;
        }

        public static boolean isOnePair(Hand h) {
            int pairs = getPairCount(getCardMap(h));
            return pairs == 1;
        }

        private static int getPairCount(Map<PokerCard.VALUE, Integer> tmp) {
            int pairs = 0 ;
            for(PokerCard.VALUE key : tmp.keySet()) {
                if(tmp.get(key) == 2) {
                    pairs++;
                }
            }
            return pairs;
        }

        public static Card getHighCard(Hand h) {
            Card[] cards = h.show();
            Card high = cards[0];
            for(int i = 1 ; i < cards.length ; ++i) {
                if(cards[i].compare(high) > 0) {
                    high = cards[i];
                }
            }
            return high;
        }
    }
}
