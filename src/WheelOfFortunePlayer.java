import java.util.HashSet;

public interface WheelOfFortunePlayer {

    /**
     * get the next guess from the player
     * @return next player guess
     */
    public char nextGuess(StringBuilder hiddenPhrase, HashSet<Character> previousGuesses);

    /**
     *
     * @return ID for player
     */
    public String playerId();

    /**
     * reset the player to start a new game
     */
    void reset();

}
