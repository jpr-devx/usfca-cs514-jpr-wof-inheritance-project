import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

/**
 * WheelOfFortune Game that features bot(s) guessing WheelOfFortune phrases
 */
public class WheelOfFortuneAIGame extends WheelOfFortune{

    private int botGuessNum;
    private ArrayList<GuessingPlayer> players;
    private GuessingPlayer currentPlayer;
    private int playerSpot;

    /**
     * Creates WoFAI game with default bot
     * @param guessesAllowed number of incorrect guesses that the player can make before the game ends
     */
    public WheelOfFortuneAIGame(int guessesAllowed){
        super();
        this.previousPhrases = new HashSet<>();
        this.randomPhrase("WoFphrases.txt");
        this.generateHiddenPhrase();
        this.previousGuesses = new HashSet<>();
        this.guessesAllowed = guessesAllowed;
        this.missesMade = 0;

        GuessingPlayer player = new WheelOfFortuneAISmartPlayer("Bot");
        this.players = new ArrayList<>();
        this.players.add(player);
        this.playerSpot = 0;
        this.currentPlayer = players.getFirst();



    }

    /**
     * Creates WoF AI game with defined player and number of allowed guesses
     * @param player GuessingPlayer player with a username and score
     * @param guessesAllowed number of incorrect guesses that the player can make before the game ends
     */
    public WheelOfFortuneAIGame(GuessingPlayer player, int guessesAllowed){
        super();
        this.previousPhrases = new HashSet<>();
        this.randomPhrase("WoFphrases.txt");
        this.generateHiddenPhrase();
        this.previousGuesses = new HashSet<>();
        this.guessesAllowed = guessesAllowed;
        this.missesMade = 0;
        this.players = new ArrayList<>();
        this.players.add(player);
        this.playerSpot = 0;
        this.currentPlayer = players.getFirst();

    }

    /**
     * Creates WoF AI game with defined players stored within an ArrayList and number of allowed guesses
     * @param players ArrayList of GuessingPlayer players each with a username and score
     * @param guessesAllowed number of incorrect guesses that the player can make before the game ends
     */
    public WheelOfFortuneAIGame(ArrayList<GuessingPlayer> players, int guessesAllowed){
        super();
        this.players = players;
        this.previousPhrases = new HashSet<>();
        this.previousGuesses = new HashSet<>();
        this.randomPhrase("WoFphrases.txt");
        this.generateHiddenPhrase();
        this.guessesAllowed = guessesAllowed;
        this.missesMade = 0;
        this.playerSpot = 0;
        this.currentPlayer = players.getFirst();


    }

    /**
     * Executes WoF game and returns a record of the username and their score
     * @return record of finished game
     */
    @Override
    protected GameRecord play() {

        GameRecord record = new GameRecord(currentPlayer.playerId());


        if (!this.previousPhrases.isEmpty()){
            while(this.previousPhrases.contains(this.phrase)){
                this.randomPhrase("WoFphrases.txt");
            }
            this.generateHiddenPhrase();
        } else {
            this.randomPhrase("WoFphrases.txt");
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
            guess = currentPlayer.nextGuess(this.hiddenPhrase, this.previousGuesses);

            if (guess.equals("0")) {
                record.setScore(0);
                return record;
            }

            foundInt = this.processGuess(guess);
        } while (foundInt != -2);

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
     * Returns true/false on whether the next game should be played. Relevant data members are cleared and the next
     * player, if there is one, is assigned as the current player for the next game to be played.
     * @return true if next game should be played, false if the last player has played their last game
     */
    @Override
    protected boolean playNext() {
        if (this.previousPhrases.size() == 3) {
            this.previousPhrases.clear();
            if (this.currentPlayer == this.players.getLast()) return false;
            else {
                this.playerSpot++;
                this.currentPlayer = this.players.get(this.playerSpot);
            }

        }
        currentPlayer.reset();
        return true;
    }

    /**
     * Method calls upon player's nextGuess method to return a guess as a String
     * @return player's guess
     */
    @Override
    protected String getGuess() {
        return "" + currentPlayer.nextGuess(this.hiddenPhrase, this.previousGuesses);
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
        WheelOfFortuneAIGame that = (WheelOfFortuneAIGame) o;
        return botGuessNum == that.botGuessNum && playerSpot == that.playerSpot && Objects.equals(players, that.players) && Objects.equals(currentPlayer, that.currentPlayer);
    }

    /**
     * String representation of game
     * @return string representation of game and it's players
     */
    @Override
    public String toString() {
        return "WheelOfFortuneAIGame{" +
                "botGuessNum=" + botGuessNum +
                ", players=" + players +
                ", currentPlayer=" + currentPlayer +
                ", playerSpot=" + playerSpot +
                '}';
    }

    /**
     * Main method that runs the Wheel of Fortune game for bots to play. Players are added and passed into the game
     * object to play
     * @param args program arguments
     */
    public static void main(String[] args){

        GuessingPlayer player1 = new WheelOfFortuneAIEasyPlayer("John");
        GuessingPlayer player2 = new WheelOfFortuneAINaivePlayer("Lili");
        GuessingPlayer player3 = new WheelOfFortuneAISmartPlayer("Tania");

        ArrayList<GuessingPlayer> players = new ArrayList<>(Arrays.asList(player1, player2, player3));


//        WheelOfFortuneAIGame game = new WheelOfFortuneAIGame(new WheelOfFortuneAISmartPlayer("John"), 3);

        WheelOfFortuneAIGame game = new WheelOfFortuneAIGame(players, 3);

//        WheelOfFortuneAIGame game = new WheelOfFortuneAIGame(3);



        AllGamesRecord records = game.playAll();
        System.out.println(records + "\n");

        System.out.println("HighGame List by Player:");
        for (GuessingPlayer player : players){
            System.out.println(records.highGameList(player.playerId(), 3));
        }

        System.out.println("\nPlayer Averages:");
        for (GuessingPlayer player : players){
            System.out.println(player.playerId() + ": " + records.average(player.playerId()));
        }


    }
}
