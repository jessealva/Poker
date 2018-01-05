package com.github.jessealva.poker.interfac;

/**
 * Created by jesusalva on 1/2/18.
 */
public interface Rules {
    public Hand[] findWinningHand(Hand[] hands);
    public Card[] toDrop(Hand hand);
    public String getWinningHand(Hand hand);
}
