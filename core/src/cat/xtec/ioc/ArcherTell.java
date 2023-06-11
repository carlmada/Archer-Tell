package cat.xtec.ioc;

/*
ARCHERTELL
JUEGO DESARROLLADO EN LIBGDX PARA M8 IOC
CARLOS CASTAÑO DE LA IGLESIA.

 */

import com.badlogic.gdx.Game;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.screens.SplashScreen;

public class ArcherTell extends Game {

    @Override
    public void create() {
        // A l'iniciar el joc carreguem els recursos
        AssetManager.load();
        // I definim la pantalla d'splash com a pantalla
        setScreen(new SplashScreen(this));
    }

    // Cridem per descartar els recursos carregats.
    @Override
    public void dispose() {
        super.dispose();
        AssetManager.dispose();
    }
}