package cat.xtec.ioc.objects;

/*
CARLOS CASTAÑO DE LA IGLESIA.
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import java.util.Random;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

public class Apple extends Scrollable {

    //TODO: Classe poma com objectes enemics
    // Creem el cercle de col·lisions
    private Circle collisionCircle;

    Random r;

    int assetApple;

    public Apple(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        // Inicialitzem el cercle de col·lisions
        collisionCircle = new Circle();

        /* Accions */
        r = new Random();
        assetApple = r.nextInt(2);

        setOrigin();

        //Eliminem la rotació i repetició.
        // Rotacio
        RotateByAction rotateAction = new RotateByAction();
        rotateAction.setAmount(0f);
        rotateAction.setDuration(0f);

        // Accio de repetició
        RepeatAction repeat = new RepeatAction();
        repeat.setAction(rotateAction);
        repeat.setCount(RepeatAction.FOREVER);

        this.addAction(repeat);

    }

    public void setOrigin() {
        this.setOrigin(width/2 + 1, height/2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Actualitzem el cercle de col·lisions (punt central de la poma i el radi).
        collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.2f);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        // Obtenim un número al·leatori entre MIN i MAX
        float newSize = Methods.randomFloat(Settings.MIN_APPLE, Settings.MAX_APPLE);

        // Modificarem l'alçada i l'amplada segons l'al·leatori anterior
        width = height = 34 * newSize;

        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y =  new Random().nextInt(Settings.GAME_HEIGHT - (int) height);

        assetApple = r.nextInt(2);
        setOrigin();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.apple[assetApple],
                position.x,
                position.y,
                this.getOriginX(),
                this.getOriginY(),
                width, height,
                this.getScaleX(),
                this.getScaleY(),
                this.getRotation());
    }

    // TODO: comprovació de col·lisions...
    // Retorna true si hi ha col·lisió entre archer i les pomes.
    public boolean collides(Archer archer) {
        if (position.x <= archer.getX() + archer.getWidth()) {
            // Comprovem si han col·lisionat sempre i quan la poma estigui a la mateixa alçada que l'arquer.
            return (Intersector.overlaps(collisionCircle, archer.getCollisionRect()));
        }
        return false;
    }

    //Retorna true si hi ha col·lisió entre les fleches i les pomes.
    public boolean collides(Arrow arrow) {
        if (position.x <= arrow.getX() + arrow.getWidth()) {
            // Comprovem si han col·lisionat sempre i quan la poma estigui a la mateixa alçada que la flecha
            return (Intersector.overlaps(collisionCircle, arrow.getCollisionRectangle()));
        }
        return false;
    }

}
