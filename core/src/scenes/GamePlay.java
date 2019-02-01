package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nawinz.itwitty.GameMain;

import Coins.Coins;
import Hud.UIHud;
import Player.Player;
import bullet.Bullet;
import ground.GroundBody;
import helper.GameInfo;
import helper.GameManager;

/**
 * Created by Administrator on 7/11/2560.
 */

public class GamePlay implements Screen, ContactListener{
    private GameMain game;
    private World world;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;

    private OrthographicCamera debugCamera;
    private Box2DDebugRenderer debugRenderer;

    private Array<Sprite> backgrounds = new Array<Sprite>();
    private Array<Sprite> grounds = new Array<Sprite>();

    //del it it not work
    private float lavelHard = 3;

    private Player player;
    private GroundBody groundBody;

    private Array<Coins> coinsArray = new Array<Coins>();
    private final int DISTANCE_COIN_PLAY = 150;

    private UIHud hud;
    private boolean firstBegin;
    private Array<Bullet> bulletArray = new Array<Bullet>();
    private final int DISTANCE_BETWEEN_BULLET = 300;
    //for count far
    private int count;
    private int plus=0;
    private Sound coinsSound, diedSound;


    public GamePlay(GameMain game){
        this.game = game;

        mainCamera = new OrthographicCamera(GameInfo.WIDTH,GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH/2f, GameInfo.HEIGHT/2,0);
        gameViewport = new StretchViewport(GameInfo.WIDTH,GameInfo.HEIGHT,mainCamera);

        debugCamera = new OrthographicCamera();
        debugCamera.setToOrtho(false, GameInfo.WIDTH/GameInfo.PPM
                , GameInfo.HEIGHT/GameInfo.PPM);
        debugCamera.position.set(GameInfo.WIDTH/2f, GameInfo.HEIGHT/2f, 0);
        debugRenderer = new Box2DDebugRenderer();


        hud = new UIHud(game);

        createdBackgrounds();
        createGrounds();
        world = new World(new Vector2(0,-9.8f), true);
        world.setContactListener(this);
        player = new Player(world, GameInfo.WIDTH/2f-230, GameInfo.HEIGHT/2f);
        groundBody = new GroundBody(world, grounds.get(0));
        coinsSound = Gdx.audio.newSound(Gdx.files.internal("Sound/Coins.mp3"));
        diedSound = Gdx.audio.newSound(Gdx.files.internal("Sound/Died.mp3"));
    }


