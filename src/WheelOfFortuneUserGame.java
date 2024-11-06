import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

/**
 * WheelOfFortune game that features a human user interacting with a CLI to make guesses on a WheelOfFortune game
 */
public class WheelOfFortuneUserGame extends WheelOfFortune{

    /**
     * WoFUserGame constructor that initializes game with a player name and guesses allowed for game
     * @param userName name of player
     * @param guessesAllowed number of guesses that a user can make
     */
    public WheelOfFortuneUserGame(String userName, int guessesAllowed){
        this.previousPhrases = new HashSet<>();
        this.randomPhrase("WoFphrases.txt");
        this.generateHiddenPhrase();
        this.previousGuesses = new HashSet<>();
        this.guessesAllowed = guessesAllowed;
        this.missesMade = 0;
        this.userName = userName;
    }

    /**
     * Returns single character as a string that is validated
     * @return validated letter or "quit" for game exit sequence
     */
    @Override
    protected String getGuess() {

        Scanner in = new Scanner(System.in);
        String input;
        do {
            System.out.print("Please guess a single letter or type quit to exit game: ");
            input = in.next();
            System.out.println();
            if (input.length() == 1 && Character.isAlphabetic(input.charAt(0))) return input;
        } while (!input.equalsIgnoreCase("quit"));

        return "0";
    }

    /**
     * Plays game for the human user that will interact with CLI.
     * @return record of game with the users name and their score
     */
    @Override
    protected GameRecord play() {
        GameRecord record = new GameRecord(this.userName);
        this.guessesAllowed = 3;

        if (!this.previousPhrases.isEmpty()){
            while(this.previousPhrases.contains(this.phrase)){
                this.randomPhrase("WoFphrases.txt");
            }
            this.generateHiddenPhrase();
        }

        int foundInt;
        String guess;

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


        do {
            guess = this.getGuess();

            if (guess.equals("0")) {
                record.setScore(0);
                return record;
            }

            foundInt = this.processGuess(guess);
        } while (foundInt != -2);

        // todo: getting the score after game end. A point is given for each occurrence of a correct letter
        int score = 0;
        for (char key : this.matchMap.keySet()) {
            score += this.matchMap.get(key)[1];
        }

        record.setScore(score);
        this.previousPhrases.add(this.phrase);
        this.previousGuesses.clear();
        this.matchMap.clear();
        this.missesMade = 0;
        return record;

    }

    /**
     * Returns true/false on whether the next game should be played. Relevant data members are cleared for the next
     * game to be played.
     * @return true if next game should be played, false if the player has played their last game
     */
    @Override
    protected boolean playNext() {
        String startMenuMessage = "\nPlease select from the following options:\n1. Continue to play this next game\n2. Exit\nEnter here: ";
        Scanner scan = new Scanner(System.in);
        String input;

        if (this.previousPhrases.size() == 3) return false;

        do {
            System.out.print(startMenuMessage);
            input = scan.next();
        } while (!input.equals("1") && !input.equals("2"));

        return input.equals("1");
    }

    /**
     * Returns string representation of game
     * @return string representation of game
     */
    @Override
    public String toString() {
        return "WheelOfFortuneUserGame{" +
                "userName='" + userName + '\'' +
                ", hiddenPhrase=" + hiddenPhrase +
                ", missesMade=" + missesMade +
                ", gamesRecords=" + gamesRecords +
                '}';
    }

    /**
     * Compares game with other object for equality
     * @param o other object
     * @return true if same instance or data members are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WheelOfFortuneUserGame that = (WheelOfFortuneUserGame) o;
        return userName.equals(that.userName) && hiddenPhrase.toString().contentEquals(that.hiddenPhrase) && missesMade == that.missesMade && gamesRecords.equals(that.gamesRecords);
    }

    /**
     * Main method that runs the Wheel of Fortune game for user to play
     * @param args program arguments
     */
    public static void main(String[] args){

        WheelOfFortuneUserGame game = new WheelOfFortuneUserGame("John", 3);

        AllGamesRecord records = game.playAll();
        System.out.println(records);
//        System.out.println(record.highGameList(2));
//        System.out.println("Average: " + record.average());



    }


}
