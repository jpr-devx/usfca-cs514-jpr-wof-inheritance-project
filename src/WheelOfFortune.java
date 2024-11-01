import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class WheelOfFortune {
//public abstract class WheelOfFortune extends Game {
//    public AllGamesRecord playAll(){
//
//    }
//
//    public GameRecord play(){
//
//    }
//
//    public boolean playNext(){
//
//    }

        String phrase;
        StringBuilder hiddenPhrase;
        HashSet<Character> previousGuesses;

        /**
         * WheelOfFortuneProject.WheelOfFortuneObject Constructor - Defines phrase and hiddenPhrase attributes upon object
         * initialization. Initialized previousGuesses hashSet to track unique guesses made
         * throughout game execution
         *
         */
        public WheelOfFortune() {

            this.phrase = "";
            this.hiddenPhrase = new StringBuilder();
            this.previousGuesses = new HashSet<>();
            this.randomPhrase();
            this.generateHiddenPhrase();

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
            System.out.printf(introduction, 3, this.hiddenPhrase);
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
         * game exit sequence. If a guess containing more than one char, or the one char is not a
         * letter a-z or A-Z, '?' is returned
         *
         * @return Unvalidated guess to be used by processGuess method call for further execution
         */
        public char getGuess(){
            Scanner in = new Scanner(System.in);
            System.out.print("Please guess a single letter or type quit to exit game: ");
            String input = in.next();
            if (input.equalsIgnoreCase("quit")) {
                return '0';
            } else if (input.length() != 1 || !Character.isAlphabetic(input.charAt(0))) {
                return '?';
            } else {
                return Character.toLowerCase(input.charAt(0));
            }
        }

        /**
         * Method returns indicator of whether a valid guess was found in phrase attribute.
         * Additionally, if valid guess is found, object's hiddenPhrase attribute is modified to
         * reveal valid guess.
         *
         * @param guess char from getGuess function call - unvalidated
         * @return True if any case of an alphabetic letter is found, false if otherwise.
         */
        public boolean processGuess(char guess){
            int startIndex = 0;
            boolean end = false;
            char guessUpper = Character.toUpperCase(guess);
            boolean upperFound = this.phrase.indexOf(guessUpper) != -1;
            boolean lowerFound = this.phrase.indexOf(guess) != -1;
            boolean upperAdded = this.hiddenPhrase.indexOf(Character.toString(guessUpper)) != -1;
            boolean lowerAdded = this.hiddenPhrase.indexOf(Character.toString(guess)) != -1;

            do {
                if (upperFound && !upperAdded && startIndex != -1) {
                    startIndex = this.phrase.indexOf(guessUpper, startIndex);
                    this.hiddenPhrase.setCharAt(startIndex, guessUpper);
                    startIndex++; // Increment allows next ch or -1 to be found in next
                    upperAdded = this.hiddenPhrase.indexOf(Character.toString(guessUpper)) != -1 &&
                            this.phrase.indexOf(guessUpper,
                                    startIndex) == -1;
                } else if (lowerFound && !lowerAdded && startIndex != -1) {
                    startIndex = this.phrase.indexOf(guess, startIndex);
                    this.hiddenPhrase.setCharAt(startIndex, guess);
                    startIndex++; // Increment allows next ch or -1 to be found in next
                    lowerAdded = this.hiddenPhrase.indexOf(Character.toString(guess)) != -1 &&
                            this.phrase.indexOf(guess, startIndex) == -1;
                } else {
                    end = true;
                }
            } while (!end);
            return upperFound || lowerFound;
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

            WheelOfFortune game = new WheelOfFortune();
            int guessesLeft = 3;
            int missNum = 0;
            String message;

            do {
                char guess = game.getGuess();
                while (guess == '?'){
                    System.out.println("Invalid guess | Guesses left: " + guessesLeft);
                    System.out.println("Phrase: " + game.hiddenPhrase);
                    System.out.println("Previous guesses: " + game.previousGuesses + "\n");
                    guess = game.getGuess();
                }
                boolean letterFound = game.processGuess(guess);

                // Conditionals to handle prompt type, guessesLeft counter, and game exit mechanics
                if (guess == '0') {
                    break;
                } else if (letterFound){
                    if (game.previousGuesses.contains(guess)){ // Letter found, old guess -> same guesses
                        message = "Guess has already been made | Guesses left: " + guessesLeft + " | Misses made: " + missNum;
                    }
                    else { // Letter found, new guess -> same guesses
                        message = "You guessed right! | Guesses left: " + guessesLeft + " | Misses made: " + missNum;
                    }
                } else {
                    if (game.previousGuesses.contains(guess)){ // Letter not found, old guess -> same guesses
                        message = "Guess has already been made | Guesses left: " + guessesLeft + " | Misses made: " + missNum;
                    }
                    else { // Letter not found, new guess -> lose one guess
                        guessesLeft--;
                        missNum++;
                        if (guessesLeft == 0){ // Sets guess to exit char to exit game at end of iter
                            System.out.println("The maximum number of guesses have been made. Better " +
                                    "luck next time!");
                            System.out.println("Phrase: " + game.hiddenPhrase);
                            System.out.println("Previous guesses: " + game.previousGuesses);
                            break;
                        } else {
                            message = "Nothing was found | Guesses left: " + guessesLeft + " | Misses made: " + missNum;
                        }
                    }
                }
                game.previousGuesses.add(guess); // Adds guesses to hashset, unique guesses accumulate
                System.out.println(message);
                System.out.println("Phrase: " + game.hiddenPhrase);
                System.out.println("Previous guesses: " + game.previousGuesses + "\n");
            } while (!game.phrase.contentEquals(game.hiddenPhrase));
            if (game.phrase.contentEquals(game.hiddenPhrase)){
                System.out.println("You have successfully guessed the phrase. Congratulations!");
            }
            System.out.println("Game has exited successfully.");


        }
}