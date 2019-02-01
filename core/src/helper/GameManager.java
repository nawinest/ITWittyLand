package helper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.logging.FileHandler;

import javax.swing.plaf.synth.SynthTextAreaUI;

import Hud.UIHud;

/**
 * Created by Administrator on 11/11/2560.
 */

public class GameManager {
    private static final GameManager ourInstance = new GameManager();

    private String[] player = {"player1", "player2", "player3"};
    private String[] onOff ={"On","Off"};
    private int indexOfplayer = 0;


    //del it if not work
    private Music music;



    public GameManager() {
    }

    public void incrementIndex(){
        indexOfplayer ++;
        if(indexOfplayer == player.length){
            indexOfplayer = 0;
        }
    }

    public String getPlayer(){
        return player[indexOfplayer];
    }

    public String getOnOff(int index){

        return  onOff[index];
    }

    public void playMusic(){
        if(music == null){
            music = Gdx.audio.newMusic(Gdx.files.internal("Sound/Background.mp3"));
        }

        if(!music.isPlaying()){
            music.setLooping(true);
            music.play();
        }

    }

    public void stopMusic(){
        if(music.isPlaying()){
            music.stop();
            music.dispose();
        }
    }


    public static GameManager getInstance() {
        return ourInstance;
    }


}
