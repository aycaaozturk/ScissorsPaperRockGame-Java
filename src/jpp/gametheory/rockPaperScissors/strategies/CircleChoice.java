package jpp.gametheory.rockPaperScissors.strategies;

import jpp.gametheory.generic.IGameRound;
import jpp.gametheory.generic.IPlayer;
import jpp.gametheory.generic.IStrategy;
import jpp.gametheory.generic.Player;
import jpp.gametheory.rockPaperScissors.RPSChoice;

import java.util.List;


public class CircleChoice implements IStrategy<RPSChoice> {



    public CircleChoice() {

    }

    @Override
    public String name() {

        return "Circle Choice";
    }

    //    Eğer oyuncu önceki turda ROCK (taş) oynadıysa, bir sonraki turda PAPER (kağıt) oynamalıdır.
//    Eğer oyuncu önceki turda PAPER (kağıt) oynadıysa, bir sonraki turda SCISSORS (makas) oynamalıdır.
//    Eğer oyuncu önceki turda SCISSORS (makas) oynadıysa, bir sonraki turda ROCK (taş) oynamalıdır.
//    daha önce hic oynanmadiysa (liste bossa): ROCK oyna
    @Override

    public RPSChoice getChoice(IPlayer<RPSChoice> player, List<IGameRound<RPSChoice>> previousRounds) {
        if(player ==null || previousRounds==null){
            throw new NullPointerException("!");
        }

        if (previousRounds.size() == 0) {
            return RPSChoice.ROCK;

        }
        IPlayer<RPSChoice> currentPlayer = player;
        RPSChoice currentChoice = previousRounds.get(previousRounds.size() - 1).getChoice(currentPlayer);
        //                                 (   preRounddaki son GameRound )
        //currentChoice: currentPlayerin son oynadigi hamle

        //GameRound classinin playerChocie attributeu: MAP türüunde
        // key: player, value: hamleler
        if (currentChoice == RPSChoice.ROCK) {
            return RPSChoice.PAPER;
        } else if (currentChoice == RPSChoice.PAPER) {
            return RPSChoice.SCISSORS;
        } else {
            return RPSChoice.ROCK;
        }

    }

    @Override
    public String toString() {

        return name();
    }
}
