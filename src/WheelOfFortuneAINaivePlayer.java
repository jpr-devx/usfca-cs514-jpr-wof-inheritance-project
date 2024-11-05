import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WheelOfFortuneAINaivePlayer implements WheelOfFortunePlayer{

    protected String playerId;
    protected int botGuessNum;
    protected HashSet<Character> previousGuesses;


    //todo: does one game, now I have to handle playNext

    public WheelOfFortuneAINaivePlayer(String userName){
        this.playerId = userName;
        this.botGuessNum = 0;
    }

    @Override
    public char nextGuess(StringBuilder hiddenPhrase, HashSet<Character> previousGuesses) {

        char guess;
        Character[] lettersByFrequencyPre = {'e', 't', 'a', 'o', 'i', 'n', 's', 'h', 'r', 'd', 'l',
                'c', 'u', 'm', 'w', 'f', 'g', 'y', 'p', 'b', 'v', 'k', 'j', 'x', 'q', 'z'};
        List<Character> lettersByFrequency =
                new ArrayList<>(List.of(lettersByFrequencyPre));

        guess = lettersByFrequency.get(botGuessNum);
        botGuessNum++;
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
