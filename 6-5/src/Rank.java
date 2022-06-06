public class Rank implements Comparable<Rank> {

    // player name
    private String playerName;

    // point
    private int point;

    /**
     *
     * @param playerName player name
     * @param point point
     */
    public Rank(String playerName, int point) {
        this.playerName = playerName;
        this.point = point;
    }

    @Override
    public String toString() {
        return playerName +
                "," + point;
    }



    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public int compareTo(Rank o) {
        return point - o.point!=0?(point - o.point):playerName.compareTo(o.playerName);
    }
}
