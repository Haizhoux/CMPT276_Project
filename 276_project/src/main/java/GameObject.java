public class GameObject {

    GameObject() {

        System.out.println("default constructor");
    }
    GameObject(int x, int y) {
        this._x = x;
        this._y = y;
        System.out.println("creating a gameObject at:" + x + "," + y);
    }

    int _x;
    int _y;

}
