import java.util.*;

public class WheelOfFortuneAISmartPlayer implements WheelOfFortunePlayer{

    protected String playerId;
    protected int botGuessNum;
    protected HashSet<Character> previousGuesses;


    //todo: does one game, now I have to handle playNext

    public WheelOfFortuneAISmartPlayer(String userName){
        this.playerId = userName;
        this.botGuessNum = 0;
//        this.previousGuesses = new HashSet<>();
    }

    @Override
    public char nextGuess(StringBuilder hiddenPhrase, HashSet<Character> previousGuesses) {
        char guess;
        Character[] lettersByFrequencyPre = {'e', 't', 'a', 'o', 'i', 'n', 's', 'h', 'r', 'd', 'l',
                'c', 'u', 'm', 'w', 'f', 'g', 'y', 'p', 'b', 'v', 'k', 'j', 'x', 'q', 'z'};
        List<Character> lettersByFrequency =
                new ArrayList<>(List.of(lettersByFrequencyPre));
        String[] bigramsDescendingPre = {"th", "he", "in", "en", "nt", "re", "er", "an", "ti", "es",
                "on", "at", "se", "nd", "or", "ar", "al", "te", "co", "de", "to", "ra", "et",
                "ed", "it", "sa", "em", "ro"};
        List<String> bigramsDescending = new ArrayList<>(List.of(bigramsDescendingPre));

        guess = lettersByFrequency.get(botGuessNum);

        // If empty the first most commonly used letter is selected
        if (previousGuesses.isEmpty()){
            this.botGuessNum++;
            return guess;
        } else {
            // if the first letter wasn't found it selects the next most commonly used letter
            if (hiddenPhrase.indexOf("e") == -1) {
                this.botGuessNum++;
                guess = lettersByFrequency.get(botGuessNum);
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
                                guess = bigramsDescending.get(j).charAt(0);
                                break;
                            }
                        }
                    } else {
                        // A bigram was not found. In this case the next most commonly used
                        // letter that hasn't been made is used and submitted as a guess
                        do {
                            char temp = lettersByFrequency.get(botGuessNum);
                            if (previousGuesses.contains(temp)) {
                                this.botGuessNum++;
                            } else {
                                guess = temp;
                            }
                        } while (previousGuesses.contains(guess));
                        break;
                    }
                }
            }
        }
//        this.previousGuesses.add(guess);
        return guess;

    }


    @Override
    public void reset() {
        //todo: need for implementing this depends on strategy. This may not be required to do anything if
        // getGuess is simple enough
        this.botGuessNum = 0;
    }

    @Override
    public String playerId() {
        return this.playerId;
    }

}
