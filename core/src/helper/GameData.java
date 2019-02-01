package helper;

/**
 * Created by Administrator on 13/11/2560.
 */

public class GameData {

    // this class made for save a data when u out of game #it14kmitl
    private int highScore;
    private int HighScoreCoins;

//

    // this class use for check sound only
    //because change a game style not use all of this.
    private boolean easyDifficulties;
    private boolean mediumDifficulties;
    private boolean hardDifficulties;


    private boolean musicOn;


    public int getHighScore() {
        return highScore;
        //
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
        //
    }

    public int getHighScoreCoins() {
        return HighScoreCoins;
        //
    }

    public void setHighScoreCoins(int highScoreCoins) {
        HighScoreCoins = highScoreCoins;
        //
    }

    public boolean isEasyDifficulties() {
        return easyDifficulties;
        //
    }

    public void setEasyDifficulties(boolean easyDifficulties) {
        this.easyDifficulties = easyDifficulties;
    }

    public boolean isMediumDifficulties() {
        return mediumDifficulties;
        //
    }

    public void setMediumDifficulties(boolean mediumDifficulties) {
        this.mediumDifficulties = mediumDifficulties;
    }

    public boolean isHardDifficulties() {
        return hardDifficulties;
        //
    }

    public void setHardDifficulties(boolean hardDifficulties) {
        this.hardDifficulties = hardDifficulties;
    }

    public boolean isMusicOn() {
        return musicOn;
        //
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
        //
    }
}//

























