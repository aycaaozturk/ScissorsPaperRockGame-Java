package jpp.gametheory.rockPaperScissors.strategies;

import jpp.gametheory.generic.IGameRound;
import jpp.gametheory.generic.IPlayer;
import jpp.gametheory.generic.IReward;
import jpp.gametheory.generic.IStrategy;
import jpp.gametheory.rockPaperScissors.RPSChoice;
import jpp.gametheory.rockPaperScissors.RPSReward;

import java.util.*;

public class MostSuccessful implements IStrategy<RPSChoice> {

    IStrategy<RPSChoice> alternate;
    IReward<RPSChoice> reward;

    public MostSuccessful(IStrategy<RPSChoice> alternate, IReward<RPSChoice> reward) {
        if (alternate == null || reward == null) {
            throw new NullPointerException("!");
        }


        this.alternate = alternate;
        this.reward = reward;
    }

    @Override
    public String name() {

        return "Most Successful Choice (Alternate: " + this.alternate + ")";
    }

    // public int getReward(IPlayer<RPSChoice> player, IGameRound<RPSChoice> gameRound)
    //getReward: player'in o gameRound'da aldigi puani veriyor

    // GameRound metotlari: getPlayerChocies (Map dönüyor) key:players, value: choices  (tüm oyuncularin hamleleri )
    //                      getChoice (C tipi dönüyor)         (sadece player in hamlesini dönüyor)
    //                      getPlayers (Set dönüyor)           (tüm oyunculari dönüyor)
    //                      getOtherPlayers (Set dönüyor)      (ben haric tüm oyuncular)
//                          toString (oyuncu listesi seklinde String dönüyor)

    //GameRound att: playerChocies (MAP türüunde)
    //               key: players, value: hamleler (choice sanirim)


    @Override
    public RPSChoice getChoice(IPlayer<RPSChoice> player, List<IGameRound<RPSChoice>> previousRounds) {
        if(player ==null || previousRounds==null){
            throw new NullPointerException("!");
        }

        Map<RPSChoice, Integer> pointsByChoice = new HashMap<>();

        RPSReward rew = new RPSReward();
        for (IGameRound<RPSChoice> round: previousRounds) {
            for (IPlayer<RPSChoice> p:  round.getPlayers()){
                RPSChoice c = round.getChoice(p);
                int r = rew.getReward(p,round);
                if (pointsByChoice.containsKey(c)) {
                    pointsByChoice.put(c,r + pointsByChoice.get(c));
                } else {
                    pointsByChoice.put(c,r);
                }

            }
        }

        List<Map.Entry<RPSChoice,Integer>> LISTE = new ArrayList<>(pointsByChoice.entrySet());


        class EntryComparator implements Comparator<Map.Entry<RPSChoice, Integer>> {

            @Override
            public int compare(Map.Entry<RPSChoice, Integer> o1, Map.Entry<RPSChoice, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        }


        Collections.sort(LISTE, new EntryComparator() );

        if (LISTE.size() == 0 ) {
            return alternate.getChoice(player,previousRounds);
        }

        if (LISTE.size()>1 ) {
            if (LISTE.get(0).getValue() == LISTE.get(1).getValue()){
                return alternate.getChoice(player,previousRounds);
            }
        }


        return LISTE.get(0).getKey();
    }


    @Override
    public String toString() {
        return name();
    }
}
