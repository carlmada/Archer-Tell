package cat.xtec.ioc.helpers;

/*
CARLOS CASTAÑO DE LA IGLESIA.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {

    //TODO: Nou sprite amb totes les imatges del joc
    // Sprite Sheet
    public static Texture sheet;

    //fons
    public static TextureRegion background;

    //Arquer
    public static TextureRegion archer;
    public static TextureRegion[] archerWalking, archerDown, archerUp;
    public static Animation walkingAnim, downAnim, upAnim;

    // Apple
    public static TextureRegion[] apple;

    // Bossa
    public static TextureRegion bag ;

    //Flecha
    public static TextureRegion arrow;

    // Flecha clavada
    public static TextureRegion[] arrowShoot;
    public static Animation arrowShootAnimation;

    //Botó PAUSE
    public static TextureRegion buttonPAUSE;

    //TODO: nous sons i música per el joc.
    // Sons
    public static Music music;
    public static Music gameOver;
    public static Music impacteFlecha;
    public static Music hit;
    public static Sound soundArrow;

    //TODO: nou tipus de lletra
    // Font
    public static BitmapFont font;
    public static BitmapFont fontPoints;


    public static void load() {
        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        sheet = new Texture(Gdx.files.internal("sheet.png"));
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //TODO: Carreguem les noves textures i animacions del joc...

        // Sprites de l'arquer
        archer = new TextureRegion(sheet,0,270,75,130);
        archer.flip(false,true);

        //Caminant...
        archerWalking = new TextureRegion[6];
        for (int i = 0; i < archerWalking.length; i++) {
            archerWalking[i] = new TextureRegion(sheet,i*75,270,75,130);
            archerWalking[i].flip(false,true);
        }
        //Animacio caminant.
        walkingAnim = new Animation(0.2f,(Object[]) archerWalking);
        walkingAnim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);

        // UP
        archerUp = new TextureRegion[1];
        archerUp[0] = new TextureRegion(sheet, 75, 400, 75, 130);
        archerUp[0].flip(false, true);
        //Animacio UP.
        upAnim = new Animation(0.05f,(Object[])archerUp);
        upAnim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);

        // DOWN
        archerDown = new TextureRegion[1];
        archerDown[0] = new TextureRegion (sheet, 0, 400, 75, 130);
        archerDown[0].flip(false, true);
        //Animacio DOWN.
        downAnim = new Animation(0.05f,(Object[])archerDown);
        downAnim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);

        // Carreguem les 2 pomes.
        apple = new TextureRegion[2];
        apple[0] = new TextureRegion(sheet, 216, 400, 32, 32);
        apple[1] = new TextureRegion(sheet, 248, 400, 32, 32);
        apple[0].flip(false, true);
        apple[1].flip(false, true);

        //Carreguem la flecha.
        arrow = new TextureRegion(sheet,150,400,66,13);
        arrow.flip(false,true);

        //Carreguem la flecha clavada a la poma.
        arrowShoot = new TextureRegion[6];
        for (int i = 0; i < arrowShoot.length; i++) {
            arrowShoot[i] = new TextureRegion(sheet, 280, 400, 50, 32);
            arrowShoot[i].flip(false, true);
        }

        //Animació de la poma clavada.
        arrowShootAnimation = new Animation(0.5f, (Object[]) arrowShoot);
        arrowShootAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        //Carreguem la bossa.
        bag = new TextureRegion(sheet,380,400,34,34 );
        bag.flip(false,true);


        // Fons de pantalla
        background = new TextureRegion(sheet, 0, 0, 480, 270);
        background.flip(false, true);

        // Carreguem la imatge del boto PAUSE.
        buttonPAUSE = new TextureRegion(sheet, 330, 400, 50, 45);
        buttonPAUSE.flip(false,true);


        /******************************* Sounds *************************************/
        // Música del joc
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/mist_covered_mountains.mp3"));
        music.setVolume(0.2f);
        music.setLooping(true);

        //So de la flecha
        soundArrow = Gdx.audio.newSound(Gdx.files.internal("sounds/soundArrow.mp3"));

        //So impacte flecha.
        impacteFlecha = Gdx.audio.newMusic(Gdx.files.internal("sounds/impacteFlecha.mp3"));

        //So impacte bossa amb arquer.
        hit = Gdx.audio.newMusic(Gdx.files.internal("sounds/hit.ogg"));
        hit.setVolume(0.2f);

        //So de gameover.
        gameOver = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameover.mp3"));
        gameOver.setVolume(0.2f);


        /******************************* Text *************************************/
        // Font space
        FileHandle fontFile = Gdx.files.internal("fonts/mifont.fnt");
        font = new BitmapFont(fontFile, true);
        font.getData().setScale(0.4f);
        //Font de la puntuacio...
        FileHandle fontpoint = Gdx.files.internal("fonts/fontPoints.fnt");
        fontPoints = new BitmapFont(fontpoint,true);
        fontPoints.getData().setScale(0.4f);

    }

    public static void dispose() {
        // Alliberem els recursos gràfics i de audio
        sheet.dispose();
        impacteFlecha.dispose();
        soundArrow.dispose();
        music.dispose();
    }

}
