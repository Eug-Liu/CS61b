package byog.Core;

import byog.TileEngine.TETile;

public class TestplayWithString {
    private static final String START = "N1235S";

    public static void main(String args[]){
        Game game = new Game();
        TETile[][] worldState = game.playWithInputString(START);
        System.out.println(TETile.toString(worldState));
    }
}
