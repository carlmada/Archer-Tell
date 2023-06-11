package cat.xtec.ioc.objects;

/*
CARLOS CASTAÑO DE LA IGLESIA.
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import java.util.Random;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class Bag extends Scrollable{

    //TODO: Classe que dona el bonus superior.
    //Variable per les col·lisions.
    private Circle collisionCircle;

    //Constructor.
    public Bag(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        // Creem el cercle de col·lisions
        collisionCircle = new Circle();
        setOrigin();
    }
    public void setOrigin() {
        this.setOrigin(width/2 + 1, height/2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Actualitzem el cercle de col·lisions (punt central de la bossa i el radi.
        collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y =  new Random().nextInt(Settings.GAME_HEIGHT - (int) height);
        setOrigin();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.bag,
                position.x,
                position.y,
                this.getOriginX(),
                this.getOriginY(),
                width,
                height,
                this.getScaleX(),
                this.getScaleY(),
                this.getRotation());
    }

    // TODO: Comprovació de les col·lisions.
    // Retorna true si hi ha col·lisió entre archer i les bosses.
    public boolean collides(Archer archer) {
        if (position.x <= archer.getX() + archer.getWidth()) {
            // Comprovem si han col·lisionat sempre i quan la bossa estigui a la mateixa alçada que l'arquer.
            return (Intersector.overlaps(collisionCircle, archer.getCollisionRect()));
        }
        return false;
    }

}
