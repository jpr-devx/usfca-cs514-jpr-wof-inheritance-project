import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


/**
 * Mastermind game that features a human user interacting with a CLI to make guesses on a Mastermind game
 */
public class Mastermind extends GuessingGame{

    private final HashSet<Character> VALIDCOLORS = new HashSet<>(Arrays.asList('R','G','B','Y','O','P'));
    private final char EXACTMATCH = (char) 9679;
    private final char PARTIALMATCH = 'X';
    private final String CORRECTANSWER = "" + EXACTMATCH + EXACTMATCH + EXACTMATCH + EXACTMATCH;
    private int exactMatchCount;
    private int partialMatchCount;


    /**
     * Constructs Mastermind game and reads from MastermindLines.txt for a phrase to use for the game
     */
    public Mastermind(){
        this.randomPhrase("MastermindLines.txt");
        this.generateHiddenPhrase();

        this.previousPhrases = new HashSet<>();
        this.missesMade = 0;
        this.guessesAllowed = 10;
        this.exactMatchCount = 0;
        this.partialMatchCount = 0;

    }

    /**
     * Looks at hidden phrase that has partial and exact matches marked and updates partial and exact match counter
     * data members
     */
    protected void updateCount() {

        this.partialMatchCount = 0;
        this.exactMatchCount = 0;

        for (int i = 0; i < this.hiddenPhrase.length(); i++){
            char element = this.hiddenPhrase.charAt(i);
            if (element == this.PARTIALMATCH) this.partialMatchCount++;
            if (element == this.EXACTMATCH) this.exactMatchCount++;
        }
    }

    /**
     * Helper method that returns true if all letters in guess are a valid color choice
     * @param guess User guess that composes of a sequence of four colors
     * @return true if guess contains only valid colors
     */
    private boolean isIn(String guess){
        if (guess.length() > 4) return false;
        for (int i = 0; i < guess.length(); i++){
            char letter = guess.charAt(i);
            if (!this.VALIDCOLORS.contains(letter)) return false;
        }
        return true;
    }

    /**
     * Helper method that returns true if a letter is present in the random phrase. This is used for exact/partial
     * match counting
     * @param letter of guess
     * @return true if letter is presen in guess
     */
    private boolean isIn(char letter){
        boolean in = false;
        for (int i = 0; i < this.phrase.length(); i++){
            if (letter == this.phrase.charAt(i)) return true;
        }
        return in;
    }

    /**
     * Helper method to rehide hidden string that was originally revealed to show locations of exact and partial
     * matches. This method is used in the printing of the message for the user to notify them of how many guesses
     * they have left, in addition to the number of partial/exact matches and instruction on valid guesses
     * @return hiddenphrase string represenation with all characters being the '*' char
     */
    private String hide(){
        String result = "";
        for (int i = 0; i < this.hiddenPhrase.length(); i++) result += "*";
        return result;
    }


    /**
     * Prompts user of game states and takes in a guess for further processing
     * @return String representation of user guess
     */
    @Override
    protected String getGuess() {
        String guess;

        Scanner scan = new Scanner(System.in);

        this.updateCount();

        String message =
                hide() + "%" + this.hiddenPhrase.length() + "s\t" + "(Guesses left: " + (this.guessesAllowed - this.missesMade) +
                        " | Exact matches: " + this.exactMatchCount + " | Partial matches: " + this.partialMatchCount + ") " +
                        "Please enter a " + this.phrase.length() +
                        "-character guess with " +
                        "colors " + this.VALIDCOLORS +
                        ": ";

        do {
            System.out.printf(message, "");
            guess = scan.nextLine();
        } while (!isIn(guess) || guess.length() != this.phrase.length());

        return guess;
    }

    /**
     * A user's guess is counted for exact and partial matches
     * @param guess single character guess
     * @return -1 if the user has successfully guessed the phrase or has run out of guesses, 0 otherwise
     */
    @Override
    protected int processGuess(String guess) {
        this.missesMade++;

        for (int i = 0; i < guess.length(); i++) {
            char letter = guess.charAt(i);
            if (isIn(letter)) {
                if (guess.charAt(i) == this.phrase.charAt(i)) this.hiddenPhrase.setCharAt(i, EXACTMATCH);
                else this.hiddenPhrase.setCharAt(i, PARTIALMATCH);
            }
            else this.hiddenPhrase.setCharAt(i, '*');
        }

        if (guess.equals(this.phrase)) return -1;
        else {
            if (this.missesMade == this.guessesAllowed) return -1;
            else return 0;
        }
    }

    /**
     * Counts exact matches as 2 points to the users score and 1 for partial matches
     * @return users counted up score
     */
    private int count(){
        int score = 0;
        for(int i = 0; i < this.hiddenPhrase.length(); i++){
            if (this.hiddenPhrase.charAt(i) == (char) 9679) score += 2;
            if (this.hiddenPhrase.charAt(i) == 'X') score++;
        }
        return score;

    }

    /**
     * Plays Mastermind game for user to interact with CLI to guess a hidden sequence of characters
     * @return Record of finished game with user's playerId and score
     */
    @Override
    protected GameRecord play() {

        String colorList = "\n\tR: Red\n\tG: Green\n\tB: Blue\n\tP: Purple\n\tY: Yellow\n\tO: Orange\n";

        String introMessage = "\nWelcome to the game of Mastermind!\nIn this game you have four colors you must guess" +
                " in the correct order. The colors you can select from are:" + colorList + "There are no duplicate " +
                "colors. \n\nWhen you correctly guess a color at its exact location, a " + EXACTMATCH + " appears. " +
                "\nIf you do not guess the exact position correctly, but that color is somewhere else in the list of " +
                "colors, a " + PARTIALMATCH + " appears in the place where you guessed that character.\n\nGood " +
                "luck!\n==========================================================================================\n";

        System.out.println(introMessage);

        if (!this.previousPhrases.isEmpty()){
            while(this.previousPhrases.contains(this.phrase)){
                this.randomPhrase("MastermindLines.txt");
            }
            this.generateHiddenPhrase();
        }


        int result = 0;
        do {
            String guess = this.getGuess();
            result = processGuess(guess);
        } while(result != -1);

        if (CORRECTANSWER.contentEquals(this.hiddenPhrase)) System.out.println(
                "Congratulations! You've " +
                        "won the " +
                        "game!");
        else System.out.println("Unfortunately you did not win. Better luck next time!");

        GameRecord record = new GameRecord("John");
        record.setScore(count());

        this.previousPhrases.add(this.phrase);
        this.missesMade = 0;

        return record;

    }

    /**
     * Method returns true/false if next game should be played
     * @return whether next game should be played
     */
    @Override
    protected boolean playNext() {
        return this.previousPhrases.size() != 3;
    }

    /**
     * Main method that runs the Mastermind game for user to play
     * @param args program arguments
     */
    public static void main(String[] args){

        Mastermind game = new Mastermind();

        AllGamesRecord records = game.playAll();

        System.out.println("\n\nSummary:");

        System.out.println(records);
        System.out.println("High Game List: " + records.highGameList(2));

    }

}
