package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Sword implements Serializable {
    final int UP = 0;
    final int DOWN = 1;
    final int LEFT = 2;
    final int RIGHT = 3;

    TETile img = Tileset.TREE;
    TETile chopImg = Tileset.WALL;
    Clean clean;

    int x;
    int y;
    TETile world[][];
    protected Sword(TETile World[][], Clean Clean, int x, int y) {
        this.x = x;
        this.y = y;
        world = World;
        clean = Clean;
    }

    Player holder;

    protected boolean isPicked(Player player) {
        return x == player.x && y == player.y;
    }

    protected int xDis(Player player) {
        return abs(player.x - x);
    }

    protected int yDis(Player player) {
        return abs(player.y - y);
    }

    protected void placeSword(TETile[][] world) {
        world[x][y] = img;
    }

    protected int abs(int x) {
        if (x < 0)
            return -x;
        return x;
    }

    protected void pick(Player player) {
        holder = player;
        holder.currPosItem = Tileset.SAND;
        holder.sword = this;
    }

    protected void chop() {
        x = holder.x;
        y = holder.y;

        switch (holder.dir){
            case UP:
                drawChop(x, y + 1, true);
                break;
            case DOWN:
                drawChop(x, y - 1, true);
                break;
            case LEFT:
                drawChop(x - 1, y, false);
                break;
            case RIGHT:
                drawChop(x + 1, y, false);
                break;
        }
    }

    protected void drawChop(int x, int y, boolean ver) {
        if (ver) {
            clean.chopEffect[0] = world[x - 1][y];
            clean.chopEffect[1] = world[x][y];
            clean.chopEffect[2] = world[x + 1][y];
            world[x - 1][y] = chopImg;
            world[x][y] = chopImg;
            world[x + 1][y] = chopImg;
            clean.chopEffectPos = new int[]{x - 1, y, x, y, x + 1, y};
        } else {
            clean.chopEffect[0] = world[x][y - 1];
            clean.chopEffect[1] = world[x][y];
            clean.chopEffect[2] = world[x][y + 1];
            world[x][y - 1] = chopImg;
            world[x][y] = chopImg;
            world[x][y + 1] = chopImg;
            clean.chopEffectPos = new int[]{x, y - 1, x, y, x, y + 1};
        }
    }
}
