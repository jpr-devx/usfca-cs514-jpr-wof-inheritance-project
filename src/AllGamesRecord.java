import java.util.ArrayList;
import java.util.Collections;

public class AllGamesRecord {

    ArrayList<GameRecord> gameRecords;

    /**
     * Constructor that initializes object with arrayList of gameRecords
     */
    public AllGamesRecord(){
        this.gameRecords = new ArrayList<>();
    }

    /**
     * Adds a GameRecord to the AllGamesRecord
     */
    private void add(GameRecord gameRecord){
        this.gameRecords.add(gameRecord);
    }

    /**
     * Returns average score for all games of a particular player
     * @return average score
     */
    private double average(){
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
    private ArrayList<GameRecord> highGameList(int n){
        // Sort games by score ascending in new arrayList sorted
        ArrayList<GameRecord> sorted = new ArrayList<>(this.gameRecords);
        Collections.sort(sorted);

        // Create new arrayList sortedN to add the n-highest in descending order. Done in for-loop below
        ArrayList<GameRecord> sortedN = new ArrayList<>();

        for (int index = sorted.size() - 1; index >= sorted.size() - n; index--) sortedN.add(sorted.get(index));

        // Return descending sortedN arrayList with n-highest scores
        return sortedN;
    }

    /**
     * Returns a sorted list of the top n scores for the specified player. This method is executed by using the
     * Collections class to sort the game instances
     * @return sorted list of n scores
     */
//    private Game[] highGameList(int playerId, int n){
//
//    }

    @Override
    public String toString() {
        return "AllGamesRecord{" +
                "gameRecords=" + gameRecords +
                '}';
    }

    public static void main(String[] args){

        AllGamesRecord games = new AllGamesRecord();

        GameRecord game1 = new GameRecord("John");

        GameRecord game2 = new GameRecord("Bill");
        GameRecord game3 = new GameRecord("Joe");

        game1.setScore(3);
        game3.setScore(10);


        games.add(game1);
        games.add(game2);
        games.add(game3);

        System.out.println("Original List of games: " + games);

        ArrayList<GameRecord> sortedGames = games.highGameList(2);
        System.out.println("Sorted list of games: " + sortedGames);

    }
}
