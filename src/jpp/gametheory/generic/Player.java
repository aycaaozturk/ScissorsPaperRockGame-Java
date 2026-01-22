package jpp.gametheory.generic;

import java.util.List;

//  C türü IChoice arayüzünü implemente eden herhangi bir sınıf olabilir.
//  extends anahtar kelimesi, C'nin IChoice arayüzünü uygulayan veya ondan türeyen bir sınıf olması gerektiğini belirtir.

//Player<C extends IChoice>: Player sınıfı, IChoice arayüzünü uygulayan bir tür (C) ile çalışabilir.
//implements IPlayer<C>: Player sınıfı, IPlayer<C> arayüzündeki metotları uygulamalıdır.


public class Player<C extends IChoice> implements IPlayer<C> {


    private final String name;
    private IStrategy<C> strategy;

    public Player(String name, IStrategy<C> strategy) {
        if(name==null){
            throw new NullPointerException();
        }
        else if(strategy==null){
            throw new NullPointerException();
        }

      this.name=name;
      this.strategy=strategy;


    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public IStrategy<C> getStrategy() {

        return strategy ;
    }

    @Override
    public C getChoice(List<IGameRound<C>> previousRounds) {
        if(previousRounds==null){
            throw new NullPointerException("!");
        }
        return strategy.getChoice( this, previousRounds);
    }

    @Override
    public int compareTo(IPlayer<C> o) {

        if(o==null){
            throw new NullPointerException();
        }
       return this.name.compareTo(o.getName());

    }

    @Override
    public boolean equals(Object o) {
        if(o== null){
            return false;
        }
        if(o instanceof Player == false){
            return false;
        }


        Player<C> newObject = (Player<C>)o;
        if(this.name.equals(newObject.getName())){
            return true;
        }
        else{ return false;}

    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
        //playerlari hashset ve hashmap e eklemek icin yazdik
    }

    @Override
    public String toString() {
      return this.name+""+ "("+ this.strategy+ ")";
    }
}
