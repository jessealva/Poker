package com.github.jessealva.poker;

import com.github.jessealva.poker.impl.PokerDealer;
import com.github.jessealva.poker.impl.PokerDeck;
import com.github.jessealva.poker.impl.PokerRules;
import com.github.jessealva.poker.interfac.Dealer;
import com.github.jessealva.poker.interfac.Hand;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

/**
 * Created by jesusalva on 1/2/18.
 */
//@SpringBootApplication
@Configuration
public class PokerApp {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        Dealer d = ctx.getBean(PokerDealer.class);
        d.initGame(5);
        d.dealCards();
        d.askForDiscards();
        d.dealLastRound();
        Hand winner = d.determineWinner();
        System.out.println("WInner is: " + winner + " with hand + " + d.winningHand(winner));
        //SpringApplication.run(PokerApp.class, args);
    }
}
