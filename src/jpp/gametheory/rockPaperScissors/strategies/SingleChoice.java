package jpp.gametheory.rockPaperScissors.strategies;

import jpp.gametheory.generic.IGameRound;
import jpp.gametheory.generic.IPlayer;
import jpp.gametheory.generic.IStrategy;
import jpp.gametheory.rockPaperScissors.RPSChoice;

import java.util.List;

public class SingleChoice implements IStrategy<RPSChoice> {
    RPSChoice choice;

    public SingleChoice(RPSChoice choice) {
        this.choice=choice;

    }

    @Override
    public String name() {

       return "Always "+this.choice;
    }

    @Override
    public RPSChoice getChoice(IPlayer<RPSChoice> player, List<IGameRound<RPSChoice>> previousRounds) {

        if(player ==null || previousRounds==null){
            throw new NullPointerException("!");
        }
       //parametreler: choice tipinde player
        //             choice tipini kullanan GameRoundlardan olusan bir liste, adi: pre Rounds

        return this.choice;
    }

    @Override
    public String toString() {
      return name();
    }
}
