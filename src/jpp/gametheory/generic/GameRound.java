package jpp.gametheory.generic;

import java.util.*;

public class GameRound<C extends IChoice> implements IGameRound<C> {

    final Map<IPlayer<C>,C> playerChoices;   //key: player, value: hamle(choice)

    public GameRound(Map<IPlayer<C>, C> playerChoices) {
        if(playerChoices==null){
            throw new NullPointerException();
        }

        if(playerChoices.isEmpty()==true){
            throw new IllegalArgumentException("!");
        }
        this.playerChoices=playerChoices;

    }

    @Override
    public Map<IPlayer<C>, C> getPlayerChoices() {

        return Collections.unmodifiableMap(playerChoices);  //burada immutable yaptim ins dogru olmustur
       // player -> choice seklinde map dönüyor
    }





    @Override
    public C getChoice(IPlayer<C> player) {
        if(player==null){
            throw new NullPointerException("!");
        }

        if (playerChoices.containsKey(player)==false){
            throw new IllegalArgumentException();
        }

        return playerChoices.get(player);
    }




    @Override
    public Set<IPlayer<C>> getPlayers() {
        Set<IPlayer<C>> playerKümesi = new HashSet<>(playerChoices.keySet());
        return playerKümesi;



    }

    @Override
    public Set<IPlayer<C>> getOtherPlayers(IPlayer<C> player) {
        Set<IPlayer<C>> playerKümesi = new HashSet<>(playerChoices.keySet());

        if(player==null){
            throw new NullPointerException("!");
        }

        if(playerKümesi.contains(player)==false){
            throw new IllegalArgumentException();
        }

       else {
            playerKümesi.remove(player);
            return playerKümesi;
        }


    }

    @Override
    public String toString() {
       List<IPlayer<C>> PlayerList = new ArrayList<IPlayer<C>>(playerChoices.keySet());//anahtarlari Liste yaptik
                                                                                       //liste elemanlari: player tipinde



       Collections.sort(PlayerList); //isim listesi artik alfabetik
        //compareTo metodunu calistiriyor arkada, yani isimleri baz alarak karsilastirdi
        //bunu yapmak icin player classinin comparable infterface ini implement etmesi gerekiyor
        //compareTo metodunu yazdik

       //PlayerNames üyelerinin hepsi birer anahtar, for döngüsüyle hepsinin choice'ine ulasabilirm (mapteki valuelar)
       //bu valuelardan liste yapilabilir


       String a="(";
       String b="";
       String c=")";
       for(int s=0;s<PlayerList.size()-1; s++){
           b=b+ PlayerList.get(s).getName() +" -> "+ playerChoices.get(PlayerList.get(s)).name()+", ";
                                                 // (          value (tipi: choice)     ).name()
       }

       b=b+PlayerList.get(PlayerList.size()-1).getName() + " -> " + playerChoices.get(PlayerList.get(PlayerList.size()-1)).name();


       return a+b+c;

    }
}
