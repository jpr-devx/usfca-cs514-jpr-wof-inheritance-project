import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class WheelOfFortune{

    private String phrase;
    private StringBuilder hiddenPhrase;
    private HashSet<Character> previousGuesses;
    private final int guessesAllowed;
    private int missesMade;

    private HashSet<Character> presentLetters;
    private Map<Character, int[]> matchMap;

    /**
     * WheelOfFortuneProject.WheelOfFortuneObject Constructor - Defines phrase and hiddenPhrase attributes upon object
     * initialization. Initialized previousGuesses hashSet to track unique guesses made
     * throughout game execution
     *
     */
    public WheelOfFortune(int guessesAllowed) {

        this.phrase = "";
        this.hiddenPhrase = new StringBuilder();
        this.previousGuesses = new HashSet<>();
        this.randomPhrase();
        this.generateHiddenPhrase();
        this.guessesAllowed = guessesAllowed;
        this.missesMade = 0;

        String introduction =
                "================================================================================" +
                        "=============%nWelcome to the Wheel of Fortune!%nThe goal of this game is to " +
                        "guess the complete phrase that has their letters hidden.%n%nThere are a few " +
                        "simple rules:%n     1) All guesses, besides 'quit' must contain only one letter " +
                        "at a time%n     2) Uppercase and lowercase letters are treated as the same%n    " +
                        " 3) If an incorrect guess is made, you lose one guess%n     4) If a " +
                        "repeated guess is made, you are not penalized%n%nIf you make %s unique wrong " +
                        "guesses, you lose the game. Below is the hidden phrase. Good luck!%n" +
                        "========================================================================" +
                        "=====================%n%s%n";
        System.out.printf(introduction, this.guessesAllowed, this.hiddenPhrase);
    }

    /**
     * Method defines the phrase attribute of initialized WheelOfFortuneProject.WheelOfFortuneObject. All lines of
     * phrase.txt are read and a random line is obtained. Nothing is returned.
     *
     */
    public void randomPhrase(){
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
    public void generateHiddenPhrase(){
        // Code block for the construction of the hidden phrase based of the "phrase" string
        for (int i = 0; i < this.phrase.length(); i++) {
            char ch = this.phrase.charAt(i);
            if (Character.isLetter(ch)) {
                this.hiddenPhrase.append('*');
            } else {
                // If not a letter, the original char in "ch" is added to the "hidden" StringBuilder
                this.hiddenPhrase.append(ch);
            }
        }
    }

    /**
     * Returns guess char if a valid guess. If "quit" is entered, '0' is returned to initiate
     * game exit sequence. Otherwise, user is prompted for a valid guess until a valid one is returned
     *
     * @return Validated single lower-case character or '0' for quit game-exit sequence
     */
    public char getGuess(){
        Scanner in = new Scanner(System.in);
        String input;
        do {
            System.out.print("Please guess a single letter or type quit to exit game: ");
            input = in.next();
            if (input.length() == 1 && Character.isAlphabetic(input.charAt(0))) return Character.toLowerCase(input.charAt(0));
        } while (!input.equalsIgnoreCase("quit"));

        return '0';
    }

    /**
     * Takes in a validated guess that is either '0' or a lowercase letter and updates the hidden phrase if a new
     * correct guess is found. Helper method notifyGuess is called to return String that is printed in processGuess
     * to prompt user on game state (Current hidden phrase, guesses left, misses made, and information on past guess
     * @param guess single character guess
     * @return integer representing guess (-1 guess not found, 0 repeat guess, 1 guess found, -2 game exit)
     */
    public int processGuess(char guess){

            boolean found = false;
            int foundInt = -1;

            for (int index = 0; index < this.phrase.length(); index++){
                if(Character.toLowerCase(this.phrase.charAt(index)) == guess) {
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
            System.out.println(this.notifyGuess(foundInt));   // Print game message
            return foundInt;
        }

    /**
     * Helper method that provides string to prompt user of game state. Used in print statement within processGuess
     * method
     * @param foundInt integer representing guess (-1 guess not found, 0 repeat guess, 1 guess found, -2 game exit)
     * @return gameNotification
     */
    private String notifyGuess(int foundInt){
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
        else if (foundInt == 2) notification = "Game has exited successfully.\n";
        else notification =
                    "Nothing was found | Guesses left: " + guessesLeft + " | Misses made: " + this.missesMade +
                            "\nPhrase: " + this.hiddenPhrase + "\nPrevious guesses: " + this.previousGuesses + "\n";
        return notification;
    }


    /**
     * Method executes WheelOfFortune object methods on initialized object to play WheelOfFortune
     * game composing of a while loop containing prompts to user on state of game, guesses left
     * and previous guesses made. Logic and associated statements prompt user for an additional guess
     * with no change in guesses remaining if a duplicate guess, correct or incorrect.
     *
     * @param args main method args
     */
    public static void main(String [] args){

        int guessesLeft = 5;
        WheelOfFortune game = new WheelOfFortune(guessesLeft);
        int result;

        do {
            char guess = game.getGuess();
            result = game.processGuess(guess);
        } while (result != -2);

    }

}