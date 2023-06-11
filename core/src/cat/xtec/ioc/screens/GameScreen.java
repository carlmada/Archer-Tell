package cat.xtec.ioc.screens;

/*
CARLOS CASTAÑO DE LA IGLESIA.
 */

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.helpers.InputHandler;
import cat.xtec.ioc.objects.Apple;
import cat.xtec.ioc.objects.Arrow;
import cat.xtec.ioc.objects.ArrowShoot;
import cat.xtec.ioc.objects.ScrollHandler;
import cat.xtec.ioc.objects.Archer;
import cat.xtec.ioc.utils.Settings;
import static cat.xtec.ioc.utils.Settings.SCORE_INCREASE;

public class GameScreen implements Screen {

    // Els estats del joc
    public enum GameState {

        //TODO: nou estat PAUSE.
        READY, RUNNING, GAMEOVER, PAUSE
    }

    private GameState currentState;

    //TODO: nous objectes del joc..
    //Amb anotacions.

    // Objectes necessaris
    public static Stage stage;
    private Archer archer;
    private ScrollHandler scrollHandler;

    //Array que tindrà les fleches que llançem.
    public static DelayedRemovalArray<Arrow> arrowList;

    //Array amb l'animació de la flecha amb la poma...
    public static ArrayList<ArrowShoot> flechazoAnim;

    // Encarregats de dibuixar elements per pantalla
    private ShapeRenderer shapeRenderer;
    private Batch batch;

    // Per controlar l'animació de la col·lisio de la poma
    private float arrowShootAnimTime = 0;

    // Preparem el textLayout per escriure text , text de pause
    // i missatge final.

    private GlyphLayout textLayout;
    //TODO: missatge de pausa.
    private GlyphLayout pauseText;
    //TODO:missatge final de nivell
    private GlyphLayout textFinal;

    //Text de puntuació .
    private Label.LabelStyle textStyle;
    private Label textScore;

    // Variables de PAUSE
    private static Image pause;

    //Variable preferences...
    protected static Preferences prefs;

    //Variable per la puntuació
    public static int points = 0;
    private int anotacioPrefs;

    public GameScreen(Batch prevBatch, Viewport prevViewport) {

        // Iniciem la música
        AssetManager.music.play();

        // Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Creem l'stage i assginem el viewport
        stage = new Stage(prevViewport, prevBatch);
        batch = stage.getBatch();

        // Creem l'arquer i la resta d'objectes
        archer = new Archer(Settings.ARCHER_STARTX,
                Settings.ARCHER_STARTY,
                Settings.ARCHER_WIDTH,
                Settings.ARCHER_HEIGHT);
        scrollHandler = new ScrollHandler();

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(archer);

        // Donem nom a l'Actor
        archer.setName("archer");

        // Creem la llista de fleches de la pantalla.
        arrowList = new DelayedRemovalArray<>();

        //Creem llista d'animacions de flecha-poma.
        flechazoAnim = new ArrayList<>();


        //TODO: Posem Text puntuació
        //Creem l'estil de l'etiqueta i l'etiqueta
        textStyle = new Label.LabelStyle(AssetManager.font, null);
        textScore = new Label("SCORE:   " + points, textStyle);
        textScore.setName("score");
        textScore.setPosition(20,5);
        stage.addActor(textScore);

        // Iniciem el GlyphLayout
        textLayout = new GlyphLayout();

        //Iniciem el test de pause
        pauseText = new GlyphLayout();
        pauseText.setText(AssetManager.font, "PAUSE");

        //Iniciem l'estat del joc.
        currentState = GameState.READY;

        // Assignem com a gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(new InputHandler(this));

        //TODO: apartat PAUSE
        // Inicialitzem el buttonPAUSE.
        pause = new Image(AssetManager.buttonPAUSE);
        pause.setName("Pausa");
        pause.setPosition(Settings.GAME_WIDTH-50,10);
        pause.setSize(30,25);
        stage.addActor(pause);

        //TODO: missatge final.
        textFinal = new GlyphLayout();

        //TODO: apartat Preferences...
        // Inicialitzem les preferences
        prefs = Gdx.app.getPreferences("prefs");
        anotacioPrefs = prefs.getInteger("highscore");

    }

