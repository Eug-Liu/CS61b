package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Random;

public class Game implements Serializable {
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
    boolean gameOver = false;

    private final String DATAFILE = "datafile.txt";

    // Seed to test playWithKeyboard function
    public static final long TESTSEED = 1234;
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() throws IOException {
        menu();
    }

    protected void menu() throws IOException {
        UI menuUI = new UI(WIDTH, HEIGHT);
        menuUI.initUI();
        menuUI.drawMainMenu();
        while (true) {
            if (!StdDraw.hasNextKeyTyped())
                continue;
            char key = StdDraw.nextKeyTyped();
            if (key == 'n' || key == 'N') {
                String seed = "";
                char input = 'a';
                menuUI.drawFrame(seed);
                menuUI.drawFrame("输入世界种子后输入S进入游戏", 12);
                menuUI.drawFrame("或直接输入S使用随机世界种子", 6);
                while (input != 's' && input != 'S') {
                    if (!StdDraw.hasNextKeyTyped()) {
                        continue;
                    }
                    input = StdDraw.nextKeyTyped();
                    if (!Character.isDigit(input)) {
                        continue;
                    }
                    seed += String.valueOf(input);
                    menuUI.drawFrame(seed);
                    menuUI.drawFrame("输入世界种子后输入S进入游戏", 12);
                    menuUI.drawFrame("或直接输入S使用随机世界种子", 6);
                }
                if (seed.isEmpty())
                    bootGame(System.currentTimeMillis());
                else
                    bootGame(Long.parseLong(seed));
                StdDraw.pause(500);
            } else if(key == 'Q' || key == 'q'){
                System.exit(0);
            } else if(key == 'L' || key == 'l') {
                load(menuUI);
            }
        }
    }

    protected void bootGame(long seed) throws IOException {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initBg(world);
        Random rand = new Random(seed);

        RandomRoom(world, rand);
        BuildHall(world, rand);
        MovingObject.init(world);

        Player player = allocatePlayer(world, rand);
        Clean clean = new Clean(world);
        Sword sword = allocateSword(player,world,clean,rand);
        Dragon dragon = allocateDragon(world, player, rand);
        UI ui = new UI(WIDTH, HEIGHT, dragon, player);

        placeDoor(world, rand);

        runGame(world, clean, player, sword, dragon, rand, ui);
    }

    protected void runGame(TETile[][] world, Clean clean, Player player, Sword sword, Dragon dragon, Random rand, UI ui) throws IOException {
        ter.renderFrame(world);
        ui.showInfo();


        while (!gameOver){


            if (!StdDraw.hasNextKeyTyped())
                continue;

            clean.cleanEffect();

            char key = StdDraw.nextKeyTyped();
            switch (key) {
                case 'w':
                    player.moveUp();
                    player.dir = player.UP;
                    break;
                case 's':
                    player.moveDown();
                    player.dir = player.DOWN;
                    break;
                case 'a':
                    player.moveLeft();
                    player.dir = player.LEFT;
                    break;
                case 'd':
                    player.moveRight();
                    player.dir = player.RIGHT;
                    break;
                case 'j':
                    if (player.sword != null)
                        player.attack();
                    break;
                case ':':
                    while (true) {
                        ui.drawFrame("按Q确认退出");
                        if (!StdDraw.hasNextKeyTyped())
                            continue;
                        char key2 = StdDraw.nextKeyTyped();
                        if (key2 == 'q' || key2 == 'Q')
                            save(world, clean, player, sword, dragon, rand, ui);
                        else break;
                    }
            }


            dragon.move();
            if (dragon.died) {
                flooding(world, rand);
            }

            ter.renderFrame(world);
            ui.showInfo();

            checkStatus(world, player, dragon, rand, ui);

            if (sword.holder == null && sword.isPicked(player)){
                sword.pick(player);
                ui.tellHold();
            }
        }
    }

    protected void load(UI loadUI) {
        try {
            FileInputStream fistream = new FileInputStream(DATAFILE);
            ObjectInputStream oistream = new ObjectInputStream(fistream);

            TETile[][] world = (TETile[][]) oistream.readObject();
            Clean clean = (Clean) oistream.readObject();
            Player player = (Player) oistream.readObject();
            Sword sword = (Sword) oistream.readObject();
            Dragon dragon = (Dragon) oistream.readObject();
            Random rand = (Random) oistream.readObject();
            UI ui = (UI) oistream.readObject();
            MovingObject.init(world);

            Font font = new Font("Monaco", Font.BOLD, UI.TILE_SIZE - 2);
            StdDraw.setFont(font);

            runGame(world, clean, player, sword, dragon, rand, ui);
        } catch (IOException e) {
            e.printStackTrace();
            loadUI.drawFrame("加载失败");
            StdDraw.pause(500);
        } catch (ClassNotFoundException e) {
            loadUI.drawFrame("没有存档");
            StdDraw.pause(500);
        }
    }

