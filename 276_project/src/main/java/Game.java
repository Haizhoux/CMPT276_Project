import java.util.HashSet;
import java.util.Set;

public class Game {

    Game() {
        System.out.println("default constructor");
    }

    Game(int width, int height) {
        this._height = height;
        this._width = width;
        System.out.println("creating a new game with board size: " + width + "x" + height);
    }

    private Set<Crop> _gameObjects = new HashSet<Crop>();
    private int _width;
    private int _height;

    void addCrop(Crop c) {
        _gameObjects.add(c);
    }

    void printGameObjects() {
        for (Crop g : _gameObjects) {
            System.out.println(g);
        }
    }
}
