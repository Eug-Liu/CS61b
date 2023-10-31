package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;
import java.io.Serializable;


public class UI implements Serializable {
    private int width;
    private int height;
    Random rand;
    protected static final int TILE_SIZE = 16;


    private final String DRAGON = "巨龙血量: ";
    private final String ESCAPE = "巨龙已死,岛屿沉没,传送门开启,速速逃离!";
    private final String UNHOLDED = "武器: 无";
    private final String HOLDED = "武器: 宝剑";
    private final String LOCK = "传送门状态: 关闭";
    private final String UNLOCK = "传送门状态: 开启";
    private final String TELLHOLD = "拾起武器: 宝器";
    private final String PROMPT1 = "上下左右攻击:WSADJ 不要被巨龙喷射的绿色火焰击中或被恶龙撞击!";
    private final String PROMPT2 = "到绿色地标拾起武器, 战胜恶龙以开启传送门逃离恶魔岛!";
    private final String QUIT = "按:Q保存游戏并推出(否则失去存档)";

    private final String KILL = "获得成就: 屠龙勇士";

    Dragon dragon;
    Player player;

    public UI(int width, int height) {
        this.width = width;
        this.height = height;
        }

    public void initUI() {

        StdDraw.setCanvasSize(width * TILE_SIZE, height * TILE_SIZE);
        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);

        StdDraw.clear(new Color(0, 0, 0));

        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    public void drawMainMenu() {
        drawFrame("(N)新游戏", 10);
        drawFrame("(L)载入游戏", 0);
        drawFrame("(Q)退出游戏", -10);
    }

    public void drawFrame(String s) {
        int midWidth = width / 2;
        int midHeight = height / 2;

        StdDraw.clear();
        StdDraw.clear(Color.black);

        Font bigFont = new Font("Monaco", Font.BOLD, 50);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight, s);
        StdDraw.show();

        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);
    }

public void drawFrame(String s, int shift) {
    int midWidth = width / 2;
    int midHeight = height / 2;

    Font bigFont = new Font("Monaco", Font.BOLD, 50);
    StdDraw.setFont(bigFont);
    StdDraw.setPenColor(Color.white);
    StdDraw.text(midWidth, midHeight + shift, s);
    StdDraw.show();
}

    public UI(int width, int height, Dragon d, Player p) {
        this.width = width;
        this.height = height;
        dragon = d;
        player = p;
    }

    protected void showInfo() {
        StdDraw.setPenColor(Color.white);

        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE + 3);
        StdDraw.setFont(font);

        showDragonInfo();
        showPLayerInfo();
        showDoorInfo();
        showQuit();
        if (!dragon.died)
            prompt();

        font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);

        StdDraw.show();
    }

    protected void showDragonInfo() {
        if (!dragon.died)
            StdDraw.textRight(width - 1, height - 1, DRAGON + dragon.lives);
        else StdDraw.textRight(width - 1, height - 1, ESCAPE);
    }

    protected void showPLayerInfo() {
        if (player.sword == null)
            StdDraw.textRight(width - 1, height - 3, UNHOLDED);
        else StdDraw.textRight(width - 1, height - 3, HOLDED);
    }

    protected void showDoorInfo() {
        if (!dragon.died)
            StdDraw.textRight(width - 1, height - 5, LOCK);
        else StdDraw.textRight(width - 1, height - 5, UNLOCK);
    }

    protected void showQuit() {
        StdDraw.textRight(width - 1, height - 7, QUIT);
    }

    protected void tellHold() {
        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE * 5);
        StdDraw.setFont(font);

        int midWidth = width / 2;
        int midHeight = height / 2;
        StdDraw.setPenColor(Color.green);
        StdDraw.text(midWidth, midHeight, TELLHOLD);
        StdDraw.show();

        font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);
    }

    protected void tellKill() {
        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE * 5);
        StdDraw.setFont(font);

        int midWidth = width / 2;
        int midHeight = height / 2;
        StdDraw.setPenColor(Color.red);
        StdDraw.text(midWidth, midHeight, KILL);
        StdDraw.show();

        font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);
    }

    protected void prompt() {
        int midWidth = width / 2;
        StdDraw.text(midWidth, height - 1, PROMPT1);
        StdDraw.text(midWidth, height - 3, PROMPT2);
    }

}
