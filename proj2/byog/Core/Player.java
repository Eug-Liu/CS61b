package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.Serializable;

public class Player extends MovingObject implements Serializable {
    final int UP = 0;
    final int DOWN = 1;
    final int LEFT = 2;
    final int RIGHT = 3;

    int dir = RIGHT;

    int speed = 1;
    TETile img = Tileset.PLAYER;
    TETile currPosItem = Tileset.SAND;
    TETile nextPosItem;

    int x;
    int y;
    boolean blocked = true;
    Sword sword;

    protected Player(int x, int y){
        this.x = x;
        this.y = y;
        placeObject(img, x, y);
    }

    protected void moveLeft() {
        nextPosItem = super.moveLeft(img, currPosItem, x, y, speed, blocked);
        if (nextPosItem != null) {
            x -= 1;
            currPosItem = nextPosItem;
        }
    }

    protected void moveRight() {
        nextPosItem = super.moveRight(img, currPosItem, x, y, speed, blocked);
        if (nextPosItem != null) {
            x += 1;
            currPosItem = nextPosItem;
        }
    }

    protected void moveUp() {
        nextPosItem = super.moveUp(img, currPosItem, x, y, speed, blocked);
        if (nextPosItem != null) {
            y += 1;
            currPosItem = nextPosItem;
        }
    }

    protected void moveDown() {
        nextPosItem = super.moveDown(img, currPosItem, x, y, speed, blocked);
        if (nextPosItem != null) {
            y -= 1;
            currPosItem = nextPosItem;
        }
    }

    protected void attack() {
        sword.chop();
    }

}
