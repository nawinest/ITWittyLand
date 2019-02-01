package Hud;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nawinz.itwitty.GameMain;

import helper.GameInfo;
import helper.GameManager;
import scenes.MainMenu;

/**
 * Created by Administrator on 13/11/2560.
 */

public class OptionButton {

    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;
    private Label whichStage;

    private ImageButton fristStage,secondStage,thirdStage,forthStage,backBtn;
    private Sound clickSound;

    private Image sign;

    public OptionButton(GameMain game){
        this.game = game;
        gameViewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT,
                new OrthographicCamera());

        stage = new Stage(gameViewport , game.getBatch());

        Gdx.input.setInputProcessor(stage);
        createAndPositionButtons();
        addAllListener();

        stage.addActor(fristStage);
        stage.addActor(secondStage);
        stage.addActor(thirdStage);
        stage.addActor(forthStage);
        stage.addActor(backBtn);
        stage.addActor(whichStage);
//        stage.addActor(sign);

    }

    void createAndPositionButtons(){
        //del if not work

        Preferences prefs = Gdx.app.getPreferences("Data");

//        prefs.putInteger("firstStageScore",0);
//        prefs.putInteger("secondStageScore",0);
//        prefs.putInteger("thirdStageScore",0);

        prefs.putBoolean("isStage1", true);
        prefs.putBoolean("isStage2",false);
        prefs.putBoolean("isStage3",false);
        prefs.putBoolean("isStage4",false);

        prefs.flush();

        int firstStageScore =  prefs.getInteger("ScoreStage1");
        int secondStageScore =  prefs.getInteger("ScoreStage2");
        int thirdStageScore =  prefs.getInteger("ScoreStage3");


        fristStage = new ImageButton(new SpriteDrawable(new Sprite(new Texture(
            "Stage/stage1On.png"))));

        secondStage = new ImageButton(new SpriteDrawable(new Sprite(new Texture(
                "Stage/stage2" + GameManager.getInstance().getOnOff(checkUnlockStage2(firstStageScore)) +".png"))));

        thirdStage = new ImageButton(new SpriteDrawable(new Sprite(new Texture(
                "Stage/stage3"+GameManager.getInstance().getOnOff(checkUnlockStage3(secondStageScore))+".png"))));
        forthStage = new ImageButton(new SpriteDrawable(new Sprite(new Texture(
                "Stage/stage4"+GameManager.getInstance().getOnOff(checkUnlockStage4(thirdStageScore))+".png"))));

        backBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture(
                "Button/Back.png"))));

//        sign = new Image(new Texture("Button/Check Sign.png"));

        backBtn.setPosition(17,17, Align.bottomLeft);

        //for which stage
        FreeTypeFontGenerator generator3 =
                new FreeTypeFontGenerator(Gdx.files.internal("font/Quark-Bold.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new
                FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter3.size = 18;
        BitmapFont font3 = generator3.generateFont(parameter3);
        whichStage = new Label("Please Choose your stage", new Label.LabelStyle(font3, Color.WHITE));
        whichStage.setPosition(30,GameInfo.HEIGHT-40);

        //
        fristStage.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT/2f + 40,Align.center);
        secondStage.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT/2f -40,Align.center);
        thirdStage.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT/2f -120,Align.center);
        forthStage.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT/2f -200,Align.center);



    }

    void addAllListener(){
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });

        fristStage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound = Gdx.audio.newSound(Gdx.files.internal("Sound/Button.mp3"));
                clickSound.play();
                System.out.println("choose stage1");
                whichStage.setText("choose stage1");
                Preferences prefs = Gdx.app.getPreferences("Data");
                prefs.putBoolean("isStage1",true);
                prefs.putBoolean("isStage2",false);
                prefs.putBoolean("isStage3",false);
                prefs.putBoolean("isStage4",false);
                prefs.flush();
            }
        });

        secondStage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound = Gdx.audio.newSound(Gdx.files.internal("Sound/Button.mp3"));
                clickSound.play();
                Preferences prefs = Gdx.app.getPreferences("Data");
                int highCoinScoreStage1 =  prefs.getInteger("ScoreStage1");
                if(highCoinScoreStage1 > 5){
                    System.out.println("choose stage2");
                    whichStage.setText("choose stage2");
                    prefs.putBoolean("isStage1",false);
                    prefs.putBoolean("isStage2",true);
                    prefs.putBoolean("isStage3",false);
                    prefs.putBoolean("isStage4",false);
                    prefs.flush();
                }
                else{
                    System.out.println("can not choose this stage");
                    whichStage.setText("You can't to choose this Stage");
                }

            }
        });

        thirdStage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound = Gdx.audio.newSound(Gdx.files.internal("Sound/Button.mp3"));
                clickSound.play();
                Preferences prefs = Gdx.app.getPreferences("Data");
                int highCoinScoreStage2 =  prefs.getInteger("ScoreStage2");

                if(highCoinScoreStage2 > 5) {
                    System.out.println("choose stage3");
                    whichStage.setText("choose stage3");
                    prefs.putBoolean("isStage1", false);
                    prefs.putBoolean("isStage2", false);
                    prefs.putBoolean("isStage3", true);
                    prefs.putBoolean("isStage4", false);
                    prefs.flush();
                }
                else{
                    System.out.println("can not choose this stage");
                    whichStage.setText("You can't to choose this Stage");
                }
            }
        });

        forthStage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound = Gdx.audio.newSound(Gdx.files.internal("Sound/Button.mp3"));
                clickSound.play();
                Preferences prefs = Gdx.app.getPreferences("Data");
                int highCoinScoreStage3 =  prefs.getInteger("ScoreStage3");

                if(highCoinScoreStage3 > 5) {
                    System.out.println("choose stage4");
                    whichStage.setText("choose stage4");
                    prefs.putBoolean("isStage1", false);
                    prefs.putBoolean("isStage2", false);
                    prefs.putBoolean("isStage3", false);
                    prefs.putBoolean("isStage4", true);
                    prefs.flush();
                }
                else{
                    System.out.println("can not choose this stage");
                    whichStage.setText("You can't to choose this Stage");
                }
            }
        });


    }

    public int checkUnlockStage2(int score){
        if (score > 5){
            return 0;
        }
        else{
            return 1;
        }

    }

    public int checkUnlockStage3(int score){
        if (score > 5){
            return 0;
        }
        else{
            return 1;
        }
    }

    public int checkUnlockStage4(int score){
        if (score > 5){
            return 0;
        }
        else{
            return 1;
        }

    }


    public String getWhichStage(){
        Preferences prefs = Gdx.app.getPreferences("Data");
        if(prefs.getBoolean("isStage1")){
            return "Stage 1 : IT freshy";
        }
        if(prefs.getBoolean("isStage2")){
            return "Stage 2 : IT sophomore";
        }

        if(prefs.getBoolean("isStage3")){
            return "Stage 3 : IT junior";
        }

        if(prefs.getBoolean("isStage4")){
            return "Stage 4 : IT senior";
        }
        return "Stage 1 : IT freshy";
    }

    public Stage getStage(){
        return this.stage;
    }




}//end