    protected void save(TETile[][] world, Clean clean, Player player, Sword sword, Dragon dragon, Random rand, UI ui) {
        try {
            FileOutputStream fostream = new FileOutputStream(DATAFILE);
            ObjectOutputStream oostream = new ObjectOutputStream(fostream);

            oostream.writeObject(world);
            oostream.writeObject(clean);
            oostream.writeObject(player);
            oostream.writeObject(sword);
            oostream.writeObject(dragon);
            oostream.writeObject(rand);
            oostream.writeObject(ui);

            oostream.close();
            fostream.close();

            ui.drawFrame("保存成功");
            while (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            ui.drawFrame("保存失败");
            StdDraw.pause(500);
            while (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            return;
        }
    }


    protected void checkStatus(TETile world[][], Player player, Dragon dragon, Random rand, UI ui) {
        if (player.currPosItem.equals(Tileset.WATER) || player.currPosItem == Tileset.GRASS || world[player.x][player.y].equals(Tileset.GRASS)){
            fail(ui);
        }

        if ((player.currPosItem.equals(dragon.img) || dragon.currPosItem.equals(player.img)) && !dragon.died)
            fail(ui);

        if (player.currPosItem.equals(Tileset.UNLOCKED_DOOR))
            win(ui);

        if (!dragon.died && world[dragon.x][dragon.y].equals(Tileset.WALL)) {
            dragon.lives -= 1;
            world[dragon.x][dragon.y] = dragon.img;
            System.out.println(dragon.lives);
        }

        if (dragon.lives == 0 && !dragon.died)
            dragonDie(world, dragon, rand, ui);
    }

    protected void dragonDie(TETile world[][], Dragon dragon, Random rand, UI ui) {
        dragon.died = true;
        openDoor(world);
        ui.tellKill();
    }

    int doorX;
    int doorY;
    protected void placeDoor(TETile world[][], Random rand) {
        doorY = rand.nextInt(HEIGHT);
        doorX = rand.nextInt(WIDTH);
        while (!world[doorX][doorY].equals(Tileset.SAND)) {
            doorY = rand.nextInt(HEIGHT);
            doorX = rand.nextInt(WIDTH);
        }
        world[doorX][doorY] = Tileset.LOCKED_DOOR;
    }

    protected void openDoor(TETile world[][]) {
        world[doorX][doorY] = Tileset.UNLOCKED_DOOR;
    }
    protected void flooding(TETile world[][], Random rand) {
        int x;
        int y;
        int n = 3;
        while (n != 0){
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(HEIGHT);
            if (world[x][y].equals(Tileset.MOUNTAIN) || world[x][y].equals(Tileset.SAND)){
                world[x][y] = Tileset.WATER;
            } else {
                continue;
            }
            n--;
        }
    }

    protected Player allocatePlayer(TETile world[][], Random rand) {
        int x = rand.nextInt(WIDTH);
        int y = rand.nextInt(HEIGHT);
        while (!world[x][y].equals(Tileset.SAND)){
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(HEIGHT);
        }
        return new Player(x, y);
    }

    private final int SWORDDIS = 15;

    protected Sword allocateSword(Player player, TETile world[][], Clean clean, Random rand) {
        int x = rand.nextInt(WIDTH);
        int y = rand.nextInt(HEIGHT);
        Sword sword = new Sword(world, clean, x, y);
        while (!world[x][y].equals(Tileset.SAND) || sword.xDis(player) > SWORDDIS || sword.yDis(player) > SWORDDIS){
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(HEIGHT);
            sword = new Sword(world, clean, x, y);

        }

        sword.placeSword(world);
        return sword;
    }

    private final int initDragonDis = 15;
    protected Dragon allocateDragon(TETile world[][], Player player, Random rand) {
        int x = rand.nextInt(WIDTH);
        int y = rand.nextInt(HEIGHT);
        Dragon dragon = new Dragon(x, y, player, world);
        while (dragon.abs(dragon.xDistance()) < initDragonDis || dragon.abs(dragon.yDistance()) < initDragonDis){
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(HEIGHT);
            dragon = new Dragon(x, y, player, world);
        }
        dragon.placeDragon();
        return dragon;
    }

    protected void win(UI ui) {
        ui.drawFrame("成功逃离,游戏胜利!");
        StdDraw.show();
        while (!StdDraw.hasNextKeyTyped())
            continue;
        System.exit(0);
    }

    protected void fail(UI ui) {
        ui.drawFrame("游戏失败!");
        StdDraw.show();
        while (!StdDraw.hasNextKeyTyped())
            continue;
        System.exit(0);
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
