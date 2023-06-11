package cat.xtec.ioc.screens;

/*
CARLOS CASTAÑO DE LA IGLESIA.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import cat.xtec.ioc.ArcherTell;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class SplashScreen implements Screen {

    private Stage stage;
    private ArcherTell game;

    private Label.LabelStyle textStyle;
    private Label textLbl;

    //Variable preferences...per obtenir HIGHSCORE.
    //Variable int de HIGHSCORE.
    protected static Preferences prefs;
    private int highscore;
    //text del highscore.
    private Label textHighscore;


    public SplashScreen(ArcherTell game) {

        this.game = game;

        // Creem la càmera de les dimensions del joc
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);

        // Posant el paràmetre a true configurem la càmera per a
        // que faci servir el sistema de coordenades Y-Down
        camera.setToOrtho(true);

        // Creem el viewport amb les mateixes dimensions que la càmera
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        // Creem l'stage i assginem el viewport
        stage = new Stage(viewport);

        //TODO: Cambiem l'aparença de la pantalla splahScreen per adaptar-la al nostre joc.

        // Afegim el fons
        stage.addActor(new Image(AssetManager.background));

        //TODO: nou tipus de lletra.

        // Creem l'estil de l'etiqueta Archer i l'etiqueta
        textStyle = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label("ArcherTell", textStyle);

        // Creem el contenidor de l'etiqueta Archer per aplicar-li les accions
        Container container = new Container(textLbl);
        container.setTransform(true);
        container.center();
        container.setPosition(Settings.GAME_WIDTH / 2, 50);

        // Afegim les accions de escalar: primer es fa gran i després torna a l'estat original ininterrompudament
        container.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(1.5f, 1.5f, 1), Actions.scaleTo(1, 1, 2))));
        stage.addActor(container);

        //TODO: nova animació amb els nostre sprite.

        // Creem la imatge de l'arquer i li assignem el moviment en horitzontal
        Image archer = new Image(AssetManager.archer);
        float y = 90;
        archer.scaleBy(-0.25f);
        archer.addAction(Actions.repeat(RepeatAction.FOREVER,
                Actions.sequence(Actions.moveTo(0 - archer.getWidth(), y),
                Actions.moveTo(Settings.GAME_WIDTH, y, 8))));
        stage.addActor(archer);

        //TODO: apartat Preferences...
        // Inicialitzem les preferences
        prefs = Gdx.app.getPreferences("prefs");
        highscore = prefs.getInteger("highscore");

        //TODO: Mostrem el text del highscore.
        // Iniciem el Label highscore
        textHighscore = new Label("HIGHSCORE :  "+highscore, textStyle);

        // Creem el contenidor de l'etiqueta HIGHSCORE
        Container container2 = new Container(textHighscore);
        container2.setTransform(true);
        container2.center();
        container2.setPosition(Settings.GAME_WIDTH / 2, 200);
        stage.addActor(container2);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
        // Si es fa clic en la pantalla, canviem la pantalla
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport()));
            dispose();
        }
    }

    //Metodes requerits per la Interface Screen...
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
}
