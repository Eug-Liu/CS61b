package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.Core.Game;
import java.util.Random;

public class TestBuildRoom {
    private static int WIDTH = Game.WIDTH;
    private static int HEIGHT = Game.HEIGHT;
    private static TERenderer ter = new TERenderer();
    public static void main(String args[]){
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        Game game = new Game();
        game.initBg(world);

        game.BuildRoom(world, WIDTH - 1, HEIGHT - 2, 1, 1);
        game.BuildRoom(world, 0, 10, 5, 5);
        game.BuildRoom(world, 23, 10, 5, 20);
        game.BuildRoom(world, 30, 30, 5, 5);

        ter.renderFrame(world);
    }
}
