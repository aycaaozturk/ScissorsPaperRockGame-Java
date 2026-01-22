package jpp.gametheory.rockPaperScissors;

import jpp.gametheory.generic.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RPSReward implements IReward<RPSChoice> {
    //  Klasse, die eine Methode zur Berechnung des Profits bereitstellt.
    //  Nach den üblichen Regeln von Schere-Stein-Papier gewinnt Papier gegen Stein, Stein gegen Schere und Schere gegen Papier. Der Profit einer Runde für einen Spieler A berechnet sich wie folgt:
  //  Für jeden Spieler, gegen den A gewonnen hat, erhält er 2 Punkte.
   // Für jeden Spieler, gegen den A verloren hat, verliert er 1 Punkt.
  //  Bei einem Unentschieden zwischen zwei Spielern bekommt keiner von beiden einen Punkt.

    // GameRound metotlari: getPlayerChocies (Map dönüyor) key:players, value: choices  (tüm oyuncularin hamleleri )
//                      getChoice (C tipi dönüyor)         (sadece player in hamlesini dönüyor)
//                      getPlayers (Set dönüyor)           (tüm oyunculari dönüyor)
//                      getOtherPlayers (Set dönüyor)      (ben haric tüm oyuncular)
//                      toString (oyuncu listesi seklinde String dönüyor)

    //GameRound att: playerChocies (MAP türüunde)
//                key: players, value: hamleler (choice sanirim)


    @Override
    public int getReward(IPlayer<RPSChoice> player, IGameRound<RPSChoice> gameRound) {

        if(player ==null || gameRound==null){
            throw new NullPointerException("!");
        }
          //RPSchoice bir enum: S P R
          //player metotlari: getChoice, getStrategy


          RPSChoice playerAChocie = gameRound.getChoice(player);  //player A nin hamlesi

      //   HashMap<IPlayer<RPSChoice>, RPSChoice> OtherPlayersChocies = new HashMap<IPlayer<RPSChoice>, RPSChoice>();

//
        int reward=0;

        for(IPlayer<RPSChoice> e:  gameRound.getOtherPlayers(player)){

            RPSChoice playerOtherChocie = gameRound.getChoice(e);  //player B nin hamlesi

            if(playerAChocie==RPSChoice.PAPER && playerOtherChocie==RPSChoice.SCISSORS){
                reward+=-1;
            }
            else if(playerAChocie==RPSChoice.PAPER && playerOtherChocie==RPSChoice.ROCK){
                reward+=2;
            }
            else if(playerAChocie==RPSChoice.SCISSORS && playerOtherChocie==RPSChoice.ROCK){
                reward+=-1;
            }
            else if(playerAChocie==RPSChoice.SCISSORS && playerOtherChocie==RPSChoice.PAPER){
                reward+=+2;
            }
            else if(playerAChocie==RPSChoice.ROCK && playerOtherChocie==RPSChoice.SCISSORS){
                reward+=+2;
            }
            else if(playerAChocie==RPSChoice.ROCK && playerOtherChocie==RPSChoice.PAPER){
                reward+=-1;}

            }
          return reward;






        }
}
