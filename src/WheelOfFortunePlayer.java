import java.util.HashSet;

public interface WheelOfFortunePlayer {

    /**
     * get the next guess from the player
     * @return next player guess
     */
    char nextGuess(StringBuilder hiddenPhrase, HashSet<Character> previousGuesses);

    /**
     *
     * @return ID for player
     */
    String playerId();

    /**
     * reset the player to start a new game
     */
    void reset();

}
