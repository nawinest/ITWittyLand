package Coins;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.Random;

import helper.GameInfo;

/**
 * Created by Administrator on 9/11/2560.
 */

public class Coins {

    private World world;
    private Body body;

    private Sprite coin1;
    private Fixture fixture;
    private TextureAtlas coinAtlas;
    private Animation animation;
    private float elapsedTime;
    //delete it
    private final float DISTANCE = 420;

    private Random random = new Random();
    private OrthographicCamera mainCamera;
    public  Coins(World world,float x){
        this.world = world;
        createCoins(x, getRandomY());
        createAnimation();
    }

    void createCoins(float x,float y){
        coin1 = new Sprite(new Texture("Acoin/coin.jpg"));

        coin1.setPosition(x,y);


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(coin1.getX()/ GameInfo.PPM,coin1.getY()/GameInfo.PPM);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(3/GameInfo.PPM
                , (coin1.getHeight()/2f)/GameInfo.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameInfo.COINS;
        fixtureDef.isSensor = true;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Score");


        shape.dispose();

    }

    public void drawCoins(SpriteBatch batch){
//        batch.draw(coin1, coin1.getX() - coin1.getWidth()/2f
//                ,coin1.getY() - coin1.getHeight()/2f);
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true)
                ,coin1.getX() - coin1.getWidth()/2f
                ,coin1.getY() - coin1.getHeight()/2f);

    }



    public void updateCoins(){
        coin1.setPosition(body.getPosition().x * GameInfo.PPM
                ,body.getPosition().y*GameInfo.PPM);

    }


    void createAnimation(){
        coinAtlas = new TextureAtlas("Acoin/Coin.atlas");
        animation = new Animation(1f/7f, coinAtlas.getRegions());

    }

    public void moveCoins(){
        body.setLinearVelocity(-1f,0);

        if(coin1.getX() + GameInfo.WIDTH/2f + 120 < mainCamera.position.x){
            body.setActive(false);
        }
    }



    public void changeFilter(){
        Filter filter = new Filter();
        filter.categoryBits = GameInfo.DESTROYED;
        fixture.setFilterData(filter);
    }

    public void stopCoins(){
        body.setLinearVelocity(0,0);
    }


    public Fixture getFixture(){
        return fixture;
    }


    public Sprite getSprite(){
        return this.coin1;
    }
    //delete if not work najaa

    public void setMainCamera(OrthographicCamera mainCamera){
        this.mainCamera = mainCamera;
    }
    float getRandomY(){
        float max = GameInfo.HEIGHT/2f+350;
        float min = GameInfo.HEIGHT/2f-220;

        return  random.nextFloat()*(max-min)+min;
    }




}
