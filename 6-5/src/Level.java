public class Level {

    // the level name
    private String levelName;

    // the level story
    private String levelStory;

    // the monster player meet
    private Monster monster;

    // the point player get while through the level
    private int point;

    /**
     *
     * @param levelName the level name
     * @param levelStory the level story
     * @param monster the monster player meet
     * @param point the point player get while through the level
     */
    public Level(String levelName, String levelStory, Monster monster, int point) {
        this.levelName = levelName;
        this.levelStory = levelStory;
        this.monster = monster;
        this.point = point;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelStory() {
        return levelStory;
    }

    public void setLevelStory(String levelStory) {
        this.levelStory = levelStory;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
