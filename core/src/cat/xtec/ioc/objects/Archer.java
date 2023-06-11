package cat.xtec.ioc.objects;

/*
CARLOS CASTAÑO DE LA IGLESIA.
 */

import static cat.xtec.ioc.screens.GameScreen.arrowList;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.screens.GameScreen;
import cat.xtec.ioc.utils.Settings;

public class Archer extends Actor {

    // TODO: Class ARCHER amb la seva animació de moviment.
    // Distintes posicions de l'arquer, caminant, pujant i baixant
    public static final int ARCHER_WALKING = 0;
    public static final int ARCHER_UP = 1;
    public static final int ARCHER_DOWN = 2;

    // Paràmetres de l'arquer
    private Vector2 position;
    private int width, height;
    private int direction;

    private Rectangle collisionRect;

    private Arrow arrow;
    private GameScreen gameScreen;
    private Stage stage ;
    private float runTime;

    public Archer(float x, float y, int width, int height) {

        // Inicialitzem els arguments segons la crida del constructor
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        // Inicialitzem l'arquer l'estat normal
        direction = ARCHER_WALKING;

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

        // Per a la gestió de hit
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);

    }

    //Accions de l'arquer
    public void act(float delta) {
        super.act(delta);
        runTime += delta;

        // Movem l'arquer depenent de la direcció controlant que no surti de la pantalla
        switch (direction) {
            case ARCHER_UP: // 42 per correcció altura del fons..
                if (this.position.y - Settings.ARCHER_VELOCITY * delta >= 42) {
                    this.position.y -= Settings.ARCHER_VELOCITY * delta;
                }
                break;
            case ARCHER_DOWN: // -40 per correcció altura del fons..
                if (this.position.y + height + Settings.ARCHER_VELOCITY * delta <= Settings.GAME_HEIGHT-40) {
                    this.position.y += Settings.ARCHER_VELOCITY * delta;
                }
                break;
            case ARCHER_WALKING:
                break;
        }
        collisionRect.set(position.x, position.y + 3, 37, 65);
        setBounds(position.x, position.y, width, height);
    }

    // Getters dels atributs principals
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Canviem la direcció de l'arquer: Puja
    public void goUp() {
        direction = ARCHER_UP;
    }

    // Canviem la direcció de l'arquer: Baixa
    public void goDown() {
        direction = ARCHER_DOWN;
    }

    // Posem l'arquer al seu estat original
    public void goStraight() {
        direction = ARCHER_WALKING;
    }

    // Obtenim el TextureRegion depenent de la posició de l'arquer
    public Animation getArcherTexture() {
        switch (direction) {
            case ARCHER_WALKING:
                return AssetManager.walkingAnim;
            case ARCHER_UP:
                return AssetManager.upAnim;
            case ARCHER_DOWN:
                return AssetManager.downAnim;
            default:
                return AssetManager.walkingAnim;
        }
    }

    public void reset() {
        // La posem a la posició inicial i a l'estat normal
        position.x = Settings.ARCHER_STARTX;
        position.y = Settings.ARCHER_STARTY;
        direction = ARCHER_WALKING;
        collisionRect = new Rectangle();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw((TextureRegion) getArcherTexture().getKeyFrame(runTime), position.x, position.y, width , height );

    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }

    public void disparaFlecha() {
        //TODO: Llençament de flecha..
        System.out.println("disparo flecha...");
        //Afegim la flecha...
        arrow = new Arrow(  position.x+this.getWidth(), position.y+this.getHeight()/2,
                Settings.ARROW_WIDTH-10, Settings.ARROW_HEIGHT,Settings.ARROW_VELOCITY);
        stage = GameScreen.getStage();
        stage.addActor(arrow);
        arrowList.add(arrow);
        AssetManager.soundArrow.play();
    }


}
