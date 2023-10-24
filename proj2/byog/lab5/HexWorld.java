package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 50;
    private static final int SEED = (int) System.currentTimeMillis();;
    private static final Random RANDOM = new Random(SEED);

    private static void addLine(TETile world[][], int length, int x, int y, TETile img){
        for (int i = 0; i < length; i++)
            world[x + i][y] = img;
    }
    private static void addHexagon(TETile world[][], int s, int x, int y, TETile img){
        int length = s;
        for (int i = 0; i < s; i++){
            for (int j = 0; j < length; j++) {
                world[x + j][y] = img;
            }
            length += 2;
            x--;
            y++;
        }

        length -= 2;
        x += 1;

        for (int i = 0; i < s; i++){
            for (int j = 0; j < length; j++) {
                world[x + j][y] = img;
            }
            length -= 2;
            x++;
            y++;
        }
    }

    private static void addHexagonLine(TETile world[][], int s, int n, int x, int y){
        TETile img = RandomImg();
        for (int i = 0; i < n; i++){
            addHexagon(world, s, x, y, img);
            x = nextX(s, x);
            y = nextY(s, y);
            img = RandomImg();
        }
    }

    private static void addTesselation(TETile world[][], int s, int x, int y){
        int n = 3;
        for (int i = 0; i < 3; i++){
            addHexagonLine(world, s, n, x, y);
            n++;
            y -= 2 * s;
        }

        n -= 2;
        y += 2 * s;
        y -= s;
        x += s * 2 -1;
        addHexagonLine(world, s, n, x, y);

        n -= 1;
        y -= s;
        x += s * 2 -1;
        addHexagonLine(world, s, n, x, y);

    }

    public static void draw(TETile world[][]){
        int x = 20;
        int y = 30;
        int s = RANDOM.nextInt(4) + 2;
        addTesselation(world, s, x, y);
    }

    private static int nextY(int s, int y){
        return y + s;
    }

    private static int nextX(int s, int x){
        return x + (s - 1) * 2 + 1;
    }

    private static TETile RandomImg(){
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.TREE;
            case 4: return Tileset.MOUNTAIN;
            default: return Tileset.SAND;
        }
    }

    private static void TestaddLine(TETile world[][]){
        TETile img = Tileset.SAND;
        int length = 50;
        int x = 5;
        int y = 45;
        addLine(world, length, x, y, img);

    }

    private static void TestaddHexagon(TETile world[][]){
        TETile img = RandomImg();
        int s = 3;
        int x = 20;
        int y = 30;
        addHexagon(world, s, x, y, img);
    }

    private static void TestaddHexagonLine(TETile world[][]){
        int s = 2;
        int x = 20;
        int y = 10;
        int n = 3;
        addHexagonLine(world, s, n, x, y);
    }

    public static void main(String args[]){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++)
                world[x][y] = Tileset.NOTHING;
        }

        draw(world);

        ter.renderFrame(world);
    }
}
