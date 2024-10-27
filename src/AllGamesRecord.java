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
    private ArrayList<GameRecord> highGameList(String playerId, int n){
        AllGamesRecord tempList = new AllGamesRecord();

        for (GameRecord game : this.gameRecords){
            if (game.playerId.equals(playerId)) tempList.add(game);
        }

        return tempList.highGameList(n);
    }

    /**
     * Returns string representation of gameRecords ArrayList
     * @return ArrayList of gameRecords
     */
    @Override
    public String toString() {
        return "AllGamesRecord{gameRecords=" + gameRecords + '}';
    }

    /**
     * Main method for AllGamesRecord
     * @param args program arguments
     */
    public static void main(String[] args){

        AllGamesRecord games = new AllGamesRecord();

        GameRecord game1 = new GameRecord("John");
        GameRecord game2 = new GameRecord("Bill");
        GameRecord game3 = new GameRecord("Joe");
        GameRecord game4 = new GameRecord("John");
        GameRecord game5 = new GameRecord("John");

        game1.setScore(3);
        game3.setScore(10);
        game4.setScore(25);
        game5.setScore(12);


        games.add(game1);
        games.add(game2);
        games.add(game3);
        games.add(game4);
        games.add(game5);

        System.out.println("Original List of games: " + games);

        ArrayList<GameRecord> sortedGames = games.highGameList(33);
        System.out.println("Sorted list of games: " + sortedGames);

    }
}
