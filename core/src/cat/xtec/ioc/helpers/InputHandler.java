package cat.xtec.ioc.helpers;

/*
CARLOS CASTAÑO DE LA IGLESIA.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import cat.xtec.ioc.objects.Archer;
import cat.xtec.ioc.screens.GameScreen;

public class InputHandler implements InputProcessor {

    // Enter per a la gestió del moviment d'arrastrar
    int previousY = 0;

    // Objectes necessaris
    private Archer archer;
    private GameScreen screen;
    private Vector2 stageCoord;
    private Stage stage;

    public InputHandler(GameScreen screen) {
        // Obtenim tots els elements necessaris
        this.screen = screen;
        archer = screen.getArcher();
        stage = screen.getStage();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        switch (screen.getCurrentState()) {
            case READY:
                // Si fem clic comencem el joc
                screen.setCurrentState(GameScreen.GameState.RUNNING);
                break;

            case RUNNING: //Estat iniciat del joc.
                previousY = screenY;
                stageCoord = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                Actor actorHit = stage.hit(stageCoord.x, stageCoord.y, true);
                if (actorHit != null) {
                    Gdx.app.log("HIT", actorHit.getName());
                    // TODO: Llençament de la flecha ...
                    //  Si cliquem a l'arquer, disparem flecha...
                    if(actorHit.getName().equals("archer")){
                        screen.getArcher().disparaFlecha();
                    }
                    // TODO: Pausa del joc ...
                    //  Si cliquem al botó PAUSE...
                    if(actorHit.getName().equals("Pausa")){
                        screen.setCurrentState(GameScreen.GameState.PAUSE);
                    }

                }
                break;

            // Si l'estat és GameOver tornem a iniciar el joc
            case GAMEOVER:
                screen.reset();
                break;

            case PAUSE:
                // Per tornar a reprendre el joc...
                screen.setCurrentState(GameScreen.GameState.RUNNING);
                break;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Quan deixem anar el dit acabem un moviment
        // i posem l'arquer en estat normal
        archer.goStraight();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Posem un umbral per evitar gestionar events quan el dit està quiet
        if (Math.abs(previousY - screenY) > 2)
            // Si la Y és major que la que tenim
            // guardada és que va cap avall
            if (previousY < screenY) {
                archer.goDown();
            } else {
                // En cas contrari cap a dalt
                archer.goUp();
            }
        // Guardem la posició de la Y
        previousY = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
