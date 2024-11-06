/**
 * Class that stores information of a finished game (Score and playerID)
 */
public class GameRecord implements Comparable<GameRecord>{

    protected int score;
    protected String playerId;

    /**
     * Initialzies GameRecord object with score at
     * @param playerId
     */
    public GameRecord(String playerId){
        this.score = 0;
        this.playerId = playerId;
    }

    /**
     * Returns GameRecord score
     * @return GameRecord score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets GameRecord score to int score
     * @param score to set as GameRecord's score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns playerId associated with GameRecord object
     * @return playerId belonging to GameRecord object
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Sets GameRecord playerId to String playerId
     * @param playerId to set as GameRecord's playerId
     */
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    /**
     * Compares GameRecord objects by their score
     * @param o the GameRecord to be compared.
     * @return -1 if this GameRecord is less than the other GameRecord object, 0 if equal to and 1 if greater than
     */
    @Override
    public int compareTo(GameRecord o) {
        return Integer.compare(this.score, o.score);
    }

    /**
     * String representation of game including game score and playerId
     * @return GameRecord{score=this.score, playerId=this.playerId}
     */
    @Override
    public String toString() {
        return "GameRecord{score=" + score + ", playerId= " + playerId + '}';
    }

    /**
     * Returns true if both objects are the same GameRecord instance or if their playerId's and scores are equal, otherwise false
     * @param o other object being compared
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRecord other = (GameRecord) o;
        return getScore() == other.getScore() && this.playerId.equals(other.playerId);
    }


}
