import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Abstract guessing game class that is extended by all games that feature a user guessing a hidden phrase
 */
public abstract class GuessingGame extends Game{

    protected HashSet<String> previousPhrases;
    protected String phrase;
    protected StringBuilder hiddenPhrase;
    protected HashSet<String> previousGuesses;
    protected int guessesAllowed;
    protected int missesMade;
    protected String userName;

    protected HashSet<Character> presentLetters;

    protected Map<Character, int[]> matchMap;

    protected abstract String getGuess();

    /**
     * Method defines the phrase attribute of initialized WheelOfFortuneProject.WheelOfFortuneObject. All lines of
     * phrase.txt are read and a random line is obtained. Nothing is returned.
     *
     */
    protected void randomPhrase(String textFile){
        List<String> phraseList=null;
        // Get the phrase from a file of phrases
        try {
            phraseList = Files.readAllLines(Paths.get(textFile));
        } catch (IOException e) {
            System.out.println(e);
        }

        // Get a random phrase from the list
        Random rand = new Random();
        int r= rand.nextInt(3); // gets 0, 1, or 2
        this.phrase = phraseList.get(r);

        // Define hashSet of all letters present in gamephrase. This will be used to create a hashMap to store
        // current exact and partial matches
        this.presentLetters = this.phrase.chars()
                .mapToObj(e ->
                        Character.toLowerCase((char) e))
                .collect(Collectors.toCollection(HashSet::new));
        this.presentLetters.remove(' '); // remove spaces from map

        // Define map that each key corresponds to distinct character present in phrase and each value
        // corresponds to [#partialMatches, #exactMatches]
        this.matchMap = this.presentLetters.stream()
                .collect(Collectors.toMap(
                        ch -> ch,
                        intArr -> new int[2]
                ));

    }

    /**
     * Method defines the hiddenPhrase attribute of initialized WheelOfFortuneProject.WheelOfFortuneObject. Object
     * hiddenPhrase previously initialized during object construction is defined in this method as
     * the object's phrase attribute with all alphabetic chars replaced with an asterisk (*).
     * Nothing is returned.
     *
     */
    protected void generateHiddenPhrase(){
        // Code block for the construction of the hidden phrase based of the "phrase" string
        this.hiddenPhrase = new StringBuilder(this.phrase);
        for (int i = 0; i < this.phrase.length(); i++) {
            char ch = this.phrase.charAt(i);
            if (Character.isLetter(ch)) {
                this.hiddenPhrase.setCharAt(i,'*');
            } else {
                // If not a letter, the original char in "ch" is added to the "hidden" StringBuilder
                this.hiddenPhrase.setCharAt(i, ch);
            }
        }
    }


    /**
     * Takes in a validated guess that is either '0' or a lowercase letter and updates the hidden phrase if a new
     * correct guess is found. Helper method notifyGuess is called to return String that is printed in processGuess
     * to prompt user on game state (Current hidden phrase, guesses left, misses made, and information on past guess
     * @param guess single character guess
     * @return integer representing guess (-1 guess not found, 0 repeat guess, 1 guess found, -2 game exit)
     */
    protected abstract int processGuess(String guess);


}
