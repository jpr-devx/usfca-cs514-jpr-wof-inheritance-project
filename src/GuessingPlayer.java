import java.util.HashSet;

/**
 * Player interface for a guessing game
 */
public interface GuessingPlayer {

    /**
     * get the next guess from the player
     * @return next player guess
     */
    String nextGuess(StringBuilder hiddenPhrase, HashSet<String> previousGuesses);

    /**
     * Returns ID of player
     * @return ID for player
     */
    String playerId();

    /**
     * reset the player to start a new game
     */
    void reset();

}
