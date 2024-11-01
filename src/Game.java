import java.util.ArrayList;

public abstract class Game {

    ArrayList<Game> games;

    public Game(){
        this.games = new ArrayList<>();
    }

    /**
     * a method that plays a set of games and records and returns an AllGamesRecord object for the set
     * @return
     */
//    public AllGamesRecord playAll(){
//        // todo: define method
//            for (Game game : games){
//
//        }
//    }

    public abstract GameRecord play();

    public abstract boolean playNext();


}