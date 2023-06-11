package cat.xtec.ioc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import cat.xtec.ioc.ArcherTell;
import cat.xtec.ioc.utils.Settings;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "ArcherTell";
        config.width = Settings.GAME_WIDTH * 2;
        config.height = Settings.GAME_HEIGHT * 2;

        new LwjglApplication(new ArcherTell(), config);
    }
}
