import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

/**
 * Wheel of Fortune player that simply guesses a random letter
 */
public class WheelOfFortuneAIEasyPlayer implements GuessingPlayer {

    protected String playerId;

    public WheelOfFortuneAIEasyPlayer(String userName){
        this.playerId = userName;
    }

    /**
     * Method called by games getGuess method
     * @param hiddenPhrase game's hiddenphrase
     * @param previousGuesses previous guesses that player has made
     * @return String representation of player's guess
     */
    @Override
    public String nextGuess(StringBuilder hiddenPhrase, HashSet<String> previousGuesses) {

        Random rand = new Random();
        return  "" + (char) ('a' + rand.nextInt(0 ,25));

    }

    /**
     * Method is not used in this class
     */
    @Override
    public void reset() {
    }

    /**
     * Compares two players by their player ID
     * @param o other object
     * @return true if players are the same instance or if their playerIDs are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WheelOfFortuneAIEasyPlayer that = (WheelOfFortuneAIEasyPlayer) o;
        return Objects.equals(playerId, that.playerId);
    }



    /**
     * String representation of player
     * @return String representation of player
     */
    @Override
    public String toString() {
        return "WheelOfFortuneAIEasyPlayer{" +
                "playerId='" + playerId + '\'' +
                '}';
    }

    /**
     * Returns player's ID
     * @return playerID
     */
    @Override
    public String playerId() {
        return this.playerId;
    }

}
