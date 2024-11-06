import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * WheelOfFortuneAI player guessing by most commonly used characters in descending order
 */
public class WheelOfFortuneAINaivePlayer implements GuessingPlayer {

    protected String playerId;
    protected int botGuessNum;
    protected HashSet<Character> previousGuesses;

    /**
     * Constructs player with a guess number to determine which letter to guess and the player's username
     * @param userName name of user
     */
    public WheelOfFortuneAINaivePlayer(String userName){
        this.playerId = userName;
        this.botGuessNum = 0;
    }

    /**
     * Method called by games getGuess method
     * @param hiddenPhrase game's hiddenphrase
     * @param previousGuesses previous guesses that player has made
     * @return String representation of player's guess
     */
    @Override
    public String nextGuess(StringBuilder hiddenPhrase, HashSet<String> previousGuesses) {

        String guess;
        Character[] lettersByFrequencyPre = {'e', 't', 'a', 'o', 'i', 'n', 's', 'h', 'r', 'd', 'l',
                'c', 'u', 'm', 'w', 'f', 'g', 'y', 'p', 'b', 'v', 'k', 'j', 'x', 'q', 'z'};
        List<Character> lettersByFrequency =
                new ArrayList<>(List.of(lettersByFrequencyPre));

        guess = "" + lettersByFrequency.get(botGuessNum);
        botGuessNum++;
        return guess;

    }

    /**
     * Reset method is not used in this class
     */
    @Override
    public void reset() {
    }

    /**
     * Returns player's ID
     * @return player's ID
     */
    @Override
    public String playerId() {
        return this.playerId;
    }

    /**
     * String representation of player
     * @return String representation of player
     */
    @Override
    public String toString() {
        return "WheelOfFortuneAINaivePlayer{" +
                "playerId='" + playerId + '\'' +
                ", botGuessNum=" + botGuessNum +
                ", previousGuesses=" + previousGuesses +
                '}';
    }

    /**
     * Compares with another object to see if it is the same instance, have equal data members or if they are different
     * @param o other object
     * @return true if objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WheelOfFortuneAINaivePlayer that = (WheelOfFortuneAINaivePlayer) o;
        return botGuessNum == that.botGuessNum && Objects.equals(playerId, that.playerId) && Objects.equals(previousGuesses, that.previousGuesses);
    }

}