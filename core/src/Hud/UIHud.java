package Hud;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nawinz.itwitty.GameMain;

import java.awt.Label;

import helper.GameData;
import helper.GameInfo;
import helper.GameManager;
import scenes.GamePlay;
import scenes.MainMenu;

/**
 * Created by Administrator on 8/11/2560.
 */

public class UIHud {
    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;
    private int farGoScore = 0;
    private int score = 0;

    private ImageButton retryBtn, quitBtn;

    public GameManager gameManager = new GameManager();
    //
    private Image coinImg, scoreImg;

    private com.badlogic.gdx.scenes.scene2d.ui.Label scoreLabel;
    private com.badlogic.gdx.scenes.scene2d.ui.Label farGo;
    private com.badlogic.gdx.scenes.scene2d.ui.Label whichStage;





    public UIHud(GameMain game){
        this.game = game;
        gameViewport = new FitViewport(GameInfo.WIDTH,
                GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(gameViewport,game.getBatch());
        createLabel();
        createImages();

        Table scoreTable = new Table();
        scoreTable.top().left();
        scoreTable.setFillParent(true);
        scoreTable.add(coinImg).padLeft(10).padTop(10);
        scoreTable.add(scoreLabel).padLeft(10).padTop(10).align(0);


//        scoreAndFarTable.row();
        Table farTable = new Table();
        farTable.top().right();
        farTable.setFillParent(true);
        farTable.add(farGo).padRight(10).padTop(10);
        farTable.add(scoreImg).padRight(10).padTop(10);
        stage.addActor(scoreTable);
        stage.addActor(farTable);


    }

    void createLabel(){
        //for coins
        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("font/04b_19.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new
                FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35;
        BitmapFont font = generator.generateFont(parameter);

        //for How far
        FreeTypeFontGenerator generator2 =
                new FreeTypeFontGenerator(Gdx.files.internal("font/04b_19.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new
                FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 35;
        BitmapFont font2 = generator2.generateFont(parameter2);

        //for which stage
        FreeTypeFontGenerator generator3 =
                new FreeTypeFontGenerator(Gdx.files.internal("font/Quark-Bold.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new
                FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter3.size = 22;
        BitmapFont font3 = generator3.generateFont(parameter3);


        scoreLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label(String.valueOf(score),
                new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(font, Color.WHITE));

        farGo = new com.badlogic.gdx.scenes.scene2d.ui.Label(String.valueOf(farGoScore),
                new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(font2, Color.WHITE));

        whichStage = new com.badlogic.gdx.scenes.scene2d.ui.Label(getWhichStage(),
                new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(font3, Color.GOLD));

        whichStage.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT - 30,Align.center);
        stage.addActor(whichStage);
//        farGo.setPosition(GameInfo.WIDTH/2f -450 ,GameInfo.HEIGHT/2f + 270 );

    }



    public void createButton(){
        //for show score
        Image gameOverPanel = new Image(new Texture("showScore/ScoreBoard.png"));
        //create font for show score
        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("font/04b_19.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new
                FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 45;
        BitmapFont font = generator.generateFont(parameter);

        //createLabel
        com.badlogic.gdx.scenes.scene2d.ui.Label endScore
                = new com.badlogic.gdx.scenes.scene2d.ui.Label(String.valueOf(farGoScore),
                new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(font,Color.WHITE));
        com.badlogic.gdx.scenes.scene2d.ui.Label endCoinsScore
                = new com.badlogic.gdx.scenes.scene2d.ui.Label(String.valueOf(score),
                new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(font,Color.WHITE));

        //setPosition for GameOverPanel and scoreall
        gameOverPanel.setPosition(GameInfo.WIDTH/2f
                ,GameInfo.HEIGHT/2f + 70, Align.center);
        endScore.setPosition(GameInfo.WIDTH/2f
                , GameInfo.HEIGHT/2f+90,Align.left);
        endCoinsScore.setPosition(GameInfo.WIDTH/2f
                , GameInfo.HEIGHT/2f-20,Align.left);

        stage.addActor(gameOverPanel);
        stage.addActor(endScore);
        stage.addActor(endCoinsScore);


        //for button
        retryBtn = new ImageButton(new SpriteDrawable
                (new Sprite(new Texture("Button/Retry.png"))));
        quitBtn = new ImageButton(new SpriteDrawable
                (new Sprite(new Texture("Button/Quit.png"))));

        retryBtn.setPosition(GameInfo.WIDTH/2f - retryBtn.getWidth()/2f-75, GameInfo.HEIGHT/2f-200f);
        quitBtn.setPosition(GameInfo.WIDTH/2f - quitBtn.getWidth()/2f+75, GameInfo.HEIGHT/2f-200f);

        retryBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GamePlay(game));
                stage.dispose();
            }
        });

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
                stage.dispose();
            }
        });
        stage.addActor(retryBtn);
        stage.addActor(quitBtn);
        Preferences prefs = Gdx.app.getPreferences("Data");
        System.out.println(prefs.getInteger("Scorefar"));


    }

    public void createChampion(){
        //for show score
        Image gameOverPanel = new Image(new Texture("showScore/ScoreBoard2.png"));
        //create font for show score
        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("font/04b_19.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new
                FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 45;
        BitmapFont font = generator.generateFont(parameter);

        gameOverPanel.setPosition(GameInfo.WIDTH/2f
                ,GameInfo.HEIGHT/2f + 70, Align.center);
        stage.addActor(gameOverPanel);

        //for button
        retryBtn = new ImageButton(new SpriteDrawable
                (new Sprite(new Texture("Button/Retry.png"))));
        quitBtn = new ImageButton(new SpriteDrawable
                (new Sprite(new Texture("Button/Quit.png"))));

        retryBtn.setPosition(GameInfo.WIDTH/2f - retryBtn.getWidth()/2f-75, GameInfo.HEIGHT/2f-200f);
        quitBtn.setPosition(GameInfo.WIDTH/2f - quitBtn.getWidth()/2f+75, GameInfo.HEIGHT/2f-200f);

        retryBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GamePlay(game));
                stage.dispose();
            }
        });

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
                stage.dispose();
            }
        });
        stage.addActor(retryBtn);
        stage.addActor(quitBtn);
        Preferences prefs = Gdx.app.getPreferences("Data");
        System.out.println(prefs.getInteger("Scorefar"));


    }

    void createImages(){
        coinImg = new Image(new Texture("collectable/Coin.png"));
        scoreImg = new Image(new Texture("collectable/far.png"));


    }

    public void increaseScore(){
        score++;
        scoreLabel.setText(String.valueOf(score));
    }

    public void setIncreaseHowFar(int farGoScore){
        this.farGoScore = farGoScore;
        farGo.setText(String.valueOf(farGoScore));

    }

    public void showScore(){
        scoreLabel.setText(String.valueOf(score));
        farGo.setText(String.valueOf(farGoScore));
        stage.addActor(scoreLabel);
        stage.addActor(farGo);
    }

    public  int getScore(){
        return this.farGoScore;
    }
    public  int getCoinScore(){
        return this.score;
    }

    public String getWhichStage(){
        Preferences prefs = Gdx.app.getPreferences("Data");
        if(prefs.getBoolean("isStage1")){
            if(getCoinScore() > 5 || prefs.getInteger("ScoreStage1") > 5){
                return "Stage 1 : IT freshy [NextStage Unlock]";
            }
            return "Stage 1 : IT freshy";
        }
        if(prefs.getBoolean("isStage2")){
            if(getCoinScore() > 5 || prefs.getInteger("ScoreStage2") > 5){
                return "Stage 2 : IT sophomore [NextStage Unlock]";
            }
            return "Stage 2 : IT sophomore";
        }

        if(prefs.getBoolean("isStage3")){
            if(getCoinScore() > 5 || prefs.getInteger("ScoreStage3") > 5){
                return "Stage 3 : IT junior [NextStage Unlock]";
            }
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


}//
























