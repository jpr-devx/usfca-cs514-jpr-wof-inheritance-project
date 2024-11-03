import java.util.ArrayList;

public abstract class Game {

    protected AllGamesRecord gamesRecords;

    public Game(){
        this.gamesRecords = new AllGamesRecord();
    }

    /**
     * a method that plays a set of games and records and returns an AllGamesRecord object for the set
     * @return
     */
    protected AllGamesRecord playAll(){
        while (playNext()) {
            GameRecord record = play();
            this.gamesRecords.add(record);
        }
        return gamesRecords;
    }

    protected abstract GameRecord play();

    protected abstract boolean playNext();


}