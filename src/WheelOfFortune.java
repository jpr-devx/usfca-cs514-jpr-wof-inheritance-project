import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public abstract class WheelOfFortune extends Game{

    protected HashSet<String> previousPhrases;
    protected String phrase;
    protected StringBuilder hiddenPhrase;
    protected HashSet<Character> previousGuesses;
    protected int guessesAllowed;
    protected int missesMade;
    protected String userName;

    protected HashSet<Character> presentLetters;

    protected Map<Character, int[]> matchMap;

    protected abstract char getGuess();

    /**
     * Method defines the phrase attribute of initialized WheelOfFortuneProject.WheelOfFortuneObject. All lines of
     * phrase.txt are read and a random line is obtained. Nothing is returned.
     *
     */
    protected void randomPhrase(){
        List<String> phraseList=null;
        // Get the phrase from a file of phrases
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
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
    protected int processGuess(char guess){

        boolean found = false;
        int foundInt = -1;

        for (int index = 0; index < this.phrase.length(); index++){
            if(Character.toLowerCase(this.phrase.charAt(index)) == guess && !this.previousGuesses.contains(Character.toLowerCase(this.phrase.charAt(index)))) {
                found = true;
                this.hiddenPhrase.setCharAt(index, this.phrase.charAt(index));
                int[] newArr = this.matchMap.get(guess);
                newArr[1]++;    // increment exact match by one, since its only one char guesses, there can only be
                // exact matches
                this.matchMap.replace(guess, newArr); // replace current array for char with incremented array
            }
        }

        if (this.previousGuesses.contains(guess)) foundInt = 0;
        else if (!found) {
            this.missesMade++;
            if (this.missesMade == this.guessesAllowed) foundInt = -2;
        } else if (guess == '0' || this.phrase.contentEquals(this.hiddenPhrase)) foundInt = -2; //quit
            // sequence
        else foundInt = 1;

        this.previousGuesses.add(guess);                // Add guess to hashSet of previous guesses

        String notification;

        int guessesLeft = this.guessesAllowed - this.missesMade;
        if (foundInt == 0) notification =
                "Guess has already been made | Guesses left: " + guessesLeft + " | Misses made: " + this.missesMade +
                        "\nPhrase: " + this.hiddenPhrase + "\nPrevious guesses: " + this.previousGuesses + "\n";
        else if (foundInt == 1) notification =
                "You guessed right! | Guesses left: " + guessesLeft + " | Misses made: " + this.missesMade +
                        "\nPhrase: " + this.hiddenPhrase + "\nPrevious guesses: " + this.previousGuesses + "\n";
        else if (this.phrase.contentEquals(this.hiddenPhrase)) notification =
                "You have successfully guessed the phrase. Congratulations!\nPhrase: " + this.hiddenPhrase +
                        "\n\nGame has " +
                        "exited successfully.\n";
        else if (foundInt == -2) notification = "Game has exited successfully.\n";
        else notification =
                    "Nothing was found | Guesses left: " + guessesLeft + " | Misses made: " + this.missesMade +
                            "\nPhrase: " + this.hiddenPhrase + "\nPrevious guesses: " + this.previousGuesses + "\n";

        System.out.println(notification);
        return foundInt;
    }



}
