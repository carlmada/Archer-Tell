package cat.xtec.ioc.objects;

/*
CARLOS CASTAÑO DE LA IGLESIA.
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class Arrow extends Scrollable {
    // TODO: Classe Arrow de l'element que llençem
    // que s'ha d'implementar...

    //creem el rectangle de colisions.
    private Rectangle collisionRectangle ;

    public Arrow(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        //Creem el rectangle de colisions
        collisionRectangle = new Rectangle();
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        //Verifiquem si la flecha surt perl costat dret de la pantalla.
        if(position.x > Settings.GAME_WIDTH){
            rightOfScreen = true;
        }
        // Actualitzem el rectangle de col·lisions de la flecha
        collisionRectangle.set(position.x, position.y,width,height );

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
        batch.draw( AssetManager.arrow, position.x,position.y,this.getOriginX(),this.getOriginY(),
                Settings.ARROW_WIDTH/2, Settings.ARROW_HEIGHT/2,
                this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    public Rectangle getCollisionRectangle(){
        return collisionRectangle;
    }

}

