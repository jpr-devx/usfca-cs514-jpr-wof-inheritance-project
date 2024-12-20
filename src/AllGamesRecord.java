import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Class that allows for storage of multiple game records and for information about those records to be summarized
 * @see GameRecord
 */
public class AllGamesRecord {

    protected ArrayList<GameRecord> gameRecords;

    /**
     * Constructor that initializes object with arrayList of gameRecords
     */
    public AllGamesRecord(){
        this.gameRecords = new ArrayList<>();
    }

    /**
     * Adds a GameRecord to the AllGamesRecord
     */
    protected void add(GameRecord gameRecord){
        this.gameRecords.add(gameRecord);
    }

    /**
     * Returns average score for all games of a particular player
     * @return average score
     */
    protected double average(){
        double total = 0.;
        for (GameRecord gameRecord : this.gameRecords){
            total += gameRecord.score;
        }
        return total / this.gameRecords.size();
    }

    /**
     * Returns a sorted list of the top n scores including player and score. This method is executed by using the
     * Collections class to sort the game instances
     * @return sorted list of n scores
     */
    protected ArrayList<GameRecord> highGameList(int n){
        // Sort games by score ascending in new arrayList sorted
        ArrayList<GameRecord> sorted = new ArrayList<>(this.gameRecords);
        Collections.sort(sorted);

        // Create new arrayList sortedN to add the n-highest in descending order. Done in for-loop below
        ArrayList<GameRecord> sortedN = new ArrayList<>();

        // Sets limiter that stops for-loop iteration to 0 if n exceeds size of gameRecords, otherwise the difference
        // between the size and n is used
        int limiter = sorted.size() - n >= 0 ? sorted.size() - n : 0;

        for (int index = sorted.size() - 1; index >= limiter; index--) sortedN.add(sorted.get(index));

        // Return descending sortedN arrayList with n-highest scores
        return sortedN;
    }

    /**
     * Returns a sorted list of the top n scores for the specified player. This method is executed by using the
     * Collections class to sort the game instances
     * @return sorted list of n scores
     */
    protected ArrayList<GameRecord> highGameList(String playerId, int n){
        AllGamesRecord tempList = new AllGamesRecord();

        for (GameRecord game : this.gameRecords){
            if (game.playerId.equals(playerId)) tempList.add(game);
        }

        return tempList.highGameList(n);
    }

    /**
     * Returns average for a specific player's games
     * @param playerId a specific player's ID
     * @return average of a player's games
     */
    protected double average(String playerId){
        AllGamesRecord tempList = new AllGamesRecord();
        double temp = 0.;
        int gameNum = 0;

        for (GameRecord game : this.gameRecords){
            if (game.playerId.equals(playerId)) {
                temp += game.score;
                gameNum++;
            }
        }
        return temp/gameNum;
    }

    /**
     * Returns string representation of gameRecords ArrayList
     * @return ArrayList of gameRecords
     */
    @Override
    public String toString() {
        return "GameRecords: " + gameRecords + "\nAverage Score: " + this.average() + " points";
    }

    /**
     * Returns true if object is the same instance of a AllGamesRecord object or if both ArrayList's of GameRecords are the same
     * @param o Other object to be compared with
     * @return true if objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllGamesRecord that = (AllGamesRecord) o;
        return Objects.equals(gameRecords, that.gameRecords);
    }
}