    private void drawElements() {

        // Recollim les propietats del Batch de l'Stage
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Inicialitzem el shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Pintem l'arquer.
        shapeRenderer.rect(archer.getX(), archer.getY(), archer.getWidth(), archer.getHeight());

        // Recollim totes les pomes
        ArrayList<Apple> apples = scrollHandler.getApples();
        Apple apple;

        for (int i = 0; i < apples.size(); i++) {
            apple = apples.get(i);
            shapeRenderer.circle(apple.getX() + apple.getWidth() / 2,
                    apple.getY() + apple.getWidth() / 2,
                    apple.getWidth() / 2);
        }
        shapeRenderer.end();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Dibuixem tots els actors de l'stage
        stage.draw();

        // Depenent de l'estat del joc farem unes accions o unes altres
        switch (currentState) {
            case GAMEOVER:
                updateGameOver(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                stage.addActor(textScore);
                stage.addActor(pause);
                AssetManager.music.setVolume(0.2f);
                break;

            case READY:
                updateReady();
                break;

            //TODO:
            // CAS PAUSE...
            case PAUSE:
                // Dibuixem el text al centre de la pantalla
                batch.begin();
                AssetManager.font.draw(batch,
                        pauseText,
                        (Settings.GAME_WIDTH / 2) - pauseText.width / 2,
                        (Settings.GAME_HEIGHT / 2) - pauseText.height / 2);
                batch.end();
                textScore.remove();
                pause.remove();
                AssetManager.music.setVolume(0.05f);

            break;
        }
        //drawElements();
    }

    private void updateReady() {
        // Dibuixem el text al centre de la pantalla
        batch.begin();

        //Inicialitzem el contador de punts.
        points = 0 ;
        textScore.setText("SCORE: "+points);

        //TODO: LLegim les preferences.
        // Llegim anotacio de "prefs" i mostrem en pantalla.
        anotacioPrefs = prefs.getInteger("highscore");

        textLayout.setText(AssetManager.font, "Touch for START");
        AssetManager.font.draw(batch,
                textLayout,
                (Settings.GAME_WIDTH / 2) - textLayout.width / 2,
                Settings.GAME_HEIGHT /2);

        batch.end();

    }

    private void updateRunning(float delta) {
        stage.act(delta);

        //TODO: Comprovem les col·lisions entres elements de la pantalla.
        if (scrollHandler.collides(archer)) {
            // si les pomes col·lisionen amb l'arquer reproduïm so de finalització i posem l'estat a GameOver
            AssetManager.gameOver.play();
            stage.getRoot().findActor("archer").remove();
            textLayout.setText(AssetManager.font, "ENDGAME :(");
            currentState = GameState.GAMEOVER;
        }

        // TODO: Comprovem els llençaments de les fleches.
        //  Comprovem les fleches llençades.
        if(arrowList.size>0) {
            for (Arrow arrow : arrowList) {
                Apple apple = scrollHandler.collides(arrow);
                if (apple != null) {
                    // Si hi ha hagut col·lisió:
                    // Augmentem la puntuació
                    // Reproduïm un so
                    // Fem una animació i eliminem la flecha i la poma
                    points = points+10;
                    textScore.setText("SCORE: "+points);

                    AssetManager.impacteFlecha.play();

                    batch.begin();
                    batch.draw((TextureRegion) AssetManager.arrowShootAnimation.getKeyFrame(arrowShootAnimTime,
                                    false),
                            arrow.getX() + arrow.getWidth() / 2 - 32,
                            arrow.getY() + arrow.getHeight() / 2 - 32,
                            50,
                            32);
                    //Esborrem la flecha..
                    removeActor(arrow);
                    arrowList.removeValue(arrow, true);
                    arrow.remove();
                    //esborrem la poma..
                    scrollHandler.removeApple(apple);
                    arrowShootAnimTime += delta;
                    batch.end();

                    break;
                }
                arrowShootAnimTime += delta;
            }
        }

        // Eliminem les fleches que surten de la pantalla per la dreta.
        for (Arrow arrow : arrowList) {
            if(arrow.isRightOfScreen()) {
                removeActor(arrow);
                arrowList.removeValue(arrow, true);
                arrow.remove();
            }
        }

        // Si l'arquer col·lisiona amb les bosses
        if (scrollHandler.collidesBosses(archer)) {
            //Incrementem la puntuació...
            points = points + SCORE_INCREASE;
            textScore.setText("SCORE: "+points);
            AssetManager.hit.play();
        }
    }

    private void updateGameOver(float delta) {
        stage.act(delta);
        //TODO: Mostrem missatge final segons la puntuació.
        //Variable string necessaria.
        String missatgeFinal = "";
        if(points<100){
            missatgeFinal = "YOUR SKILL: INEXPERT  :(";
        }else if ( (points>=100)&&(points<150) ){
            missatgeFinal = "YOUR SKILL: CORRECT  :|";
        }else if(points>=150){
            missatgeFinal = "YOUR SKILL: AVERAGE  :)";
        }

        //Li donem format el text.
        textFinal.setText(AssetManager.font,missatgeFinal);

        batch.begin();
        AssetManager.font.draw(batch,
                textLayout,
                (Settings.GAME_WIDTH - textLayout.width) / 2,
                (Settings.GAME_HEIGHT - textLayout.height) / 2);

        AssetManager.font.draw(batch,
                textFinal,
                (Settings.GAME_WIDTH - textFinal.width) / 2,
                (Settings.GAME_HEIGHT - textFinal.height) / 2+35);

        batch.end();

        //Si la puntuació es major que HIGHSCORE
        //La gravem a prefs.
        if (points>anotacioPrefs){
            prefs.putInteger("highscore",points);
        }

        //TODO: fem persistent la puntuació.
        prefs.flush();
        arrowShootAnimTime += delta;

    }

    public void reset() {
        // Posem el text d'inici
        textLayout.setText(AssetManager.font, "ARE YOU READY??");
        // Cridem als restart dels elements.
        archer.reset();
        scrollHandler.reset();

        //Eliminem les fleches llençades...
        for (Arrow arrow : arrowList){
            arrow.remove();
            arrowList.clear();
        }

        // Posem l'estat a 'Ready'
        currentState = GameState.READY;
        // Afegim l'arquer a l'stage
        stage.addActor(archer);
        // Posem a 0 les variables per controlar el temps jugat i l'animació de l'explosió
        arrowShootAnimTime = 0.0f;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {


    }

    public Archer getArcher() {
        return archer;
    }

    public static Stage getStage() {
        return stage;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

}
