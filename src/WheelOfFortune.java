/**
 * Abstract wheel of fortune class that concrete versions of WoF will implement
 * @see WheelOfFortuneAIGame
 * @see WheelOfFortuneUserGame
 */
public abstract class WheelOfFortune extends GuessingGame{

    /**
     * Takes in a validated guess that is either '0' or a lowercase letter and updates the hidden phrase if a new
     * correct guess is found. Helper method notifyGuess is called to return String that is printed in processGuess
     * to prompt user on game state (Current hidden phrase, guesses left, misses made, and information on past guess
     * @param guess single character guess
     * @return integer representing guess (-1 guess not found, 0 repeat guess, 1 guess found, -2 game exit)
     */
    @Override
    protected int processGuess(String guess){

        boolean found = false;
        int foundInt = -1;

        for (int index = 0; index < this.phrase.length(); index++){
            if(guess.equals("" + Character.toLowerCase(this.phrase.charAt(index))) && !this.previousGuesses.contains("" + Character.toLowerCase(this.phrase.charAt(index)))) {
                found = true;
                this.hiddenPhrase.setCharAt(index, this.phrase.charAt(index));
                int[] newArr = this.matchMap.get(guess.charAt(0));
                newArr[1]++;    // increment exact match by one, since its only one char guesses, there can only be
                // exact matches
                this.matchMap.replace(guess.charAt(0), newArr); // replace current array for char with incremented array
            }
        }

        if (this.previousGuesses.contains(guess)) foundInt = 0;
        else if (!found) {
            this.missesMade++;
            if (this.missesMade == this.guessesAllowed) foundInt = -2;
        } else if (guess.equals("0") || this.phrase.contentEquals(this.hiddenPhrase)) foundInt = -2; //quit
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
