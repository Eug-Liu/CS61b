package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.Serializable;

public class Dragon extends MovingObject implements Serializable {
    final int UP = 0;
    final int DOWN = 1;
    final int LEFT = 2;
    final int RIGHT = 3;

    int dir = RIGHT;

    boolean doFire = false;
    boolean died = false;

    int speed = 2;
    TETile img = Tileset.FLOWER;
    TETile currPosItem = Tileset.MOUNTAIN;
    TETile nextPosItem;
    TETile fireImg = Tileset.GRASS;



    int x;
    int y;
    private final int fireDistance = 8;
    boolean blocked = false;
    Player enermy;
    int moveCd = 0;
    int fireCd = 0;
    int lives = 3;
    TETile[][] world;

    protected Dragon(int x, int y, Player e, TETile[][] w){
        this.x = x;
        this.y = y;
        enermy = e;
        world = w;
    }

    protected void placeDragon(){
        placeObject(img, x, y);
    }

    protected int xDistance() {
        return enermy.x - x;
    }

    protected int yDistance() {
        return enermy.y - y;
    }

    protected int abs(int x){
        if (x > 0)
            return x;
        return -x;
    }

    protected void move() {
        if (died) {
            return;
        }
        if (doFire) {
            attack();
            doFire = false;
            return;
        }

        if (moveCd != 0){
            moveCd -= 1;
            return;
        }

        if (abs(xDistance()) <= fireDistance && abs(yDistance()) <= fireDistance && fireCd ==0) {
            doFire = true;
            prepare();
            return;
        }

        switch (abs(xDistance())){

            case 0:
            case 1:
            case 2:
                break;
            case 3:
                speed = 1;
                if (xDistance() > 0)
                    moveRight();
                else moveLeft();
                speed = 2;
                break;
            default:
                if (xDistance() > 0)
                    moveRight();
                else moveLeft();
        }

        switch (abs(yDistance())){
            case 0:
            case 1:
            case 2:
                break;
            case 3:
                speed = 1;
                if (yDistance() > 0)
                    moveUp();
                else moveDown();
                speed = 2;
                break;
            default:
                if (yDistance() > 0)
                    moveUp();
                else moveDown();
        }

        if (fireCd != 0)
            fireCd -= 1;
    }

    protected void moveLeft() {
        nextPosItem = super.moveLeft(img, currPosItem, x, y, speed, blocked);
        if (nextPosItem != null) {
            x -= speed;
            currPosItem = nextPosItem;
            dir = LEFT;
        }
    }

    protected void moveRight() {
        nextPosItem = super.moveRight(img, currPosItem, x, y, speed, blocked);
        if (nextPosItem != null) {
            x += speed;
            currPosItem = nextPosItem;
            dir = RIGHT;
        }
    }

    protected void moveUp() {
        nextPosItem = super.moveUp(img, currPosItem, x, y, speed, blocked);
        if (nextPosItem != null) {
            y += speed;
            currPosItem = nextPosItem;
            dir = UP;
        }
    }

    protected void moveDown() {
        nextPosItem = super.moveDown(img, currPosItem, x, y, speed, blocked);
        if (nextPosItem != null) {
            y -= speed;
            currPosItem = nextPosItem;
            dir = DOWN;
        }
    }

    int temY;
    int temX;
    int currEnermyX;
    int currEnermyY;
    int factorX;
    int factorY;
    protected void prepare() {
        temY = y;
        temX = x;
        currEnermyX = enermy.x;
        currEnermyY = enermy.y;
        if (currEnermyX == temX)
            factorX = 0;
        else
            factorX = (currEnermyX - temX) / abs(currEnermyX - temX);
        if (currEnermyY == temY)
            factorY = 0;
        else
            factorY = (currEnermyY - temY) / abs(currEnermyY - temY);
    }

    protected void attack() {

        while (temX != currEnermyX || temY != currEnermyY) {
            if (temX != currEnermyX)
                temX += factorX;
            if (temY != currEnermyY)
                temY += factorY;

            if (!world[temX][temY].equals(Tileset.WATER) && !world[temX][temY].equals(Tileset.MOUNTAIN)) {
                world[temX][temY] = fireImg;
            }
        }
        moveCd = 2;
        fireCd = 2;

    }
}
