package com.github.jessealva.poker.impl;

import com.github.jessealva.poker.interfac.Card;
import com.github.jessealva.poker.interfac.Dealer;
import com.github.jessealva.poker.interfac.Deck;
import com.github.jessealva.poker.interfac.Hand;

/**
 * Created by jesusalva on 12/26/17.
 */
public class PokerDealer implements Dealer {
    private static int HAND_SIZE = 5;
    private Hand[] hands;
    private Deck deck;

    public PokerDealer() {
        this.deck = PokerDeck.instance();
        System.out.println(deck);
    }

    public void initGame(int hands) {
        this.hands = new Hand[hands];
        for(int i = 0 ; i < hands ; ++i) {
            this.hands[i] = new PokerHand(HAND_SIZE);
        }
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

            printHands();
        }
    }

    public void printHands() {
        for(int i = 0 ; i < hands.length ; ++i) {
            System.out.println(hands[i]);
        }
    }

    public void askForDiscards() {
        if (hands != null) {
            for(int i = 0 ; i < hands.length ; ++i) {
                Card[] discards = hands[i].discard();
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
    }

    public Hand determineWinner() {
        System.out.println("Determining Winner:");
        Hand winner = hands[0];
        printHands();
        return winner;
    }

    public static void main(String[] args) {
        Dealer d = new PokerDealer();
        d.initGame(5);
        d.dealCards();
        d.askForDiscards();
        d.dealLastRound();
        Hand winner = d.determineWinner();
        System.out.println("WInner is: " + winner);
    }
}
