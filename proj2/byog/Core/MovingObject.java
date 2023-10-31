package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class MovingObject {
    static final int HEIGHT = Game.HEIGHT;
    static final int WIDTH = Game.WIDTH;
    static TETile world[][];

    protected static void init(TETile[][] World){
        world = World;
    }

    protected TETile placeObject(TETile img, int x, int y){
        TETile nextPosItem = world[x][y];
        world[x][y] = img;
        return nextPosItem;
    }

    protected TETile moveLeft(TETile img, TETile currPosItem, int x, int y, int speed, boolean blocked){
        if (x - speed < 0)
            return null;
        if (blocked && world[x - speed][y].equals(Tileset.MOUNTAIN))
            return null;

        world[x][y] = currPosItem;
        TETile nextPosItem = world[x - speed][y];
        world[x - speed][y] = img;
        return nextPosItem;
    }

    protected TETile moveRight(TETile img, TETile currPosItem, int x, int y, int speed, boolean blocked){
        if (x + speed > WIDTH)
            return null;
        if (blocked && world[x + speed][y].equals(Tileset.MOUNTAIN))
            return null;

        world[x][y] = currPosItem;
        TETile nextPosItem = world[x + speed][y];
        world[x + speed][y] = img;
        return nextPosItem;
    }

    protected TETile moveUp(TETile img, TETile currPosItem, int x, int y, int speed, boolean blocked){
        if (y + speed > HEIGHT)
            return null;
        if (blocked && world[x][y + speed].equals(Tileset.MOUNTAIN))
            return null;

        world[x][y] = currPosItem;
        TETile nextPosItem = world[x][y + speed];
        world[x][y + speed] = img;
        return nextPosItem;

    }

    protected TETile moveDown(TETile img, TETile currPosItem, int x, int y, int speed, boolean blocked){
        if (y - speed < 0)
            return null;

        if (blocked && world[x][y - speed].equals(Tileset.MOUNTAIN))
            return null;

        world[x][y] = currPosItem;
        TETile nextPosItem = world[x][y - speed];
        world[x][y - speed] = img;
        return nextPosItem;

    }

}
