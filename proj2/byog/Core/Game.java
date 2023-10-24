package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    public static final double MAXROOMRATE = 0.5;
    public static final double MINROOMRATE = 0.35;
    public static final int HEIGHTLIMIT = 10;
    public static final int WIDTHLIMIT = 10;
    public static final int MINHEIGHT = 3;
    public static final int MINWIDTH = 3;
    RoomList Rooms;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().


        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        initBg(finalWorldFrame);
        Random rand = initRandom(input);
        ter.initialize(WIDTH, HEIGHT);
        RandomRoom(finalWorldFrame, rand);
        BuildHall(finalWorldFrame, rand);
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

    /** Initialize the backgroud */
    protected void initBg(TETile[][] finalWorldFrame){
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                finalWorldFrame[x][y] = Tileset.WATER;
            }
        }
    }

    /** Extract the seed from the input and then initialize the random */
    protected Random initRandom(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < input.length() - 1; i++) {
            sb.append(input.charAt(i));
        }
        String str = sb.toString();
        System.out.println(str);
        final long seed = Long.parseLong(str);
        return new Random(seed);
    }

    /** Build a rectangular room */
    protected void BuildRoom(TETile world[][], int x,int y, int w, int h){
        // Build floor
        for(int i = 0; i < w; i++){
            for(int j = 0; j < h; j++){
                world[x + i][y + j] = Tileset.SAND;
            }
        }

        // Build walls
        BuildWall(world, x, y, w, false);
        BuildWall(world, x, y, h, true);
        BuildWall(world, x, y + h - 1, w, false);
        BuildWall(world, x + w - 1, y, h, true);

    }

    /** Build a straight wall */
    protected void BuildWall(TETile[][] world, int x1, int y1, int length, boolean vertical){
        if(vertical){
            for (int i = 0; i < length; i++){
                world[x1][y1 + i] = Tileset.MOUNTAIN;
            }
        }
        else {
            for (int i = 0; i < length; i++){
                world[x1 + i][y1] = Tileset.MOUNTAIN;
            }
        }
    }

    /** Generate rooms in different locations randomly */
    protected void RandomRoom(TETile world[][], Random rand){
        // Limit the area
        double area = (double) HEIGHT * WIDTH;
        double maxArea = area * MAXROOMRATE;
        double minArea = area * MINROOMRATE;
        int holdArea = 0;

        Rooms = new RoomList();

        while (holdArea < minArea) {
            int x = rand.nextInt(WIDTH - MINWIDTH);
            int y = rand.nextInt(HEIGHT - MINHEIGHT);
            int h = rand.nextInt(HEIGHTLIMIT) + MINHEIGHT;
            int w = rand.nextInt(WIDTHLIMIT) + MINWIDTH;

            if (!Rooms.checkAvailable(x, y, w, h) || !checkBound(x, y, w, h) || holdArea + h * w > maxArea)
                continue;

            holdArea += h * w;
            Rooms.add(x, y, w, h);
            BuildRoom(world, x, y, w, h);
        }
    }

    protected void BuildHall(TETile world[][], Random rand) {
        Rooms.visitNext();
        do {
            int[] path = Rooms.nextPath(rand);
            int i = 0;
            while (i < path.length){
                int x = path[i];
                int y = path[i + 1];
                world[x][y] = Tileset.SAND;
                HallWall(world, x, y);
                i += 2;
            }

        } while (Rooms.visitNext());
    }

    protected void HallWall(TETile world[][], int x, int y){
        if (x + 1 <= WIDTH && world[x + 1][y].equals(Tileset.WATER)){
            world[x + 1][y] = Tileset.MOUNTAIN;
        }
        if (x - 1 >= 0 && world[x - 1][y].equals(Tileset.WATER)){
            world[x - 1][y] = Tileset.MOUNTAIN;
        }
        if (y + 1 <= HEIGHT && world[x][y + 1].equals(Tileset.WATER)){
            world[x][y + 1] = Tileset.MOUNTAIN;
        }
        if (y - 1 >= 0 && world[x][y - 1].equals(Tileset.WATER)){
            world[x][y - 1] = Tileset.MOUNTAIN;
        }
    }

    protected boolean checkBound(int x, int y, int w, int h){
        return x + w <= WIDTH && y + h <= HEIGHT;
    }
}
