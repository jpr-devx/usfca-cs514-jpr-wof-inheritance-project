import java.util.*;

public class WheelOfFortuneAIGame extends WheelOfFortune{

    private int botGuessNum;
    private ArrayList<WheelOfFortunePlayer> players;
    private WheelOfFortunePlayer currentPlayer;
    private int playerSpot;

    public WheelOfFortuneAIGame(int guessesAllowed){
        super();
//        this.botGuessNum = 0;
        this.previousPhrases = new HashSet<>();
        this.randomPhrase();
        this.generateHiddenPhrase();
        this.previousGuesses = new HashSet<>();
        this.guessesAllowed = guessesAllowed;
        this.missesMade = 0;

        WheelOfFortunePlayer player = new WheelOfFortuneAISmartPlayer("Bot");
        this.players = new ArrayList<>();
        this.players.add(player);
        this.playerSpot = 0;
        this.currentPlayer = players.getFirst();



    }

    public WheelOfFortuneAIGame(WheelOfFortunePlayer player, int guessesAllowed){
        super();
        this.previousPhrases = new HashSet<>();
        this.randomPhrase();
        this.generateHiddenPhrase();
        this.previousGuesses = new HashSet<>();
        this.guessesAllowed = guessesAllowed;
        this.missesMade = 0;
        this.players = new ArrayList<>();
        this.players.add(player);
        this.playerSpot = 0;
        this.currentPlayer = players.getFirst();

    }


    public WheelOfFortuneAIGame(ArrayList<WheelOfFortunePlayer> players, int guessesAllowed){
        super();
        this.players = players;
        this.previousPhrases = new HashSet<>();
        this.previousGuesses = new HashSet<>();
        this.randomPhrase();
        this.generateHiddenPhrase();
        this.guessesAllowed = guessesAllowed;
        this.missesMade = 0;
        this.playerSpot = 0;
        this.currentPlayer = players.getFirst();


    }

    @Override
    protected GameRecord play() {

        GameRecord record = new GameRecord(currentPlayer.playerId());


        if (!this.previousPhrases.isEmpty()){
            while(this.previousPhrases.contains(this.phrase)){
                this.randomPhrase();
            }
            this.generateHiddenPhrase();
        } else {
            this.randomPhrase();
            this.generateHiddenPhrase();
        }

        int foundInt;
        char guess;

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

            if (guess == '0') {
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

    @Override
    protected char getGuess() {
        return currentPlayer.nextGuess(this.hiddenPhrase, this.previousGuesses);
    }




    public static void main(String[] args){

        WheelOfFortunePlayer player1 = new WheelOfFortuneAIEasyPlayer("John");
        WheelOfFortunePlayer player2 = new WheelOfFortuneAINaivePlayer("Lili");
        WheelOfFortunePlayer player3 = new WheelOfFortuneAISmartPlayer("Tania");

        ArrayList<WheelOfFortunePlayer> players = new ArrayList<>(Arrays.asList(player1, player2, player3));


//        WheelOfFortuneAIGame game = new WheelOfFortuneAIGame(new WheelOfFortuneAISmartPlayer("John"), 3);

        WheelOfFortuneAIGame game = new WheelOfFortuneAIGame(players, 3);

//        WheelOfFortuneAIGame game = new WheelOfFortuneAIGame(3);



        AllGamesRecord records = game.playAll();
        System.out.println(records + "\n");

        System.out.println("HighGame List by Player:");
        for (WheelOfFortunePlayer player : players){
            System.out.println(records.highGameList(player.playerId(), 3));
        }

        System.out.println("\nPlayer Averages:");
        for (WheelOfFortunePlayer player : players){
            System.out.println(player.playerId() + ": " + records.average(player.playerId()));
        }


    }
}
