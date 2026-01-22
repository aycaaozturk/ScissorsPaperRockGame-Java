package jpp.gametheory.rockPaperScissors.strategies;

import jpp.gametheory.generic.IGameRound;
import jpp.gametheory.generic.IPlayer;
import jpp.gametheory.generic.IStrategy;
import jpp.gametheory.rockPaperScissors.RPSChoice;

import java.util.*;


public class MostCommon implements IStrategy<RPSChoice> {
    IStrategy<RPSChoice> alternate;

    //alternate: RPSChoice türünü kullanan, IStrategy türünde bir variable
    //Istrategy: interface

    public MostCommon(IStrategy<RPSChoice> alternate) {
        if(alternate==null){
            throw new NullPointerException("!");
        }
        this.alternate= alternate;
    }

    @Override
    public String name() {

        return "Most Common Choice (Alternate: "+ this.alternate+ ")";
    }


    // GameRound metotlari: getPlayerChocies (Map dönüyor) key:players, value: choices
//                      getChoice (C tipi dönüyor)
//                      getPlayers (Set dönüyor)
//                      getOtherPlayers (Set dönüyor)
//                      toString (oyuncu listesi seklinde String dönüyor)

    //GameRound att: playerChocies (MAP türüunde)
//                key: players, value: hamleler (choice sanirim)

    //parametreler: choice tipinde player
    //             choice tipini kullanan GameRoundlardan olusan bir liste, adi: pre Rounds

    @Override
    public RPSChoice getChoice(IPlayer<RPSChoice> player, List<IGameRound<RPSChoice>> previousRounds) {
        if(player==null || previousRounds==null){
            throw new NullPointerException("!");
        }

        List<RPSChoice> ChoiceListesi = new ArrayList<>();
        for (IGameRound<RPSChoice> round: previousRounds) {
            for (RPSChoice choice:  round.getPlayerChoices().values()){
                ChoiceListesi.add(choice);
            }
        }
        //choice listesi yaptik
        HashMap<RPSChoice, Integer> numberOfChoices = new HashMap<>();  //key: choice, value: sayisi

        for(int a=0; a<ChoiceListesi.size(); a++){
            RPSChoice element = ChoiceListesi.get(a);
            if(numberOfChoices.containsKey(element)){
               numberOfChoices.put(element, numberOfChoices.get(element)+1);

            }
            else {
               numberOfChoices.put(element, 1);
        }}
        RPSChoice mostCommon = null;
        int maxNumberOfChoices=0;

       // List<Entry<String, Integer>> entryList = new ArrayList<>(frequencyMap.entrySet());
       //burada map.entry arayüzü kullandik: <key,value> seklinde 2 deger tutar
        // entrySet(): map'i key-value ciftleri birlikte duracak sekilde set'e dönüstürür

       //yani yukarida map -> set'e dönüstü
       // <key,value> seklinde elemanlar tutan listeye bunu attik

       List<Map.Entry<RPSChoice,Integer>> LISTE = new ArrayList<>(numberOfChoices.entrySet());


        class EntryComparator implements Comparator<Map.Entry<RPSChoice, Integer>> {

            @Override
            public int compare(Map.Entry<RPSChoice, Integer> o1, Map.Entry<RPSChoice, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        }


        Collections.sort(LISTE, new EntryComparator() );

        if(LISTE.size()==0){
            return alternate.getChoice(player,previousRounds);
        }


        if (LISTE.size()>1 ) {
            if (LISTE.get(0).getValue() == LISTE.get(1).getValue()){
                return alternate.getChoice(player,previousRounds);
            }
        }


        return LISTE.get(0).getKey();

//        for (int i = 0; i < LISTE.size(); i++) {
//            Map.Entry<RPSChoice, Integer> entry = LISTE.get(i);
//            if (entry.getValue() > maxNumberOfChoices) {
//                maxNumberOfChoices = entry.getValue();
//                mostCommon = entry.getKey();
//            }
//        }
//        return mostCommon;

    }

    @Override
    public String toString() {
      return name();
    }
}
