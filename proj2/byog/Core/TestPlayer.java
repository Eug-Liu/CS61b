package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.io.IOException;
import java.util.Random;

public class TestPlayer {
    private static int WIDTH = Game.WIDTH;
    private static int HEIGHT = Game.HEIGHT;
    private static TERenderer ter = new TERenderer();
    private static long SEED = Game.TESTSEED;
    public static void main(String args[]) throws IOException {
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        Game game = new Game();
        game.initBg(world);
        Random rand = new Random(SEED + 2);

        game.RandomRoom(world, rand);
        game.BuildHall(world, rand);
        MovingObject.init(world);

        Player player = new Player(15, 15);
        Clean clean = new Clean(world);
        Sword sword = new Sword(world, clean,17, 16);
        Dragon dragon = new Dragon(40, 40 , player, world);
        UI ui = new UI(WIDTH, HEIGHT, dragon, player);

        game.runGame(world, clean, player, sword, dragon, rand, ui);
    }

}
