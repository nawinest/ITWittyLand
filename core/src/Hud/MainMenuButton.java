package Hud;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nawinz.itwitty.GameMain;

import helper.GameInfo;
import helper.GameManager;
import scenes.GamePlay;
import scenes.HighScore;
import scenes.MainMenu;
import scenes.Options;

/**
 * Created by Administrator on 11/11/2560.
 */

public class MainMenuButton {
    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;
    private ImageButton playbtn, scoreBtn , changeCaracterButton,optionsBtn,quitBtn,musicBtn;

    public MainMenuButton(GameMain game){
        this.game = game;
        gameViewport = new FitViewport(GameInfo.WIDTH,GameInfo.HEIGHT,new OrthographicCamera());
        stage = new Stage(gameViewport ,game.getBatch());
        createButtonplay();
        stage.addActor(playbtn);
        stage.addActor(scoreBtn);
        stage.addActor(optionsBtn);
        stage.addActor(quitBtn);
//        stage.addActor(musicBtn);
        Preferences prefs = Gdx.app.getPreferences("Data");


        checkMusic();
        changeMusic();
        changeCaracter();


    }

    void createButtonplay(){
        playbtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("ButtonMainMenu/startBtn.png"))));

        scoreBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("ButtonMainMenu/highscoreBtn.png"))));

        optionsBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("ButtonMainMenu/optionsBtn.png"))));

        quitBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("ButtonMainMenu/quitBtn.png"))));



        playbtn.setPosition(GameInfo.WIDTH/2f-225,
                GameInfo.HEIGHT/2f -250, Align.center);
        scoreBtn.setPosition(GameInfo.WIDTH/2f-75,
                GameInfo.HEIGHT/2f - 250, Align.center);
        optionsBtn.setPosition(GameInfo.WIDTH/2f+75,
                GameInfo.HEIGHT/2f - 250, Align.center);
        quitBtn.setPosition(GameInfo.WIDTH/2f+225,
                GameInfo.HEIGHT/2f- 250, Align.center);





        playbtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GamePlay((game)));
                playbtn.setDisabled(true);
                scoreBtn.setDisabled(true);
                optionsBtn.setDisabled(true);
                playbtn.setDisabled(true);
            }
        });

        scoreBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new HighScore(game));

            }
        });

        optionsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Options(game));
            }
        });

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });


    }


    void changeCaracter(){
        if (changeCaracterButton != null) {
            changeCaracterButton.remove();
        }
        changeCaracterButton = new ImageButton(new SpriteDrawable
                (new Sprite(new Texture("Player/" +
                        GameManager.getInstance().getPlayer() + "Front.png"))));

        changeCaracterButton.setPosition(GameInfo.WIDTH / 2f,
                GameInfo.HEIGHT / 2f, Align.center);

        changeCaracterButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.getInstance().incrementIndex();

                //fixed it to Buy a new Caracter;
                changeCaracter();
            }
        });

        stage.addActor(changeCaracterButton);
    }

    void changeMusic() {
        if (musicBtn != null) {
            musicBtn.remove();
        }
        musicBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("Button/music-" + checkOnOff() + ".png"))));

        musicBtn.setPosition(GameInfo.WIDTH - 13, 13, Align.bottomRight);

        musicBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("on click at sound button");
                Preferences prefs = Gdx.app.getPreferences("Data");
                boolean checkSound =  prefs.getBoolean("BackgroundSound");


                if(checkSound){
                    prefs.putBoolean("BackgroundSound",false);
                    GameManager.getInstance().stopMusic();
                }
                else {
                    prefs.putBoolean("BackgroundSound",true);
                    GameManager.getInstance().playMusic();
                }
                System.out.println(checkSound);
                prefs.flush();
                changeMusic();
            }
        });

        stage.addActor(musicBtn);

    }

    void checkMusic(){
        Preferences prefs = Gdx.app.getPreferences("Data");
        boolean checkSound =  prefs.getBoolean("BackgroundSound");
        if(checkSound){
            GameManager.getInstance().playMusic();
        }
    }

    public String checkOnOff(){
        Preferences prefs = Gdx.app.getPreferences("Data");
        boolean checkSound =  prefs.getBoolean("BackgroundSound");
        if(checkSound){
            return "on";
        }
        else{
            return "off";
        }

    }

    public Stage getStage(){
        return this.stage;
    }


} // end






