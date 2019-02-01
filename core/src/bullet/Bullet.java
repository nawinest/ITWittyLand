package bullet;

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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import Player.Player;
import helper.GameInfo;

/**
 * Created by Administrator on 7/11/2560.
 */

public class Bullet{
    private World world;
    private Body body1,body2;

    private Sprite bullet1,bullet2;
    private TextureAtlas bulletAtlas;
    private Animation animation;
    private float elapsedTime;

    private final float DISTANCE_BETWEEN_BULLET = 150f;


    private Random random = new Random();
    private OrthographicCamera mainCamera;

    public Bullet(World world,float x){
        this.world = world;
        createBullet(x, getRandomYforBullet());
        createAnimation();
    }

    void createBullet(float x,float y){
        bullet1 = new Sprite(new Texture("attack/rocket.jpg"));
        bullet2 = new Sprite(new Texture("attack/rocket.jpg"));

        bullet1.setPosition(x -getRandomXPlusforBullet(), y + DISTANCE_BETWEEN_BULLET);
        bullet2.setPosition(x+getRandomXPlusforBullet() , y - DISTANCE_BETWEEN_BULLET);


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        //create body for bullet1
        bodyDef.position.set(bullet1.getX()/GameInfo.PPM,
                bullet1.getY()/GameInfo.PPM);
        body1 = world.createBody(bodyDef);
        body1.setFixedRotation(false);
        //create body for bullet2
        bodyDef.position.set(bullet2.getX()/GameInfo.PPM,
                bullet2.getY()/GameInfo.PPM);
        body2 = world.createBody(bodyDef);
        body2.setFixedRotation(false);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(((bullet1.getWidth()/2f)-5)/ GameInfo.PPM,
                ((bullet1.getHeight()/2f)-5)/ GameInfo.PPM);
        // i can use same shape because it same size

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameInfo.BULLET;

        Fixture fixture1 = body1.createFixture(fixtureDef); //for b-1
        Fixture fixture2 = body2.createFixture(fixtureDef); //for b-2
        fixture1.setUserData("Bullet");
        fixture2.setUserData("Bullet");
        shape.dispose();

    }

    public  void drawBullet(SpriteBatch batch){

//        batch.draw(bullet1,bullet1.getX() - bullet1.getWidth()/2f,
//                bullet1.getY() - bullet1.getHeight()/2f);
//
//        batch.draw(bullet2,bullet2.getX() - bullet2.getWidth()/2f,
//                bullet2.getY() - bullet2.getHeight()/2f);

        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true)
                ,bullet1.getX() - bullet1.getWidth()/2f,
                bullet1.getY() - bullet1.getHeight()/2f);
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true)
                ,bullet2.getX() - bullet2.getWidth()/2f,
                bullet2.getY() - bullet2.getHeight()/2f);

    }

    public void updateBullets(){
        //decare base on its body
        bullet1.setPosition(body1.getPosition().x * GameInfo.PPM,
                body1.getPosition().y * GameInfo.PPM);
        bullet2.setPosition(body2.getPosition().x * GameInfo.PPM,
                body2.getPosition().y * GameInfo.PPM);

    }

    void createAnimation(){
        bulletAtlas = new TextureAtlas("attack/RocketF.atlas");
        animation = new Animation(1f/7f, bulletAtlas.getRegions());

    }


    public void moveBullet(){
        body1.setLinearVelocity(-1,0);
        body2.setLinearVelocity(-1,0);

        if(bullet1.getX() + (GameInfo.WIDTH/2f) +200 < mainCamera.position.x){
//            System.out.println("Out");
            body1.setActive(false);
            body2.setActive(false);
        }
    }

    public void stopBullet(){
        body1.setLinearVelocity(0,0);
        body2.setLinearVelocity(0,0);
    }

    public void setMainCamera(OrthographicCamera mainCamera){
        this.mainCamera = mainCamera;
    }

    float getRandomYforBullet(){
        //find random for y-cor set to bullet #it14kmitl
        float max = GameInfo.HEIGHT/2f+350;
        float min = GameInfo.HEIGHT/2f;
        return random.nextFloat() * (max-min)+min;
    }

    float getRandomXPlusforBullet(){
        //find random for y-cor set to bullet #it14kmitl
        float max = 50;
        float min = 25;
        return random.nextFloat() * (max-min)+min;
    }




}







