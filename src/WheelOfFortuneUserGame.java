import java.util.*;

public class WheelOfFortuneUserGame extends WheelOfFortune{



    public WheelOfFortuneUserGame(String userName, int guessesAllowed){
        super();
        this.previousPhrases = new HashSet<>();
        this.randomPhrase();
        this.generateHiddenPhrase();
        this.previousGuesses = new HashSet<>();
        this.guessesAllowed = guessesAllowed;
        this.missesMade = 0;
        this.userName = userName;
    }

    @Override
    public char getGuess() {

        Scanner in = new Scanner(System.in);
        String input;
        do {
            System.out.print("Please guess a single letter or type quit to exit game: ");
            input = in.next();
            System.out.println();
            if (input.length() == 1 && Character.isAlphabetic(input.charAt(0))) return Character.toLowerCase(input.charAt(0));
        } while (!input.equalsIgnoreCase("quit"));

        return '0';
    }

    @Override
    protected GameRecord play() {
        GameRecord record = new GameRecord(this.userName);
        // note: The way that random phrase is created is that a random string is retrieved and thats returned. So
        //  without changing the return type, or adding a data member or something I can't think of anything else
        //  but this...

        this.guessesAllowed = 3;

        if (!this.previousPhrases.isEmpty()){
            while(this.previousPhrases.contains(this.phrase)){
                this.randomPhrase();
            }
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
            guess = this.getGuess();

            if (guess == '0') {
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



    public static void main(String[] args){

        WheelOfFortuneUserGame game = new WheelOfFortuneUserGame("John", 3);

        AllGamesRecord records = game.playAll();
        System.out.println(records);
//        System.out.println(record.highGameList(2));
//        System.out.println("Average: " + record.average());



    }


}
