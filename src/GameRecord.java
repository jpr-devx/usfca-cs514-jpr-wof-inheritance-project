public class GameRecord implements Comparable{

    int score;
    String playerId;

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
     * @param o the object to be compared.
     * @return -1 if this GameRecord is less than the other GameRecord object, 0 if equal to and 1 if greater than
     */
    public int compareTo(Object o){
        GameRecord other = (GameRecord) o;
        return Integer.compare(this.score, other.score);
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
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRecord other = (GameRecord) o;
        return getScore() == other.getScore() && this.playerId.equals(other.playerId);
    }
}
