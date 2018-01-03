import com.github.jessealva.poker.impl.PokerCard;
import com.github.jessealva.poker.impl.PokerHand;
import com.github.jessealva.poker.impl.PokerRules;
import com.github.jessealva.poker.interfac.Card;
import com.github.jessealva.poker.interfac.Hand;
import com.github.jessealva.poker.interfac.Rules;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by jesusalva on 1/2/18.
 */
public class PokerRulesTest {
    Rules rules;

    @Before
    public void setUp() throws Exception {
        rules = new PokerRules();
    }

    @After
    public void tearDown() throws Exception{

    }

    @Test
    public void testSFlushDropMethod() {
        Hand h = getHand(getCards("AC", "KC", "QC", "JC", "TC"));
        Card[] drops = rules.toDrop(h);
        Assert.assertTrue(drops.length == 0);
    }

    @Test
    public void test4KindDropMethod() {
        Hand h = getHand(getCards("AC", "AS", "AH", "AD", "TC"));
        Card[] drops = rules.toDrop(h);
        Assert.assertTrue(drops.length == 1);
        Assert.assertTrue(drops[0].equals(h.show()[4]));
    }

    @Test
    public void testFullHouseDropMethod() {
        Hand h = getHand(getCards("AC", "AD", "AS", "JC", "JS"));
        Card[] drops = rules.toDrop(h);
        Assert.assertTrue(drops.length == 0);
    }

    @Test
    public void testFlushDropMethod() {
        Hand h = getHand(new Card[]{ getCard("2C"), getCard("3C"), getCard("QC"),
                getCard("7C"), getCard("TC")});
        Card[] drops = rules.toDrop(h);
        Assert.assertTrue(drops.length == 0);
    }

    @Test
    public void testStraightDropMethod() {
        Hand h = getHand(getCards("2C", "3D", "4S", "5H", "6C"));
        Card[] drops = rules.toDrop(h);
        Assert.assertTrue(drops.length == 0);
    }
    @Test
    public void test3KindDropMethod() {
        Hand h = getHand(getCards("AC", "AD", "AS", "2C", "JS"));
        Card[] expectedDrops = getCards("2C", "JS");

        Card[] drops = rules.toDrop(h);
        Assert.assertTrue(drops.length == 2);

        Arrays.sort(expectedDrops, drops[0].getComparator());
        Arrays.sort(drops, drops[0].getComparator());
        for(int i =0 ; i < drops.length ; ++i) {
            Assert.assertTrue(expectedDrops[i].equals(drops[i]));
        }
    }

    @Test
    public void testTwoPairDropMethod() {
        Hand h = getHand(getCards("AC", "AD", "QS", "2C", "2S"));
        Card[] drops = rules.toDrop(h);
        Assert.assertTrue(drops.length == 1);
        Assert.assertTrue(drops[0].equals(h.show()[2]));
    }

    @Test
    public void testPairDropMethod() {
        Hand h = getHand(getCards("AC", "AS", "8D", "7H", "2D"));
        Card[] expectedDrops = getCards("8D", "7H", "2D");

        Card[] drops = rules.toDrop(h);
        Assert.assertTrue(drops.length == 3);

        Arrays.sort(expectedDrops, drops[0].getComparator());
        Arrays.sort(drops, drops[0].getComparator());
        for(int i =0 ; i < drops.length ; ++i) {
            Assert.assertTrue(expectedDrops[i].equals(drops[i]));
        }
    }

    @Test
    public void testHighCardDropMethod() {
        Hand h = getHand(getCards("AC", "2S", "5D", "7H", "3D"));
        Card[] expectedDrops = getCards("2S", "5D", "7H", "3D");

        Card[] drops = rules.toDrop(h);

        Arrays.sort(expectedDrops, drops[0].getComparator());
        Arrays.sort(drops, drops[0].getComparator());
        for(int i =0 ; i < drops.length ; ++i) {
            Assert.assertTrue(expectedDrops[i].equals(drops[i]));
        }
    }

