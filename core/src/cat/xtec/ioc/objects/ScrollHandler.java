package cat.xtec.ioc.objects;

/*
CARLOS CASTAÑO DE LA IGLESIA.
 */

import com.badlogic.gdx.scenes.scene2d.Group;
import java.util.ArrayList;
import java.util.Random;
import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

public class ScrollHandler extends Group {

    // TODO: Elements del joc de tipus Scrollable.
    // background, pomes, bosses.

    // Fons de pantalla
    Background bg, bg_back;

    // Pomes
    int numApples;
    private final ArrayList<Apple> apples;

    // Bosses.
    int numBosses;
    private final ArrayList<Bag> bags;

    // Objecte Random
    Random r;

    public ScrollHandler() {

        // Creem els dos fons
        bg = new Background(0, 0,
                Settings.GAME_WIDTH * 2,
                Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(), 0,
                Settings.GAME_WIDTH * 2,
                Settings.GAME_HEIGHT, Settings.BG_SPEED);

        // Afegim els fons al grup
        addActor(bg);
        addActor(bg_back);

        // Creem l'objecte random
        r = new Random();

        // Comencem amb 3 pomes
        numApples = 3;

        // Creem l'ArrayList
        apples = new ArrayList<>();

        // Definim una mida aleatòria entre el mínim i el màxim
        float newSize = Methods.randomFloat(Settings.MIN_APPLE, Settings.MAX_APPLE) * 34;

        // Afegim la primera poma a l'Array i al grup
        Apple apple = new Apple(Settings.GAME_WIDTH+Settings.APPLE_GAP, r.nextInt(Settings.GAME_HEIGHT - (int) newSize),
                newSize,
                newSize,
                Settings.APPLE_SPEED);
        apples.add(apple);
        addActor(apple);

        // Des de la segona poma fins a l'ultima
        for (int i = 1; i < numApples; i++) {
            // Creem la mida al·leatòria
            newSize = Methods.randomFloat(Settings.MIN_APPLE, Settings.MAX_APPLE) * 34;
            // Creem la poma.
            apple = new Apple(apples.get(apples.size() - 1).getTailX() + Settings.APPLE_GAP,
                    r.nextInt(Settings.GAME_HEIGHT - (int) newSize),
                    newSize,
                    newSize,
                    Settings.APPLE_SPEED);
            // Afegim la poma a l'ArrayList
            apples.add(apple);
            // Afegim la poma al grup d'actors
            addActor(apple);
        }

        // Comencem amb 2 bosses
        numBosses = 2;
        // Creem l'ArrayList
        bags = new ArrayList<>();
        //Afegim la primera bossa
        Bag bag = new Bag(Settings.GAME_WIDTH ,
                r.nextInt(Settings.GAME_HEIGHT - 34),
                34,
                34,
                Settings.BAG_SPEED);
        bags.add(bag);
        addActor(bag);

        //Afegim la resta de bosses.
        for (int i = 1; i < numBosses; i++) {
            bag = new Bag(bags.get(bags.size() - 1).getTailX() + Settings.BAG_GAP,
                    r.nextInt(Settings.GAME_HEIGHT - 34),
                  34,
                  34,
                  Settings.BAG_SPEED);
          bags.add(bag);
          addActor(bag);
        }


    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Si algun element està fora de la pantalla, fem un reset de l'element.
        // Fons de pantalla...
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailX());

        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());
        }
        // Pomes...
        for (int i = 0; i < apples.size(); i++) {
            Apple apple = apples.get(i);
            if (apple.isLeftOfScreen()) {
                if (i == 0) {
                    apple.reset(apples.get(apples.size() - 1).getTailX() + Settings.APPLE_GAP);
                } else {
                    apple.reset(apples.get(i-1).getTailX()+ Settings.APPLE_GAP);
                }
            }
        }

        // Bosses...
        for (int i = 0; i < bags.size(); i++) {
            Bag bag = bags.get(i);
            if (bag.isLeftOfScreen()) {
                if (i == 0) {
                    bag.reset(bags.get(bags.size() - 1).getTailX() + Settings.BAG_GAP);
                } else {
                    bag.reset(bags.get(i-1).getTailX() + Settings.BAG_GAP);
                }
            }
        }

    }

    // TODO: comprovació de col·lisions...
    // Comprovem les col·lisions entre cada poma i l'arquer
    public boolean collides(Archer archer) {
        for (Apple apple : apples) {
            if (apple.collides(archer)) {
                return true;
            }
        }
        return false;
    }

    //Comprovem les col·lisions entre cada poma i les fleches.
    public Apple collides(Arrow arrow) {
        for (Apple apple : apples) {
            if (apple.collides(arrow)) {
                return apple;
            }
        }
        return null;
    }

    // Comprovem les col·lisions entre l'arquer i les bosses.
    public boolean collidesBosses(Archer archer){
        // Comprovem les col·lisions entre cada bossa i l'arquer
        for (Bag bag  : bags) {
            if (bag.collides(archer)) {
                bags.remove(bag);
                bag.remove();

                //Afegim una nova bossa a l'array.
                bag = new Bag(bags.get(bags.size() - 1).getTailX() + Settings.BAG_GAP,
                        r.nextInt(Settings.GAME_HEIGHT - 34),
                        34,
                        34,
                        Settings.BAG_SPEED);


                bags.add(bag);
                addActor(bag);
                return true;
            }
        }
        return false;
    }


    // Metode per esborrar les Pomes.
    public void removeApple(Apple apple){
        apples.remove(apple);
        apple.remove();

        // Afegim una nova poma a l'array de pomes.
        float newSize = Methods.randomFloat(Settings.MIN_APPLE, Settings.MAX_APPLE) * 34;
        // Creem la poma.
        apple = new Apple(apples.get(apples.size() - 1).getTailX() + Settings.APPLE_GAP,
                r.nextInt(Settings.GAME_HEIGHT - (int) newSize),
                newSize,
                newSize,
                Settings.APPLE_SPEED);
        // Afegim la poma a l'ArrayList
        apples.add(apple);

        // Afegim la poma al grup d'actors
        addActor(apple);

    }

    public void reset() {

        // Posem la primera poma fora de la pantalla per la dreta
        apples.get(0).reset(Settings.GAME_WIDTH);
        // Calculem les noves posicions de la resta de pomes.
        for (int i = 1; i < apples.size(); i++) {
            apples.get(i).reset(apples.get(i - 1).getTailX() + Settings.APPLE_GAP);
        }

        // Posem la primera bossa fora de la pantalla per la dreta
        bags.get(0).reset(Settings.GAME_WIDTH);
        //Afegim la resta de bosses
        for (int i = 1; i < bags.size() ; i++) {
            bags.get(i).reset(bags.get(i-1).getTailX()+ Settings.BAG_GAP);
        }

    }

    public ArrayList<Apple> getApples() {
        return apples;
    }

}