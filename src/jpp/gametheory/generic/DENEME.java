package jpp.gametheory.generic;

import jpp.gametheory.rockPaperScissors.RPSChoice;
import jpp.gametheory.rockPaperScissors.RPSReward;
import jpp.gametheory.rockPaperScissors.strategies.CircleChoice;
import jpp.gametheory.rockPaperScissors.strategies.MostCommon;
import jpp.gametheory.rockPaperScissors.strategies.MostSuccessful;
import jpp.gametheory.rockPaperScissors.strategies.SingleChoice;

import java.util.HashSet;
import java.util.Set;

public class DENEME {
    public static void main(String[] args) {
        RPSReward rpsReward = new RPSReward();
        CircleChoice circleStrategy = new CircleChoice();
        MostCommon mostCommon = new MostCommon(circleStrategy);
        MostSuccessful mostSuccessful = new MostSuccessful(circleStrategy, rpsReward);
        SingleChoice singleROCK = new SingleChoice(RPSChoice.ROCK);

        Player<RPSChoice> a = new Player("Ayca",mostCommon);
        Player<RPSChoice> t = new Player("Tolga", circleStrategy);
        Player<RPSChoice> d = new Player("Deniz", mostSuccessful);
        Player<RPSChoice> player4 = new Player("Player4",singleROCK );

        Set<IPlayer<RPSChoice>> playerSet = new HashSet<>();
        playerSet.add(t);
        playerSet.add(a);
        playerSet.add(d);
        playerSet.add(player4);

        Game game = new Game(playerSet, rpsReward);
        game.playRound();
    }
}
