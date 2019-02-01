package Hud;

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

public class HighscoreButton {

    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;

    private Label scoreLabel, coinsLabel;
    private ImageButton backBtn;
    public HighscoreButton(GameMain game){
        this.game = game;

        gameViewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT,
                new OrthographicCamera());

        stage = new Stage(gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createAndPositionUiElement();

        stage.addActor(backBtn);
        stage.addActor(scoreLabel);
        stage.addActor(coinsLabel);
    }

    void createAndPositionUiElement(){
        backBtn = new ImageButton(new SpriteDrawable(new SpriteDrawable(new Sprite(
                new Texture("Button/Back.png")))));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("font/blow.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        BitmapFont scoreFont = generator.generateFont(parameter);
        BitmapFont coinFont = generator.generateFont(parameter);



        Preferences prefs = Gdx.app.getPreferences("Data");
//
//        prefs.putInteger("Scorefar", 0);
//        prefs.putInteger("Score", 0);
        System.out.println(prefs.getInteger("Scorefar"));


        scoreLabel = new Label(String.valueOf(prefs.getInteger("ScorefarOfStage4"))
                , new Label.LabelStyle(scoreFont, Color.WHITE));
        coinsLabel = new Label(String.valueOf(prefs.getInteger("ScoreStage4"))
                , new Label.LabelStyle(coinFont, Color.WHITE));




        backBtn.setPosition(17,17, Align.bottomLeft);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                 game.setScreen(new MainMenu(game));
            }
        });

        scoreLabel.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT/2-55,Align.center);
        coinsLabel.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT/2-170,Align.center);

    }

    public Stage getStage(){
        return this.stage;
    }
}
