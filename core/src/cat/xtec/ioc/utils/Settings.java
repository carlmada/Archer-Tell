package cat.xtec.ioc.utils;

/*
CARLOS CASTAÑO DE LA IGLESIA.
 */

public class Settings {

    // TODO: Constants necessaries del joc.
    //  Mida del joc, s'escalarà segons la necessitat
    public static final int GAME_WIDTH = 480;
    public static final int GAME_HEIGHT = 270;

    // Propietats de l'arquer
    public static final int ARCHER_VELOCITY = 75;
    public static final int ARCHER_WIDTH = 37;
    public static final int ARCHER_HEIGHT = 65;
    public static final float ARCHER_STARTX = 20;
    public static final float ARCHER_STARTY = GAME_HEIGHT/2 - ARCHER_HEIGHT /2;

    // Rang de valors per canviar la mida de la poma.
    public static final float MAX_APPLE = 1.0f;
    public static final float MIN_APPLE = 0.7f;

    // Configuració Scrollable
    public static final int APPLE_SPEED = -150;
    public static final int APPLE_GAP = 200;
    public static final int BG_SPEED = -100;

    //Propietats de les bosses.
    public static final int BAG_SPEED = -400;
    public static final int BAG_GAP = 400;

    //Propietats de la flecha.
    public static final float ARROW_WIDTH = 66f;
    public static final float ARROW_HEIGHT = 15f;
    public static final float ARROW_VELOCITY = 100;

    // Variables de puntuació
    public static final int SCORE_INCREASE = 100;

}
