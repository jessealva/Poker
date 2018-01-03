package com.github.jessealva.poker.impl;

import com.github.jessealva.poker.interfac.Card;
import com.github.jessealva.poker.interfac.Dealer;
import com.github.jessealva.poker.interfac.Deck;
import com.github.jessealva.poker.interfac.Hand;
import com.github.jessealva.poker.interfac.Rules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * Created by jesusalva on 12/26/17.
 */
public class PokerDealer implements Dealer {
    private static int HAND_SIZE = 5;
    private Hand[] hands;
    private Deck deck;
    private Rules rules;

    public PokerDealer(Deck deck, Rules rules) {
        this.rules = rules;
        this.deck = deck;
    }

    public void initGame(int hands) {
        this.hands = new Hand[hands];
        for(int i = 0 ; i < hands ; ++i) {
            this.hands[i] = new PokerHand(HAND_SIZE);
        }
        shuffle();
    }

    public void shuffle() {
        deck.shuffle();
    }

    public void dealCards() {
        if (hands != null) {
            for(int h = 0 ; h < HAND_SIZE ; ++h) {
                for (int i = 0 ; i < hands.length ; ++i) {
                    hands[i].receiveCard(deck.giveCard());
                }
            }
            System.out.println("Dealt cards:");
            printHands();
        }
    }


    @Override
    public String winningHand(Hand hand) {
        return rules.getWinningHand(hand);
    }

    public void printHands() {
        for(int i = 0 ; i < hands.length ; ++i) {
            System.out.println(hands[i] + " - " + winningHand(hands[i]));
        }
    }

    public void askForDiscards() {

        rules.findWinningHand(hands);
        if (hands != null) {
            for(int i = 0 ; i < hands.length ; ++i) {
                Card[] discards = hands[i].discard(rules);
            }
        }
    }

    public void dealLastRound() {
        for(int i = 0 ; i < hands.length; ++i) {
            int needed = hands[i].cardsNeeded();
            for(int j = 0 ; j < needed; ++j){
                hands[i].receiveCard(deck.giveCard());
            }
        }
        System.out.println("Dealing last round: ");
        printHands();
    }

    public Hand determineWinner() {
        System.out.println("Determining Winner:");

        Hand winner = rules.findWinningHand(hands);//hands[0];
        printHands();
        return winner;
    }

    public static void main(String[] args) {
        Dealer d = new PokerDealer(PokerDeck.instance(), new PokerRules());
        d.initGame(5);
        d.dealCards();
        d.askForDiscards();
        d.dealLastRound();
        Hand winner = d.determineWinner();
        System.out.println("WInner is: " + winner);
    }
}
