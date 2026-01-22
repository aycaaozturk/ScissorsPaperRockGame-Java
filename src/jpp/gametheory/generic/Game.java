package jpp.gametheory.generic;

import jpp.gametheory.rockPaperScissors.RPSChoice;
import jpp.gametheory.rockPaperScissors.RPSReward;

import java.util.*;


public class Game<C extends IChoice> {

    Set<IPlayer<C>> players;
    IReward<C> reward;
    LinkedList<IGameRound<C>> GespielteRunden = new LinkedList<>();

    Map<IPlayer<C>, Integer> playerProfits = new HashMap<>();
    int numberOfRounds = 0;


    public Game(Set<IPlayer<C>> players, IReward<C> reward) {

        if (players == null || reward == null) {
            throw new NullPointerException("Parameter: null!");
        } else if (players.isEmpty() == true) {
            throw new IllegalArgumentException("Number of players= 0, not valid!");
        } else {
            this.players = players;
            this.reward = reward;

        }
    }

    public Set<IPlayer<C>> getPlayers() {
        return players;
    }

    public IGameRound<C> playRound() { //bir round simule ediyor,
        // bir round: playerA -> hamlesi

        List<IPlayer<C>> PlayerList = new ArrayList<>(players);  //players setini listeye dönüstürdüm
        HashMap<IPlayer<C>, C> PlayerMap = new HashMap<>();

        // public C getChoice(List<IGameRound<C>> previousRounds)

        for (int i = 0; i < PlayerList.size(); i++) {
            IPlayer<C> pl = PlayerList.get(i);


            PlayerMap.put(pl, pl.getChoice(getPlayedRounds()));   //key: player, value: choice olacak sekilde map
            //cunku GameRound olustururken constructor icin lazim
        }

        IGameRound<C> newGameRound = new GameRound<>(PlayerMap);

        GespielteRunden.add(newGameRound);//oynanmis oyunlara bunu ekle
        numberOfRounds = numberOfRounds + 1;
        for (IPlayer<C> player : players) {                                    //her Roundda, her oyuncunun aldigi puani
            int profit = playerProfits.getOrDefault(player, 0);      //playerProfit adli Map'e aktar
            //key: players, value: aldiklari puan toplami
            profit += reward.getReward(player, newGameRound);                  //her playRound() yapildiginda puanlar güncelleniyor
            playerProfits.put(player, profit);
        }

        return newGameRound;
    }

    public void playNRounds(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        } else {
            for (int i = 0; i < n; i++) {
                playRound();
            }
        }
    }

    public Optional<IGameRound<C>> undoRound() {  //son roundi siliyor ve bu son silinen roundu return ediyor

        if (GespielteRunden.isEmpty()) {
            return Optional.empty();
        }
        IGameRound<C> lastRound = GespielteRunden.removeLast();


        for (IPlayer<C> player : players) {
            int profit = playerProfits.getOrDefault(player, 0);
            profit -= reward.getReward(player, lastRound);
            playerProfits.put(player, profit);
        }

        return Optional.of(lastRound);
    }

    public void undoNRounds(int n) {

        if (n < 1) {
            throw new IllegalArgumentException();
        } else {
            for (int i = 0; i < n; i++) {
                undoRound();
            }

        }
    }

    public List<IGameRound<C>> getPlayedRounds() {
        if (GespielteRunden == null) {
            throw new NullPointerException("!");
        }
        return Collections.unmodifiableList(GespielteRunden);
    }

    //public int getReward(IPlayer<RPSChoice> player, IGameRound<RPSChoice> gameRound)


    public int getPlayerProfit(IPlayer<C> player) { //oyuncu icin toplam puani hesaplamak istiyor

        if (player == null) {
            throw new NullPointerException("!");
        }

        if (players.contains(player) == false) {
            throw new IllegalArgumentException();
        } else {
            return playerProfits.getOrDefault(player, 0);
        }

    }

    public Optional<IPlayer<C>> getBestPlayer() {


        // Map<IPlayer<C>, Integer> playerProfits = new HashMap<>();
        List<Map.Entry<IPlayer<C>, Integer>> PlayerProfitList = new ArrayList<>(playerProfits.entrySet());
        //oyunculari listeye dönüstürdük

        class EntryComparator implements Comparator<Map.Entry<IPlayer<C>, Integer>> {

            @Override
            public int compare(Map.Entry<IPlayer<C>, Integer> o1, Map.Entry<IPlayer<C>, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        }


        Collections.sort(PlayerProfitList, new EntryComparator() );
        if (PlayerProfitList.size()>1 ) {
            if (PlayerProfitList.get(0).getValue() == PlayerProfitList.get(1).getValue()){
                return  Optional.empty();
            } else {
                return Optional.of(PlayerProfitList.get(0).getKey());
            }
        }

        return  Optional.empty();

    }


    public String toString() {
        List<IPlayer<C>> PlayersList = new ArrayList<>(players);
        List<Map.Entry<IPlayer<C>, Integer>> PlayerProfits = new ArrayList<>();

        for (int i = 0; i < PlayersList.size(); i++) {
            IPlayer<C> currentPlayer = PlayersList.get(i);
            int currentReward = getPlayerProfit(currentPlayer);
            AbstractMap.SimpleEntry<IPlayer<C>, Integer> playerRewardEntry = new AbstractMap.SimpleEntry<>(currentPlayer, currentReward);
            PlayerProfits.add(playerRewardEntry);
        }
        Collections.sort(PlayerProfits, new Comparator<Map.Entry<IPlayer<C>, Integer>>() {
            @Override
            public int compare(Map.Entry<IPlayer<C>, Integer> entry1, Map.Entry<IPlayer<C>, Integer> entry2) {
                if (entry2.getValue() == entry1.getValue()) {
                    return entry1.getKey().getName().compareTo(entry2.getKey().getName());
                } else {
                    return entry2.getValue().compareTo(entry1.getValue());
                }




            }
        });
        String numberOfPlayedRounds = String.valueOf(numberOfRounds);
        StringBuilder yazici = new StringBuilder();
        yazici.append("Spiel nach "+ numberOfPlayedRounds+ " Runden:\n");
        yazici.append("Profit : Spieler");

        for(int i=0; i<PlayerProfits.size(); i++){
            yazici.append("\n")
                    .append(PlayerProfits.get(i).getValue())
                    .append(" : ")
                    .append(PlayerProfits.get(i).getKey().toString());
        }
      return yazici.toString();
    }
}

//LIFO: linked list?