    void checkForfist(){
        if(!firstBegin){
            if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                plus = 1;
                checkLavelHard();
                createAllObject();
                firstBegin = true;
                player.activePlayer();
            }
        }
    }

    void checkLavelHard(){
        Preferences prefs = Gdx.app.getPreferences("Data");
        //check
        if(prefs.getBoolean("isStage1")){
            lavelHard = 7;
            System.out.println("Lavel of hard = " + lavelHard);
        }
        if(prefs.getBoolean("isStage2")){
            lavelHard = 5;
            System.out.println("Lavel of hard = " + lavelHard);
        }

        if(prefs.getBoolean("isStage3")){
            lavelHard = 3;
            System.out.println("Lavel of hard = " + lavelHard);
        }

        if(prefs.getBoolean("isStage4")){
            lavelHard = 2;
            System.out.println("Lavel of hard = " + lavelHard);
        }





    }


    void update(float dt){
        count += plus;
        hud.setIncreaseHowFar(count);
        checkForfist();
        if(player.getIsAlive()){
            moveBackgrounds();
            moveGrounds();
            playerJump();
            updateBullet();
            updateCoins();
            moveCoins();
            moveBullets();
        }
    }


    void createAllObject(){
        RunnableAction run = new RunnableAction();
        run.setRunnable(new Runnable() {
            @Override
            public void run() {
                //put my customcode
                createBullet();
                createCoins();
            }
        });

        SequenceAction sa = new SequenceAction();
        sa.addAction(Actions.delay(lavelHard));
        sa.addAction(run);
        hud.getStage().addAction(Actions.forever(sa));
    }

    void playerJump(){
        if(Gdx.input.isTouched()  || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            player.playerJump();
        }
    }



    void createdBackgrounds(){
        for (int i = 0;i<3;i++){
         Sprite bg =  new Sprite(new Texture("StageBackgrounds/bgStage"+bgOfStage()+".jpg"))   ;
         bg.setPosition(i*bg.getWidth(),0);
         backgrounds.add(bg);
        }
    }

    public String bgOfStage(){
        Preferences prefs = Gdx.app.getPreferences("Data");
        if(prefs.getBoolean("isStage1")) {
            return "1";
        }
        if(prefs.getBoolean("isStage2")){
            return "2";
        }
        if(prefs.getBoolean("isStage3")){
            return "3";
        }
        if(prefs.getBoolean("isStage4")){
            return "4";
        }
        return "1";
    }

    void createGrounds(){
        for(int i = 0;i<3;i++){
            Sprite ground = new Sprite(new Texture("StageBackgrounds/groundStage"+groundOfStage()+".png"));
            ground.setPosition(i*ground.getWidth(),0);
            grounds.add(ground);
        }
    }

    public String groundOfStage(){
        Preferences prefs = Gdx.app.getPreferences("Data");
        if(prefs.getBoolean("isStage1")) {
            return "1";
        }
        if(prefs.getBoolean("isStage2")){
            return "2";
        }
        if(prefs.getBoolean("isStage3")){
            return "3";
        }
        if(prefs.getBoolean("isStage4")){
            return "4";
        }
        return "1";
    }

    void drawBackgrounds(SpriteBatch batch){
        for (Sprite s: backgrounds){
            batch.draw(s,s.getX(),s.getY());
        }
    }

    void drawGround(SpriteBatch batch){
        for (Sprite g : grounds){
            batch.draw(g,g.getX(),g.getY());
        }
    }


    void moveBackgrounds(){
        for (Sprite bg : backgrounds){
            float x1 = bg.getX() - 2f;
            bg.setPosition(x1,bg.getY());

            if(bg.getX()+GameInfo.WIDTH + bg.getWidth() < mainCamera.position.x){
                 float x2 = bg.getX() + bg.getWidth()*backgrounds.size;
                 bg.setPosition(x2,bg.getY());
            }

        }

    }

    void moveGrounds(){
        for (Sprite ground : grounds){
            float x1 = ground.getX() -1f;
            ground.setPosition(x1,ground.getY());

            if(ground.getX() + GameInfo.WIDTH + (ground.getWidth()/2f) < mainCamera.position.x){
                float x2 = ground.getX() + ground.getWidth() * grounds.size;
                ground.setPosition(x2,ground.getY());
            }
        }
    }
    void createBullet(){
        Bullet b = new Bullet(world, GameInfo.WIDTH+DISTANCE_BETWEEN_BULLET);
        b.setMainCamera(mainCamera);
        bulletArray.add(b);
    }


    void drawBullets(SpriteBatch batch){
        for (Bullet bullet:bulletArray){
            bullet.drawBullet(batch);
        }
    }

    void updateBullet(){
        for (Bullet bullet:bulletArray){
           bullet.updateBullets();
        }
    }

    void moveBullets(){
        for(Bullet bullet:bulletArray){
            bullet.moveBullet();
        }
    }

    void createCoins(){
        Coins c = new Coins(world,GameInfo.WIDTH + DISTANCE_COIN_PLAY);
        c.setMainCamera(mainCamera);
        coinsArray.add(c);
    }

    void drawCoins(SpriteBatch batch){
        for (Coins c: coinsArray){
            c.drawCoins(batch);
        }
    }

    //delete it later if notwork;
    public void removeCollectable(){
        for (int i = 0 ; i< coinsArray.size ; i++){
            if(coinsArray.get(i).getFixture().getUserData() == "Remove") {
                coinsArray.get(i).getSprite().getTexture().dispose();
                coinsArray.get(i).changeFilter();
                coinsArray.removeIndex(i);
            }
        }
    }

    void updateCoins(){
        for (Coins u : coinsArray){
            u.updateCoins();
        }
    }

    void moveCoins(){
        for(Coins m : coinsArray){
            m.moveCoins();
        }
    }

    void stopAll(){
        for(Bullet b:bulletArray){
            b.stopBullet();
        }

        for(Coins c: coinsArray){
            c.stopCoins();
        }

    }

    void playerDie(){

        player.setIsAlive(false);
        stopAll();
        plus = 0;
//        hud.getStage().clear()
        hud.showScore();


        //delete it if not workings
        //create separate pref 14/11/60 > next
        Preferences prefs = Gdx.app.getPreferences("Data");
        if(prefs.getBoolean("isStage4")){
            int highCoinScoreStage4 =  prefs.getInteger("ScoreStage4");
            int highScoreStage4 = prefs.getInteger("ScorefarOfStage4");
            if(highCoinScoreStage4 < hud.getCoinScore()){
                prefs.putInteger("ScoreStage4",hud.getCoinScore());
                prefs.flush();
            }
            if(highScoreStage4 < hud.getScore()){
                prefs.putInteger("ScorefarOfStage4",hud.getScore());
                prefs.flush();
            }

            if(hud.getCoinScore()>4 || highCoinScoreStage4>4){
                System.out.print(highCoinScoreStage4);
                hud.createChampion();
                stopAll();
            }
            else {
                hud.createButton();
            }
        }
        else {
            //save  Coins score and scoreFar
            if(prefs.getBoolean("isStage1")){
                int highCoinScoreStage1 =  prefs.getInteger("ScoreStage1");
                int highScoreStage1 = prefs.getInteger("ScorefarOfStage1");

                if(highCoinScoreStage1 < hud.getCoinScore()){
                    prefs.putInteger("ScoreStage1",hud.getCoinScore());
                    prefs.flush();

                }
                if(highScoreStage1 < hud.getScore()){
                    prefs.putInteger("ScorefarOfStage1",hud.getScore());
                    prefs.flush();
                }

                System.out.println("Stage 1 : high score" + prefs.getInteger("ScorefarOfStage1"));

            }

            if(prefs.getBoolean("isStage2")){
                int highCoinScoreStage2 =  prefs.getInteger("ScoreStage2");
                int highScoreStage2 = prefs.getInteger("ScorefarOfStage2");
                if(highCoinScoreStage2 < hud.getCoinScore()){
                    prefs.putInteger("ScoreStage2",hud.getCoinScore());
                    prefs.flush();

                }
                if(highScoreStage2 < hud.getScore()){
                    prefs.putInteger("ScorefarOfStage2",hud.getScore());
                    prefs.flush();
                }

                System.out.println("Stage 2 : high score" + highScoreStage2);


            }

            if(prefs.getBoolean("isStage3")){
                int highCoinScoreStage3 =  prefs.getInteger("ScoreStage3");
                int highScoreStage3 = prefs.getInteger("ScorefarOfStage3");
                if(highCoinScoreStage3 < hud.getCoinScore()){
                    prefs.putInteger("ScoreStage3",hud.getCoinScore());
                    prefs.flush();

                }
                if(highScoreStage3 < hud.getScore()){
                    prefs.putInteger("ScorefarOfStage3",hud.getScore());
                    prefs.flush();
                }

                System.out.println("Stage 3 : high score" + highScoreStage3);
            }

            if(prefs.getBoolean("isStage4")){
                int highCoinScoreStage4 =  prefs.getInteger("ScoreStage4");
                int highScoreStage4 = prefs.getInteger("ScorefarOfStage4");
                if(highCoinScoreStage4 < hud.getCoinScore()){
                    prefs.putInteger("ScoreStage4",hud.getCoinScore());
                    prefs.flush();


                }
                if(highScoreStage4 < hud.getScore()){
                    prefs.putInteger("ScorefarOfStage4",hud.getScore());
                    prefs.flush();
                }

                System.out.println("Stage 4 : high score" + highScoreStage4);
            }
            //for reset highScore & coin
//        prefs.putInteger("ScoreStage1",10);
//        prefs.putInteger("ScoreStage2",10);
//        prefs.putInteger("ScoreStage3",0);
//        prefs.putInteger("ScoreStage4",0);
//        prefs.putInteger("ScorefarOfStage1",0);
//        prefs.putInteger("ScorefarOfStage2",0);
//        prefs.putInteger("ScorefarOfStage3",0);
//        prefs.putInteger("ScorefarOfStage4",0);
//
//

            //end //
            hud.createButton();
        }

        Gdx.input.setInputProcessor(hud.getStage());
        player.playerDie();


    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        drawBackgrounds(game.getBatch());
        drawGround(game.getBatch());
        player.drawPlayer(game.getBatch());

        //draw coins
        drawCoins(game.getBatch());
        //draw bullet
        drawBullets(game.getBatch());
        game.getBatch().end();
//        debugRenderer.render(world,debugCamera.combined);
        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();
        hud.getStage().act();

        player.updatePlayer();



        world.step(Gdx.graphics.getDeltaTime(),6,2);
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        player.getTexture().dispose();
        debugRenderer.dispose();
        coinsSound.dispose();
        diedSound.dispose();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture body1,body2;

        if(contact.getFixtureA().getUserData() == "Player"){
            body1 = contact.getFixtureA();
            body2 = contact.getFixtureB();
        }else{
            body1 = contact.getFixtureB();
            body2 = contact.getFixtureA();
        }

        if(body1.getUserData() == "Player" && body2.getUserData() == "Bullet"){
            if(player.getIsAlive()){
                System.out.println("die by bullet");
                diedSound.play();
                playerDie();
            }
        }

        if(body1.getUserData() == "Player" && body2.getUserData() == "Ground"){
            if(player.getIsAlive()){
                System.out.println("die by ground");
                diedSound.play();
                playerDie();
            }
        }

        if(body1.getUserData() == "Player" && body2.getUserData() == "Score"){
            hud.increaseScore();
            coinsSound.play();
            System.out.println("Coins");
            body2.setUserData("Remove");
            removeCollectable();

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}//gameplay











