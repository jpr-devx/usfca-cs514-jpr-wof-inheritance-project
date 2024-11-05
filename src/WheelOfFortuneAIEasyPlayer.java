import java.io.ByteArrayInputStream;
import java.util.*;

public class WheelOfFortuneAIEasyPlayer implements WheelOfFortunePlayer{

    protected String playerId;
    protected int botGuessNum;
    protected HashSet<Character> previousGuesses;


    //todo: does one game, now I have to handle playNext

    public WheelOfFortuneAIEasyPlayer(String userName){
        this.playerId = userName;
    }

    @Override
    public char nextGuess(StringBuilder hiddenPhrase, HashSet<Character> previousGuesses) {

        Random rand = new Random();
        char guess = (char) ('a' + rand.nextInt(0 ,25));

        return guess;

    }


    @Override
    public void reset() {
        //todo: need for implementing this depends on strategy. This may not be required to do anything if
        // getGuess is simple enough
    }

    @Override
    public String playerId() {
        return this.playerId;
    }

}
