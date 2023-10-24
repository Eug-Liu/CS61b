package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.Core.Game;
import java.util.Random;

public class TestRandomRoom {
    private static int WIDTH = Game.WIDTH;
    private static int HEIGHT = Game.HEIGHT;
    private static TERenderer ter = new TERenderer();
    private static int SEED = (int) System.currentTimeMillis();
    public static void main(String args[]){
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        Game game = new Game();
        game.initBg(world);
        Random rand = new Random(SEED);

        game.RandomRoom(world, rand);

        ter.renderFrame(world);
    }
}
