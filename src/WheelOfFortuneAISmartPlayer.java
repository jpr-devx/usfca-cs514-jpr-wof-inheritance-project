import java.util.*;

/**
 * Smart bot that uses commonly used letters and commonly seen bigrams in English words
 */
public class WheelOfFortuneAISmartPlayer implements GuessingPlayer {

    protected String playerId;
    protected int botGuessNum;

    /**
     * Constructs smart player with username and its score set to 0
     * @param userName name of bot
     */
    public WheelOfFortuneAISmartPlayer(String userName){
        this.playerId = userName;
        this.botGuessNum = 0;
    }

    /**
     * Bot uses commonly used letters and bigrams to provide a String guess of length 1
     * @param hiddenPhrase current hiddenphrase of game
     * @param previousGuesses previous guesses that bot has made
     * @return String guess of length 1
     */
    @Override
    public String nextGuess(StringBuilder hiddenPhrase, HashSet<String> previousGuesses) {
        String guess;
        Character[] lettersByFrequencyPre = {'e', 't', 'a', 'o', 'i', 'n', 's', 'h', 'r', 'd', 'l',
                'c', 'u', 'm', 'w', 'f', 'g', 'y', 'p', 'b', 'v', 'k', 'j', 'x', 'q', 'z'};
        List<Character> lettersByFrequency =
                new ArrayList<>(List.of(lettersByFrequencyPre));
        String[] bigramsDescendingPre = {"th", "he", "in", "en", "nt", "re", "er", "an", "ti", "es",
                "on", "at", "se", "nd", "or", "ar", "al", "te", "co", "de", "to", "ra", "et",
                "ed", "it", "sa", "em", "ro"};
        List<String> bigramsDescending = new ArrayList<>(List.of(bigramsDescendingPre));

        guess = "" + lettersByFrequency.get(botGuessNum);

        // If empty the first most commonly used letter is selected
        if (previousGuesses.isEmpty()){
            this.botGuessNum++;
            return guess;
        } else {
            // if the first letter wasn't found it selects the next most commonly used letter
            if (hiddenPhrase.indexOf("e") == -1) {
//                this.botGuessNum++;
                guess = "" + lettersByFrequency.get(botGuessNum);
            } else {
                // if the first letter was found, it searches for bigrams with that letter and
                // then if found, the bigrams list is iterated over to submit a guess if a bigram
                // is found.
                String hiddenPhraseString = "" + hiddenPhrase;
                List<String> hiddenPhraseStringList = Arrays.asList(hiddenPhraseString.split(" "));
                for (int i = 0; i < hiddenPhraseStringList.size(); i++){
                    if (hiddenPhraseStringList.get(i).length() == 2) {
                        for (int j = 0; j < bigramsDescending.size(); j++) {
                            if (!previousGuesses.contains(bigramsDescending.get(i).charAt(0)) && bigramsDescending.get(i).charAt(1) == 'e') {
                                guess = "" + bigramsDescending.get(j).charAt(0);
//                                botGuessNum++;
                                break;
                            }
                        }
                    } else {
                        // A bigram was not found. In this case the next most commonly used
                        // letter that hasn't been made is used and submitted as a guess
                        do {
                            char temp = lettersByFrequency.get(botGuessNum);
                            if (previousGuesses.contains(temp)) {
//                                this.botGuessNum++;
                            } else {
                                guess = "" + temp;
                            }
                        } while (previousGuesses.contains(guess));
                        break;
                    }
                }
            }
        }
//        this.previousGuesses.add(guess);
        this.botGuessNum++;
        return guess;

    }


    /**
     * Sets bot guess num to 0, an integer used to determine what letter to guess
     */
    @Override
    public void reset() {
        this.botGuessNum = 0;
    }

    /**
     * Returns playerId of bot
     * @return playerId of bot
     */
    @Override
    public String playerId() {
        return this.playerId;
    }


    /**
     * Returns string representation of player with their ID and number of guesses
     * @return string representation of player
     */
    @Override
    public String toString() {
        return "WheelOfFortuneAISmartPlayer{" +
                "playerId='" + playerId + '\'' +
                ", botGuessNum=" + botGuessNum +
                '}';
    }

    /**
     * Compares player with object to see if they are the same instance or having the same data members
     * @param o other object being compared
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WheelOfFortuneAISmartPlayer that = (WheelOfFortuneAISmartPlayer) o;
        return botGuessNum == that.botGuessNum && Objects.equals(playerId, that.playerId);
    }



}
