/**
 * Abstract game class that specific games will extend
 * @see Mastermind
 * @see WheelOfFortune
 */
public abstract class Game {

    protected AllGamesRecord gamesRecords;


    /**
     * Constructs a game object with an AllGamesRecord object
     */
    public Game(){
        this.gamesRecords = new AllGamesRecord();
    }

    /**
     * a method that plays a set of games and records and returns an AllGamesRecord object for the set
     * @return AllGamesRecord object
     */
    protected AllGamesRecord playAll(){
        while (playNext()) {
            GameRecord record = play();
            this.gamesRecords.add(record);
        }
        return gamesRecords;
    }

    /**
     * Returns record of a finished game
     * @return GameRecord object following the end of a game
     */
    protected abstract GameRecord play();

    /**
     * Returns whether the next game should be played
     * @return true/false for whether another game should be played
     */
    protected abstract boolean playNext();


}