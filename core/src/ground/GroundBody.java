package ground;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import helper.GameInfo;

/**
 * Created by Administrator on 7/11/2560.
 */

public class GroundBody {
    //this class i made for declare a body of ground #itkmitl14
    private World world;
    private Body body;

    public GroundBody(World world, Sprite ground){
        this.world = world;
        creatGroundBody(ground);
    }


    void creatGroundBody(Sprite ground){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((ground.getWidth()/2f/ GameInfo.PPM), 0);

        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(ground.getWidth()/GameInfo.PPM,
                ground.getHeight()/GameInfo.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameInfo.GROUND;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Ground");
        shape.dispose();

    }


}//















