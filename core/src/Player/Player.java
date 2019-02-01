package Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.org.apache.xpath.internal.operations.Bool;

import helper.GameInfo;
import helper.GameManager;

/**
 * Created by Administrator on 7/11/2560.
 */

public class Player extends Sprite {
    private World world;
    private Body body;
    private Boolean isAlive = false;
    private Texture playerDied;

    public Player(World world, float x ,float y){
        super(new Texture("Player/" +
                GameManager.getInstance().getPlayer() + ".png"));
        playerDied = new Texture("Player/" +GameManager.getInstance().getPlayer() + "die.png");
        this.world = world;
        setPosition(x,y);
        createBody();

    }

    void createBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX()/ GameInfo.PPM,getY()/GameInfo.PPM);

        body = world.createBody(bodyDef);
        body.setFixedRotation(true);

        CircleShape shape = new CircleShape();
        shape.setRadius(((getHeight()/2f)-5)/GameInfo.PPM);
        FixtureDef fixtureDef  = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.filter.categoryBits = GameInfo.PLAYER;
        fixtureDef.filter.maskBits = GameInfo.GROUND | GameInfo.BULLET | GameInfo.COINS;


        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Player");

        shape.dispose();
        body.setActive(false);
    }

    public void activePlayer(){
        isAlive = true;
        body.setActive(true);
    }

    public void playerJump(){
        body.setLinearVelocity(0,3);



    }

    public void drawPlayer(SpriteBatch batch){
        batch.draw(this, getX()-getWidth()/2f,getY()-getHeight()/2f);


    }

    public void updatePlayer(){
        setPosition(body.getPosition().x * GameInfo.PPM
                ,body.getPosition().y* GameInfo.PPM);
    }

    public void setIsAlive(boolean isAlive){
        this.isAlive = isAlive;
    }
    public boolean getIsAlive(){
        return  isAlive;
    }

    public void playerDie(){
        this.setTexture(playerDied);
    }



}