    @Test
    public void testFindWinningHand() {
        Hand straightFlush = getHand(getCards("2H", "3H", "4H", "5H", "6H"));
        Hand fourOfAKind = getHand(getCards("8H","8S","8D","8C","TH"));
        Hand fullHouse = getHand(getCards("7D", "7H", "7S", "2D", "2S"));
        Hand flush = getHand(getCards("AD", "2D", "3D", "JD", "8D"));
        Hand straight = getHand(getCards("5D", "6C", "7S", "8H", "9H"));
        Hand threeOfAKind = getHand(getCards("5D", "5C", "5S", "TH", "3D"));
        Hand twoPair = getHand(getCards("AC", "AS", "QS", "QD", "5H"));
        Hand onePair = getHand(getCards("TH", "TS", "3D", "AH", "9D"));
        Hand highCard = getHand(getCards("AH", "JC", "TH", "9S", "8H"));

        Hand winner = rules.findWinningHand(new Hand[]{straightFlush, fullHouse, highCard});
        Assert.assertTrue(straightFlush.equals(winner));

        winner = rules.findWinningHand(new Hand[]{straightFlush, fourOfAKind});
        Assert.assertTrue(straightFlush.equals(winner));

        winner = rules.findWinningHand(new Hand[]{fourOfAKind, fullHouse});
        Assert.assertTrue(fourOfAKind.equals(winner));

        winner = rules.findWinningHand(new Hand[]{fullHouse, flush});
        Assert.assertTrue(fullHouse.equals(winner));

        winner = rules.findWinningHand(new Hand[]{flush, straight});
        Assert.assertTrue(flush.equals(winner));

        winner = rules.findWinningHand(new Hand[]{straight, threeOfAKind, highCard});
        Assert.assertTrue(straight.equals(winner));

        winner = rules.findWinningHand(new Hand[]{threeOfAKind, twoPair});
        Assert.assertTrue(threeOfAKind.equals(winner));

        winner = rules.findWinningHand(new Hand[]{twoPair, onePair});
        Assert.assertTrue(twoPair.equals(winner));

        winner = rules.findWinningHand(new Hand[]{onePair, highCard});
        Assert.assertTrue(onePair.equals(winner));
    }

    private Card[] getCards(String... values) {
        Card[] cards = values == null ? null: new Card[values.length];
        if(cards != null) {
            for(int i = 0 ; i < values.length ; ++i) {
                cards[i] = getCard(values[i]);
            }
        }
        return cards;
    }

    private Card getCard(String sv) {

        PokerCard.SUIT s = null;
        PokerCard.VALUE v = null;
        switch (sv.charAt(1)) {
            case 'C': s = PokerCard.SUIT.CLUB; break;
            case 'S': s = PokerCard.SUIT.SPADE; break;
            case 'D': s = PokerCard.SUIT.DIAMOND; break;
            case 'H': s = PokerCard.SUIT.HEART; break;
        }
        switch (sv.charAt(0)) {
            case '2': v = PokerCard.VALUE.TWO; break;
            case '3': v = PokerCard.VALUE.THREE; break;
            case '4': v = PokerCard.VALUE.FOUR; break;
            case '5': v = PokerCard.VALUE.FIVE; break;
            case '6': v = PokerCard.VALUE.SIX; break;
            case '7': v = PokerCard.VALUE.SEVEN; break;
            case '8': v = PokerCard.VALUE.EIGHT; break;
            case '9': v = PokerCard.VALUE.NINE; break;
            case 'T': v = PokerCard.VALUE.TEN; break;
            case 'J': v = PokerCard.VALUE.JACK; break;
            case 'Q': v = PokerCard.VALUE.QUEEN; break;
            case 'K': v = PokerCard.VALUE.KING; break;
            case 'A': v = PokerCard.VALUE.ACE; break;
        }
        return new PokerCard(s, v);
    }

    private Hand getHand(Card[] cards) {
        Hand h = new PokerHand(cards.length);
        for(Card c : cards) {
            h.receiveCard(c);
        }
        return h;
    }
}
